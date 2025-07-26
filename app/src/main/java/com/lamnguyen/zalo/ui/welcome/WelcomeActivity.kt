package com.lamnguyen.zalo.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.login.LoginActivity
import com.lamnguyen.zalo.ui.main.MainActivity
import com.lamnguyen.zalo.utils.helpers.TokenHelper

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        loginSuccess()

        findViewById<AppCompatButton>(R.id.button_login).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginSuccess() {
        val token = TokenHelper.getAccessToken(this)
        if (token == null) return

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}