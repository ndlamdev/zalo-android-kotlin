package com.lamnguyen.zalo.ui.start

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.configs.RetrofitClient
import com.lamnguyen.zalo.domain.responses.LoginResponse
import com.lamnguyen.zalo.ui.login.LoginActivity
import com.lamnguyen.zalo.ui.main.MainActivity
import com.lamnguyen.zalo.ui.welcome.WelcomeActivity
import com.lamnguyen.zalo.utils.helpers.LogHelper
import com.lamnguyen.zalo.utils.helpers.TokenHelper
import com.lamnguyen.zalo.utils.helpers.isPastWelcomeActivity
import kotlinx.coroutines.launch

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)
    }

    override fun onStart() {
        super.onStart()

        resign()
    }

    private fun resign() {
        lifecycleScope.launch {
            try {
                val authService = RetrofitClient.authService(this@StartActivity, false)
                authService.resignSuspend().data?.let(this@StartActivity::resignSuccess)
            } catch (e: Exception) {
                LogHelper.errorWithClassName(this@StartActivity.javaClass, e)
                resignFail()
            }
        }
    }

    private fun resignSuccess(response: LoginResponse) {
        TokenHelper.saveAccessToken(response.accessToken, this@StartActivity)
        val intent = Intent(this@StartActivity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun resignFail() {
        var intent: Intent
        val isPastWelcomeActivity = isPastWelcomeActivity(this@StartActivity)
        if (!isPastWelcomeActivity) {
            intent = Intent(this@StartActivity, WelcomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        } else {
            intent = Intent(this@StartActivity, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            intent.putExtra(LoginActivity.ARG_HIDDEN_BUTTON_BACK, true)
        }


        startActivity(intent)
    }
}