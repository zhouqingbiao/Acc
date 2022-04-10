package com.zhouqingbiao.acc

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast

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

    // 工作
    private var gzViewId = "cn.xuexi.android:id/home_bottom_tab_button_work"
    private lateinit var gz: AccessibilityNodeInfo
    private var gzCount = 0

    // 登录1分
    var dl = "登录"

    // 登录积分
    var dljf = ""

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

        // 寻找积分ViewId
        if (jfCount == 0) {
            val size = rootInActiveWindow.findAccessibilityNodeInfosByViewId(jfViewId).size
            if (size > 0) {
                jf = rootInActiveWindow.findAccessibilityNodeInfosByViewId(jfViewId)[0]
                Log.i(xue, "${jf.text}积分")
                jfCount++
            }
            // 点击积分
            // jf.performAction(AccessibilityNodeInfo.ACTION_CLICK)

            // getJf()
        }

//        Log.i(xue,"${event?.eventType}")
//        jf.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        windows.forEach { aw ->
            var size = aw.root.findAccessibilityNodeInfosByText("王水明").size
            if (size > 0) {
                Toast.makeText(this, "登录$size", Toast.LENGTH_SHORT).show()
                Log.i(xue, "登录$size")
            }
        }
//        performGlobalAction(GLOBAL_ACTION_BACK)
//        val size = rootInActiveWindow.findAccessibilityNodeInfosByText("登录").size
//        if (size > 0) {
//            Toast.makeText(this, "$size", Toast.LENGTH_SHORT).show()
//            Log.i(xue, "$size")
//            if (size > 0) {
//                dljf =
//                    rootInActiveWindow.findAccessibilityNodeInfosByText(dl)[0].parent.parent.getChild(
//                        2
//                    ).text as String
//                Toast.makeText(this, "登录积分${dljf}", Toast.LENGTH_SHORT).show()
//                Log.i(xue, "登录积分${dljf}")
//            }
//        }
        // 寻找百灵ViewId
        if (blCount == 0) {
            val size = rootInActiveWindow.findAccessibilityNodeInfosByViewId(blViewId).size
            if (size > 0) {
                bl = rootInActiveWindow.findAccessibilityNodeInfosByViewId(blViewId)[0]
                Log.i(xue, "百灵")
                blCount++
            }
        }

        // 寻找本地ViewId
        if (bdCount == 0) {
            val size = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江").size
            if (size > 0) {
                Log.i(xue, "${rootInActiveWindow.findAccessibilityNodeInfosByText("浙江")[0].text}")
                bd = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江")[0].parent
                bdCount++

                // bd.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }
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

    private fun getJf() {
        // 寻找登录
        val size = rootInActiveWindow.findAccessibilityNodeInfosByText(dl).size
        Toast.makeText(this, "$size", Toast.LENGTH_SHORT).show()
        Log.i(xue, "$size")
        if (size > 0) {
            dljf =
                rootInActiveWindow.findAccessibilityNodeInfosByText(dl)[0].parent.parent.getChild(2).text as String
            Toast.makeText(this, "登录积分${dljf}", Toast.LENGTH_SHORT).show()
            Log.i(xue, "登录积分${dljf}")
        }
        // 返回
        // performGlobalAction(GLOBAL_ACTION_BACK)
    }
}