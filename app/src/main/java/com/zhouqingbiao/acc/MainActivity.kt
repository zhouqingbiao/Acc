package com.zhouqingbiao.acc

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun switchAcc(view: View) {
        var am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var cn = ComponentName("com.zhouqingbiao.acc", "com.zhouqingbiao.acc.AccService")
        if (am.getRunningServiceControlPanel(cn) == null) {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        } else {
            Toast.makeText(this, "无障碍已开启", Toast.LENGTH_SHORT).show()
        }
        findViewById<SwitchMaterial>(R.id.switch_acc).isChecked = true
    }

    fun startXxqg(view: View) {
        startActivity(packageManager.getLaunchIntentForPackage("cn.xuexi.android"))
    }
}