package com.neowise.spic.ui.activities.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.neowise.spic.model.Role
import com.neowise.spic.model.Account
import com.neowise.spic.databinding.ActivityLoginBinding
import com.neowise.spic.model.Token
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.ActivityRoute
import com.neowise.spic.util.buildModel
import com.neowise.spic.util.validateValue
import com.neowise.spic.util.RunIO

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            login(build())
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun login(account: Account) {

        if (account == Account.Empty) return

        disableControls()

        RunIO<Token>()
            .run {
                Services.authService.login(account)
            }
            .onSuccess(::onLoginSuccess)
            .onFailure(::onLoginFailed)
            .finally {
                enableControls()
            }
            .execute()
    }

    private fun onLoginSuccess(token: Token) {
        if (token.isValid) {
            Preferences.instance(this).token = token
            nextActivity(token.role)
        }
    }

    private fun nextActivity(role: Role) {
        val roleActivity = ActivityRoute[role]
        startActivity(Intent(this, roleActivity))
//        startActivity(Intent(this, RoleSelectionActivity::class.java))
    }

    private fun onLoginFailed(e: Exception) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }

    private fun build(): Account {

        return buildModel(this, Account.Empty) {

            val username = binding.usernameEdit.validateValue("username")
            val password = binding.passwordEdit.validateValue("password")

            Account(username, password)
        }
    }

    private fun disableControls() {
        enableControls(false)
    }

    private fun enableControls(value: Boolean = true) {
        binding.loginButton.isEnabled = value
    }
}