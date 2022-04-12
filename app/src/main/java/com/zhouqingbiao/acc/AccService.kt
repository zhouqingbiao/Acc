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
    var fx = false

    // 发表观点1分
    private var fbgd = false

    // 本地频道1分
    var bdpd = "本地频道"

    // 强国运动2分
    var qgyd = "强国运动"


    private lateinit var menuButton: AccessibilityNodeInfo
    var menuButtonCount = 0

    private lateinit var szButton: AccessibilityNodeInfo
    var szButtonCount = 0

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val text = "强国城兑福利"
        val viewId = "cn.xuexi.android:id/webview_frame"
//        findByText(text, event)
//        findByViewId(viewId, event)

        // 积分
        if (jfCount == 0) {
            val size = rootInActiveWindow.findAccessibilityNodeInfosByViewId(jfViewId).size
            if (size > 0) {
                jf = rootInActiveWindow.findAccessibilityNodeInfosByViewId(jfViewId)[0]
                Log.i(xue, "${jf.text}积分")
                jfCount++
            }

            // jf.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }

        // 百灵
        if (blCount == 0) {
            val size = rootInActiveWindow.findAccessibilityNodeInfosByViewId(blViewId).size
            if (size > 0) {
                bl = rootInActiveWindow.findAccessibilityNodeInfosByViewId(blViewId)[0]
                Log.i(xue, "百灵")
                blCount++
            }
        }

        // 分享
        // 发表观点
//        if (!fx) {
//            val size =
//                rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/general_card_title_id").size
//            if (size > 0) {
//                var b =
//                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/general_card_title_id")[0].text
//                Log.i(xue, "$b")
//                var a =
//                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/general_card_title_id")[0].parent.parent
//                if (a.isClickable) {
//                    a.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                }
//            }
//            fx = true
//        }


        val accessibilityNodeInfosRootInActiveWindow =
            rootInActiveWindow.findAccessibilityNodeInfosByViewId(viewId)
        if (accessibilityNodeInfosRootInActiveWindow.size > 0) {
            (0 until accessibilityNodeInfosRootInActiveWindow.size).forEach { index ->
                Log.i(
                    xue,
                    "findByViewId->rootInActiveWindow->${index}->${accessibilityNodeInfosRootInActiveWindow[index].viewIdResourceName}"
                )
                recycle(mutableListOf(accessibilityNodeInfosRootInActiveWindow[index]))
                findByText()
            }
        }


        // 本地
        if (bdCount == 0) {
            val size = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江").size
            if (size > 0) {
                Log.i(
                    xue,
                    "${rootInActiveWindow.findAccessibilityNodeInfosByText("浙江")[0].text}"
                )
                bd = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江")[0].parent
                bdCount++

                // bd.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }
    }

    override fun onInterrupt() {

    }

    // 正式
    private var mutableListAccessibilityNodeInfo: MutableList<AccessibilityNodeInfo> =
        mutableListOf()

    private fun recycle(mani: MutableList<AccessibilityNodeInfo>) {
        // 临时
        val mutableList: MutableList<AccessibilityNodeInfo> =
            mutableListOf()
        try {
            mani.forEach { ani ->
                // 有子节点才进行添加操作
                if (ani.childCount > 0) {
                    (0 until ani.childCount).forEach { index ->
                        // 同时添加
                        mutableListAccessibilityNodeInfo.add(ani.getChild(index))
                        mutableList.add(ani.getChild(index))
                    }
                }
            }
        } catch (e: Exception) {
            Log.i(xue, "$e")
        }

        // 只循环临时
        if (mutableList.size > 0) {
            recycle(mutableList)
        }
    }

    private fun findByText() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            Log.i(xue, "${ani.className}")
            Log.i(xue, "${ani.text}")
        }
    }

    private fun findByText(text: String, event: AccessibilityEvent?) {

        windows.forEach { accessibilityWindowInfo ->
            val accessibilityNodeInfosRoot =
                accessibilityWindowInfo.root.findAccessibilityNodeInfosByText(text)

            if (accessibilityNodeInfosRoot.size > 0) {
                (0 until accessibilityNodeInfosRoot.size).forEach { index ->
                    Log.i(
                        xue,
                        "findByText->root->${index}->${accessibilityNodeInfosRoot[index].text}"
                    )
                }
            }
        }

        val accessibilityNodeInfosRootInActiveWindow =
            rootInActiveWindow.findAccessibilityNodeInfosByText(text)
        if (accessibilityNodeInfosRootInActiveWindow.size > 0) {
            (0 until accessibilityNodeInfosRootInActiveWindow.size).forEach { index ->
                Log.i(
                    xue,
                    "findByText->rootInActiveWindow->${index}->${accessibilityNodeInfosRootInActiveWindow[index].text}"
                )
            }
        }

        val accessibilityNodeInfosSource = event?.source?.findAccessibilityNodeInfosByText(text)

        if (accessibilityNodeInfosSource != null) {
            if (accessibilityNodeInfosSource.size > 0) {
                (0 until accessibilityNodeInfosSource.size).forEach { index ->
                    Log.i(
                        xue,
                        "findByText->source->${index}->${accessibilityNodeInfosSource[index].text}"
                    )
                }
            }
        }
    }

    private fun findByViewId(viewId: String, event: AccessibilityEvent?) {

        windows.forEach { accessibilityWindowInfo ->
            val accessibilityNodeInfosRoot =
                accessibilityWindowInfo.root.findAccessibilityNodeInfosByViewId(viewId)

            if (accessibilityNodeInfosRoot.size > 0) {
                (0 until accessibilityNodeInfosRoot.size).forEach { index ->
                    Log.i(
                        xue,
                        "findByViewId->root->${index}->${accessibilityNodeInfosRoot[index].viewIdResourceName}"
                    )
                }
            }
        }

        val accessibilityNodeInfosRootInActiveWindow =
            rootInActiveWindow.findAccessibilityNodeInfosByViewId(viewId)
        if (accessibilityNodeInfosRootInActiveWindow.size > 0) {
            (0 until accessibilityNodeInfosRootInActiveWindow.size).forEach { index ->
                Log.i(
                    xue,
                    "findByViewId->rootInActiveWindow->${index}->${accessibilityNodeInfosRootInActiveWindow[index].viewIdResourceName}"
                )
            }
        }

        val accessibilityNodeInfosSource = event?.source?.findAccessibilityNodeInfosByViewId(viewId)

        if (accessibilityNodeInfosSource != null) {
            if (accessibilityNodeInfosSource.size > 0) {
                (0 until accessibilityNodeInfosSource.size).forEach { index ->
                    Log.i(
                        xue,
                        "findByViewId->source->${index}->${accessibilityNodeInfosSource[index].viewIdResourceName}"
                    )
                }
            }
        }
    }
}
