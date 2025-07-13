package com.lamnguyen.zalo.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.main.fragments.MainFragment
import com.lamnguyen.zalo.utils.functions.setStatusBarGradiant

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_main, MainFragment())
            .commit()

    }
}