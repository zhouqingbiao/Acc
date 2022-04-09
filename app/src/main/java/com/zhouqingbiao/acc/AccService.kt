package com.zhouqingbiao.acc

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AccService : AccessibilityService() {

    // 学
    var xue = "学"

    // 积分
    private var jfViewId = "cn.xuexi.android:id/comm_head_xuexi_score"
    private lateinit var jf: AccessibilityNodeInfo
    private var jfCount = 0

    // 百灵
    var blViewId = "cn.xuexi.android:id/home_bottom_tab_button_ding"
    private lateinit var bl: AccessibilityNodeInfo
    var blCount = 0

    // 本地
    private var bdViewId = "cn.xuexi.android:id/view_pager"
    private lateinit var bd: AccessibilityNodeInfo
    private var bdCount = 0

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

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.i(xue, "Event开始")
        // 打印eventType
        val eventType = event?.eventType
        Log.i(xue, "eventType:${eventType}")

        // 寻找积分ViewId
        // 找到后不再寻找
        if (jfCount == 0) {
            val size =
                rootInActiveWindow.findAccessibilityNodeInfosByViewId(jfViewId).size
            Log.i(xue, "找到${size}个积分ViewId")
            if (size > 0) {
                jf =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId(jfViewId)[0]
            }
            Log.i(xue, "${jf.text}积分")
            // 计数加1
            // 不再寻找
            jfCount = 1
        }

        // 寻找百灵ViewId
        // 找到后不再寻找
        if (blCount == 0) {
            val size =
                rootInActiveWindow.findAccessibilityNodeInfosByViewId(blViewId).size
            Log.i(xue, "找到${size}个百灵ViewId")
            if (size > 0) {
                bl =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId(blViewId)[0]
            }
            // 计数加1
            // 不再寻找
            blCount = 1
        }

        // 寻找本地ViewId
        // 找到后不再寻找
        if (bdCount == 0) {
            val size =
                rootInActiveWindow.findAccessibilityNodeInfosByViewId(bdViewId).size
            Log.i(xue, "找到${size}个本地ViewId")
            if (size > 0) {
                bd =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId(bdViewId)[0]
//                var b = bd.getChild(2).getChild(0).getChild(0).getChild(0).getChild(3)
//                    .getChild(0) as TextView
//                Log.i("学习强国", "${b.text}")
            }
            // 计数加1
            // 不再寻找
            bdCount = 1
        }

        // 点击积分ViewId
        // jf.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        Log.i(xue, "Event结束")
    }

    override fun onInterrupt() {

    }
}