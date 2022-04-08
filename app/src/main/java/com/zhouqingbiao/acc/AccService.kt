package com.zhouqingbiao.acc

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AccService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // 积分
        var jf: AccessibilityNodeInfo
        // 登录1分
        var dl = false
        // 我要选读文章12分
        var wyxdwz = false
        // 视听学习6分
        var stxx = false
        // 视听学习时长6分
        var stxxsc = false
        // 每日答题5分
        var mrdt = false
        // 每周答题5分
        var mzdt = false
        // 专项答题5分
        var zxdt = false
        // 挑战答题10分
        var tzdt = false
        // 四人赛5分
        var srs = false
        // 双人对战2分
        var srdz = false
        // 订阅2分
        var dy = false
        // 分享1分
        var fx = false
        // 发表观点1分
        var fbgd = false
        // 本地频道1分
        var bdpd = false
        // 强国运动2分
        var qgyd = false
        Log.i("学习强国", "开始")
        val eventType = event?.eventType
        Log.i("学习强国", eventType.toString())
        val size = rootInActiveWindow.findAccessibilityNodeInfosByViewId("").size
        Log.i("学习强国", "找到${size}个ID")
        if (size > 0) {
            (0 until size).forEach { s ->
                jf = rootInActiveWindow.findAccessibilityNodeInfosByViewId("")[s]
                Log.i(
                    "学习强国",
                    "积分是${jf.text}"
                )
            }
        }
    }

    override fun onInterrupt() {

    }
}