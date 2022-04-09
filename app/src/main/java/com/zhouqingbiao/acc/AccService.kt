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
    var dl = "登录"

    // 我要选读文章12分
    var wyxdwz = "我要选读文章"

    // 视听学习6分
    var stxx = "视听学习"

    // 视听学习时长6分
    var stxxsc = "视听学习时长"

    // 每日答题5分
    var mrdt = "每日答题"

    // 每周答题5分
    var mzdt = "每周答题"

    // 专项答题10分
    var zxdt = "专项答题"

    // 挑战答题6分
    var tzdt = "挑战答题"

    // 四人赛5分
    var srs = "四人赛"

    // 双人对战2分
    var srdz = "双人对战"

    // 订阅2分
    var dy = "订阅"

    // 分享1分
    var fx = "分享"

    // 发表观点1分
    var fbgd = "发表观点"

    // 本地频道1分
    var bdpd = "本地频道"

    // 强国运动2分
    var qgyd = "强国运动"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.i(xue, "Event开始")
        // 打印eventType
        val eventType = event?.eventType
//        Log.i(xue, "eventType:${eventType}")

        // 寻找积分ViewId
        // 找到后不再寻找
        if (jfCount == 0) {
            val size = rootInActiveWindow.findAccessibilityNodeInfosByViewId(jfViewId).size
            Log.i(xue, "找到${size}个积分ViewId")
            if (size > 0) {
                jf = rootInActiveWindow.findAccessibilityNodeInfosByViewId(jfViewId)[0]
                Log.i(xue, "${jf.text}积分")
            }
            // 计数加1
            // 不再寻找
            jfCount = 1
        }

        // 寻找登录
        // 找到后不再寻找
        if (jfCount == 0) {
            val size = rootInActiveWindow.findAccessibilityNodeInfosByText(dl).size
            if (size > 0) {
                rootInActiveWindow.findAccessibilityNodeInfosByText(dl)[0].parent.parent.findAccessibilityNodeInfosByText("已完成")
                Log.i(xue, "${jf.text}积分")
            }
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
            val size = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江").size
            Log.i(xue, "找到本地")
            if (size > 0) {
                Log.i(xue, "${rootInActiveWindow.findAccessibilityNodeInfosByText("浙江")[0].text}")
                bd = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江")[0].parent
                // bd.performAction(AccessibilityNodeInfo.ACTION_CLICK)

                // 找到本地
                onCirculate(rootInActiveWindow)
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

    private fun onCirculate(accessibilityNodeInfo: AccessibilityNodeInfo) {
        if (accessibilityNodeInfo.childCount == 0) {
            Log.i(xue, "${accessibilityNodeInfo.text}")
        } else {
            (1 until accessibilityNodeInfo.childCount).forEach { index ->
                if (accessibilityNodeInfo.getChild(index) != null) {
                    Log.i(xue, "${accessibilityNodeInfo.getChild(index).text}")
                    onCirculate(accessibilityNodeInfo.getChild(index))
                }
            }
        }
    }
}