package com.lamnguyen.zalo.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.configs.RetrofitClient
import com.lamnguyen.zalo.ui.login.LoginActivity
import com.lamnguyen.zalo.ui.main.MainActivity
import com.lamnguyen.zalo.utils.helpers.TokenHelper
import com.lamnguyen.zalo.utils.helpers.isPastWelcomeActivity
import com.lamnguyen.zalo.utils.helpers.pastWelcomeActivity
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        lifecycleScope.launch {
            loginSuccess()
        }

        findViewById<AppCompatButton>(R.id.button_login).setOnClickListener {
            pastWelcomeActivity(this)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private suspend fun loginSuccess() {
        try {
            val authService = RetrofitClient.authService(this@WelcomeActivity, false)
            authService.resignSuspend().data?.let {
                TokenHelper.saveAccessToken(it.accessToken, this@WelcomeActivity)
                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                startActivity(intent)
            }
        } catch (_: Exception) {
            if (!isPastWelcomeActivity(this)) return

            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            intent.putExtra(LoginActivity.ARG_HIDDEN_BUTTON_BACK, true)
            startActivity(intent)
        }
    }
}