package com.zhouqingbiao.acc

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
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

    fun startXxqg(view: View) {
        var am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var cn = ComponentName("com.zhouqingbiao.acc", "com.zhouqingbiao.acc.AccService")
        if (am.getRunningServiceControlPanel(cn) == null) {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            startActivity(packageManager.getLaunchIntentForPackage("cn.xuexi.android"))
        } else {
            startActivity(packageManager.getLaunchIntentForPackage("cn.xuexi.android"))
        }
    }
}