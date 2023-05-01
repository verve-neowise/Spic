package com.neowise.spic.ui.activities.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.neowise.spic.model.Role
import com.neowise.spic.model.SignUp
import com.neowise.spic.databinding.ActivitySignupBinding
import com.neowise.spic.model.Token
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.ActivityRoute
import com.neowise.spic.util.InvalidValueException
import com.neowise.spic.util.buildModel
import com.neowise.spic.util.validateValue
import com.neowise.spic.util.RunIO

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener {
            signUp(build())
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun signUp(signUp: SignUp) {

        if (signUp == SignUp.Empty) return

        disableControls()

        RunIO<Token>()
            .run {
                Services.authService.signUp(signUp)
            }
            .onSuccess(::onSignSuccess)
            .onFailure(::onSignFailed)
            .finally {
                enableControls()
            }
            .execute()
    }

    private fun onSignSuccess(token: Token) {
        if (token.isValid) {
            Preferences.instance(this).token = token
            nextActivity(token.role)
        }
    }

    private fun nextActivity(role: Role) {
//        startActivity(Intent(this, RoleSelectionActivity::class.java))
        val roleActivity = ActivityRoute[role]
        startActivity(Intent(this, roleActivity))
    }

    private fun onSignFailed(e: Exception) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }

    private fun build(): SignUp {

        return buildModel(this, SignUp.Empty) {

            val username = binding.usernameEdit.validateValue("username")
            val password = binding.passwordEdit.validateValue("password")
            val passwordConfirm = binding.confirmPasswordEdit.validateValue("Confirm password")
            val name = binding.nameEdit.validateValue("name")
            val birthDay = ""

            if (password != passwordConfirm) {
                throw InvalidValueException("password and confirmation does't match!")
            }

            SignUp(username, password, name, birthDay)
        }
    }

    private fun disableControls() {
        enableControls(false)
    }

    private fun enableControls(value: Boolean = true) {
        binding.signupButton.isEnabled = value
    }
}