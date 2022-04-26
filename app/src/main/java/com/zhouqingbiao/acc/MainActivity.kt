package com.zhouqingbiao.acc

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickAcc(view: View) {
        startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }
}