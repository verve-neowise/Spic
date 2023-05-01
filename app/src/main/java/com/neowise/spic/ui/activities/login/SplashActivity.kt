package com.neowise.spic.ui.activities.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neowise.spic.R
import com.neowise.spic.util.timeTask

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        timeTask(2000L) {
            showMain()
        }
    }

    private fun showMain() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}