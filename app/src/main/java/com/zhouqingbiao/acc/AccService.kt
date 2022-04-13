package com.zhouqingbiao.acc

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AccService : AccessibilityService() {

    // 登录1分
    var dlText = "登录"
    var dlBoolean = false
    lateinit var dl: AccessibilityNodeInfo
    lateinit var dlF: AccessibilityNodeInfo
    lateinit var dlClick: AccessibilityNodeInfo

    // 我要选读文章12分
    var wyxdwzText = "我要选读文章"
    var wyxdwzBoolean = false
    lateinit var wyxdwz: AccessibilityNodeInfo
    lateinit var wyxdwzF: AccessibilityNodeInfo
    lateinit var wyxdwzClick: AccessibilityNodeInfo

    // 视听学习6分
    var stxxText = "视听学习"
    var stxxBoolean = false
    lateinit var stxx: AccessibilityNodeInfo
    lateinit var stxxF: AccessibilityNodeInfo
    lateinit var stxxClick: AccessibilityNodeInfo

    // 视听学习时长6分
    var stxxscText = "视听学习时长"
    var stxxscBoolean = false
    lateinit var stxxsc: AccessibilityNodeInfo
    lateinit var stxxscF: AccessibilityNodeInfo
    lateinit var stxxscClick: AccessibilityNodeInfo

    // 每日答题5分
    var mrdtText = "每日答题"
    var mrdtBoolean = false
    lateinit var mrdt: AccessibilityNodeInfo
    lateinit var mrdtF: AccessibilityNodeInfo
    lateinit var mrdtClick: AccessibilityNodeInfo

    // 每周答题5分
    var mzdtText = "每周答题"
    var mzdtBoolean = false
    lateinit var mzdt: AccessibilityNodeInfo
    lateinit var mzdtF: AccessibilityNodeInfo
    lateinit var mzdtClick: AccessibilityNodeInfo

    // 专项答题10分
    var zxdtText = "专项答题"
    var zxdtBoolean = false
    lateinit var zxdt: AccessibilityNodeInfo
    lateinit var zxdtF: AccessibilityNodeInfo
    lateinit var zxdtClick: AccessibilityNodeInfo

    // 挑战答题6分
    var tzdtText = "挑战答题"
    var tzdtBoolean = false
    lateinit var tzdt: AccessibilityNodeInfo
    lateinit var tzdtF: AccessibilityNodeInfo
    lateinit var tzdtClick: AccessibilityNodeInfo

    // 四人赛5分
    var srsText = "四人赛"
    var srsBoolean = false
    lateinit var srs: AccessibilityNodeInfo
    lateinit var srsF: AccessibilityNodeInfo
    lateinit var srsClick: AccessibilityNodeInfo

    // 双人对战2分
    var srdzText = "双人对战"
    var srdzBoolean = false
    lateinit var srdz: AccessibilityNodeInfo
    lateinit var srdzF: AccessibilityNodeInfo
    lateinit var srdzClick: AccessibilityNodeInfo

    // 订阅2分
    var dyText = "订阅"
    var dyBoolean = false
    lateinit var dy: AccessibilityNodeInfo
    lateinit var dyF: AccessibilityNodeInfo
    lateinit var dyClick: AccessibilityNodeInfo

    // 分享1分
    var fxText = "分型"
    var fxBoolean = false
    lateinit var fx: AccessibilityNodeInfo
    lateinit var fxF: AccessibilityNodeInfo
    lateinit var fxClick: AccessibilityNodeInfo

    // 发表观点1分
    var fbgdText = "发表观点"
    var fbgdBoolean = false
    lateinit var fbgd: AccessibilityNodeInfo
    lateinit var fbgdF: AccessibilityNodeInfo
    lateinit var fbgdClick: AccessibilityNodeInfo

    // 本地频道1分
    var bdpdText = "本地频道"
    var bdpdBoolean = false
    lateinit var bdpd: AccessibilityNodeInfo
    lateinit var bdpdF: AccessibilityNodeInfo
    lateinit var bdpdClick: AccessibilityNodeInfo

    // 强国运动2分
    var qgydText = "强国运动"
    var qgydBoolean = false
    lateinit var qgyd: AccessibilityNodeInfo
    lateinit var qgydF: AccessibilityNodeInfo
    lateinit var qgydClick: AccessibilityNodeInfo

    // ------------------------------------------------------------

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


    private lateinit var menuButton: AccessibilityNodeInfo
    var menuButtonCount = 0

    private lateinit var szButton: AccessibilityNodeInfo
    var szButtonCount = 0

    override fun onServiceConnected() {
        super.onServiceConnected()


    }

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

        if (!dlBoolean and !wyxdwzBoolean and !stxxBoolean and !stxxscBoolean and !mrdtBoolean and !mzdtBoolean and !zxdtBoolean and !tzdtBoolean and !tzdtBoolean and !srsBoolean and !srdzBoolean and !dyBoolean and !fbgdBoolean and !bdpdBoolean and !qgydBoolean) {
            val accessibilityNodeInfosRootInActiveWindow =
                rootInActiveWindow.findAccessibilityNodeInfosByViewId(viewId)
            if (accessibilityNodeInfosRootInActiveWindow.size > 0) {
                (0 until accessibilityNodeInfosRootInActiveWindow.size).forEach { index ->
                    Log.i(
                        xue,
                        "findByViewId->rootInActiveWindow->${index}->${accessibilityNodeInfosRootInActiveWindow[index].viewIdResourceName}"
                    )
                    // 遍历所有子节点
                    recycle(mutableListOf(accessibilityNodeInfosRootInActiveWindow[index]))
                    // 查找学习积分标题
                    findXxjfBt()
                    // 查找学习积分明细

                }
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

    // 查找学习积分标题
    private fun findXxjfBt() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                when (ani.text) {
                    dlText -> dl = ani
                    wyxdwzText -> wyxdwz = ani
                    stxxText -> stxx = ani
                    stxxscText -> stxxsc = ani
                    mrdtText -> mrdt = ani
                    mzdtText -> mzdt = ani
                    zxdtText -> zxdt = ani
                    tzdtText -> tzdt = ani
                    srsText -> srs = ani
                    srdzText -> srdz = ani
                    dyText -> dy = ani
                    fxText -> fx = ani
                    fbgdText -> fbgd = ani
                    bdpdText -> bdpd = ani
                    qgydText -> qgyd = ani
                }
            }
            if (ani.isClickable) {
                dlClick = ani

                Log.i(xue, "${ani.className}")
                Log.i(xue, "${ani.text}")
            }
        }
    }

    // 查找学习积分明细
    private fun findXxjfMx() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    dlF = ani

                    Log.i(xue, "${ani.className}")
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                dlClick = ani

                Log.i(xue, "${ani.className}")
                Log.i(xue, "${ani.text}")
            }
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
