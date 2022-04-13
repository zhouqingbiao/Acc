package com.zhouqingbiao.acc

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AccService : AccessibilityService() {

    // 学
    private var xue = "学"

    // 积分
    private var jfViewId = "cn.xuexi.android:id/comm_head_xuexi_score"
    private var jfBoolean = false
    private var jf: AccessibilityNodeInfo? = null
    private var jfCount = 0

    // 本地
    private var bdText = "浙江"
    private var bdBoolean = false
    private var bd: AccessibilityNodeInfo? = null
    private var bdCount = 0

    // 百灵
    private var blViewId = "cn.xuexi.android:id/home_bottom_tab_button_ding"
    private var blBoolean = false
    private var bl: AccessibilityNodeInfo? = null
    private var blCount = 0

    // 工作
    private var gzViewId = "cn.xuexi.android:id/home_bottom_tab_button_work"
    private var gzBoolean = false
    private var gz: AccessibilityNodeInfo? = null
    private var gzCount = 0

    // 学习积分
    private var xxjfViewId = "cn.xuexi.android:id/webview_frame"
    private var xxjfBoolean = false
    private var xxjf: MutableList<AccessibilityNodeInfo> = mutableListOf()
    private var xxjfBack = false

    // ---------------------------------------------------------------------------------------------

    // 登录1分
    private var dlText = "登录"
    private var dlBoolean = false
    private var dl: AccessibilityNodeInfo? = null
    private var dlF: AccessibilityNodeInfo? = null
    private var dlClick: AccessibilityNodeInfo? = null

    // 我要选读文章12分
    private var wyxdwzText = "我要选读文章"
    private var wyxdwzBoolean = false
    private var wyxdwz: AccessibilityNodeInfo? = null
    private var wyxdwzF: AccessibilityNodeInfo? = null
    private var wyxdwzClick: AccessibilityNodeInfo? = null

    // 视听学习6分
    private var stxxText = "视听学习"
    private var stxxBoolean = false
    private var stxx: AccessibilityNodeInfo? = null
    private var stxxF: AccessibilityNodeInfo? = null
    private var stxxClick: AccessibilityNodeInfo? = null

    // 视听学习时长6分
    private var stxxscText = "视听学习时长"
    private var stxxscBoolean = false
    private var stxxsc: AccessibilityNodeInfo? = null
    private var stxxscF: AccessibilityNodeInfo? = null
    private var stxxscClick: AccessibilityNodeInfo? = null

    // 每日答题5分
    private var mrdtText = "每日答题"
    private var mrdtBoolean = false
    private var mrdt: AccessibilityNodeInfo? = null
    private var mrdtF: AccessibilityNodeInfo? = null
    private var mrdtClick: AccessibilityNodeInfo? = null

    // 每周答题5分
    private var mzdtText = "每周答题"
    private var mzdtBoolean = false
    private var mzdt: AccessibilityNodeInfo? = null
    private var mzdtF: AccessibilityNodeInfo? = null
    private var mzdtClick: AccessibilityNodeInfo? = null

    // 专项答题10分
    private var zxdtText = "专项答题"
    private var zxdtBoolean = false
    private var zxdt: AccessibilityNodeInfo? = null
    private var zxdtF: AccessibilityNodeInfo? = null
    private var zxdtClick: AccessibilityNodeInfo? = null

    // 挑战答题6分
    private var tzdtText = "挑战答题"
    private var tzdtBoolean = false
    private var tzdt: AccessibilityNodeInfo? = null
    private var tzdtF: AccessibilityNodeInfo? = null
    private var tzdtClick: AccessibilityNodeInfo? = null

    // 四人赛5分
    private var srsText = "四人赛"
    private var srsBoolean = false
    private var srs: AccessibilityNodeInfo? = null
    private var srsF: AccessibilityNodeInfo? = null
    private var srsClick: AccessibilityNodeInfo? = null

    // 双人对战2分
    private var srdzText = "双人对战"
    private var srdzBoolean = false
    private var srdz: AccessibilityNodeInfo? = null
    private var srdzF: AccessibilityNodeInfo? = null
    private var srdzClick: AccessibilityNodeInfo? = null

    // 订阅2分
    private var dyText = "订阅"
    private var dyBoolean = false
    private var dy: AccessibilityNodeInfo? = null
    private var dyF: AccessibilityNodeInfo? = null
    private var dyClick: AccessibilityNodeInfo? = null

    // 分享1分
    private var fxText = "分享"
    private var fxBoolean = false
    private var fx: AccessibilityNodeInfo? = null
    private var fxF: AccessibilityNodeInfo? = null
    private var fxClick: AccessibilityNodeInfo? = null

    // 发表观点1分
    private var fbgdText = "发表观点"
    private var fbgdBoolean = false
    private var fbgd: AccessibilityNodeInfo? = null
    private var fbgdF: AccessibilityNodeInfo? = null
    private var fbgdClick: AccessibilityNodeInfo? = null

    // 本地频道1分
    private var bdpdText = "本地频道"
    private var bdpdBoolean = false
    private var bdpd: AccessibilityNodeInfo? = null
    private var bdpdF: AccessibilityNodeInfo? = null
    private var bdpdClick: AccessibilityNodeInfo? = null

    // 强国运动2分
    private var qgydText = "强国运动"
    private var qgydBoolean = false
    private var qgyd: AccessibilityNodeInfo? = null
    private var qgydF: AccessibilityNodeInfo? = null
    private var qgydClick: AccessibilityNodeInfo? = null


    override fun onServiceConnected() {
        super.onServiceConnected()

        // 初始化

        // 积分
        jfBoolean = false
        jfCount = 0

        // 本地
        bdBoolean = false
        bdCount = 0

        // 百灵
        blBoolean = false
        blCount = 0

        // 工作
        gzBoolean = false
        gzCount = 0

        // -----------------------------------------------------------------------------------------

        // 登录1分
        dlBoolean = false

        // 我要选读文章12分
        wyxdwzBoolean = false

        // 视听学习6分
        stxxBoolean = false

        // 视听学习时长6分
        stxxscBoolean = false

        // 每日答题5分
        mrdtBoolean = false

        // 每周答题5分
        mzdtBoolean = false

        // 专项答题10分
        zxdtBoolean = false

        // 挑战答题6分
        tzdtBoolean = false

        // 四人赛5分
        srsBoolean = false

        // 双人对战2分
        srdzBoolean = false

        // 订阅2分
        dyBoolean = false

        // 分享1分
        fxBoolean = false

        // 发表观点1分
        fbgdBoolean = false

        // 本地频道1分
        bdpdBoolean = false

        // 强国运动2分
        qgydBoolean = false

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        // 积分
        if (!jfBoolean) {
            val temp = rootInActiveWindow.findAccessibilityNodeInfosByViewId(jfViewId)
            if (temp.size > 0) {
                jf = temp[0]
                Log.i(xue, "${jf?.text}积分")
                jfBoolean = true
            }
        }

        // 本地
        if (!bdBoolean) {
            val temp = rootInActiveWindow.findAccessibilityNodeInfosByText(bdText)
            if (temp.size > 0) {
                bd = temp[0].parent
                Log.i(xue, bdText)
                bdBoolean = true
            }
        }

        // 百灵
        if (!blBoolean) {
            val temp = rootInActiveWindow.findAccessibilityNodeInfosByViewId(blViewId)
            if (temp.size > 0) {
                bl = temp[0]
                Log.i(xue, "百灵")
                blBoolean = true
            }
        }

        // 工作
        if (!gzBoolean) {
            val temp = rootInActiveWindow.findAccessibilityNodeInfosByViewId(gzViewId)
            if (temp.size > 0) {
                gz = temp[0]
                Log.i(xue, "工作")
                gzBoolean = true
            }
        }

        // 学习积分
        // 如果 积分存在 学习积分不存在 则执行
        if (jfBoolean && !xxjfBoolean) {
            // 点击积分进入学习积分
            jf?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            // 查找webview_frame节点
            xxjf = rootInActiveWindow.findAccessibilityNodeInfosByViewId(xxjfViewId)
            // 如果有则执行
            if (xxjf.size > 0) {
                // 遍历所有子节点
                (0 until xxjf.size).forEach { index ->
                    recycle(mutableListOf(xxjf[index]))
                }
                // 查找学习积分标题并赋值
                findXxjfBt()
            }
        }

        // 学习积分明细
        // 如果学习积分存在则执行
        if (xxjfBoolean) {
            if (!dlBoolean && dl != null) {
                recycle(mutableListOf(dl?.parent?.parent!!))
                findDl()
            }
            if (!wyxdwzBoolean && wyxdwz != null) {
                recycle(mutableListOf(wyxdwz?.parent?.parent!!))
                findWyxdwz()
            }
            if (!stxxBoolean && stxx != null) {
                recycle(mutableListOf(stxx?.parent?.parent!!))
                findStxx()
            }
            if (!stxxscBoolean && stxxsc != null) {
                recycle(mutableListOf(stxxsc?.parent?.parent!!))
                findStxxsc()
            }
            if (!mrdtBoolean && mrdt != null) {
                recycle(mutableListOf(mrdt?.parent?.parent!!))
                findMrdt()
            }
            if (!mzdtBoolean && mzdt != null) {
                recycle(mutableListOf(mzdt?.parent?.parent!!))
                findMzdt()
            }
            if (!zxdtBoolean && zxdt != null) {
                recycle(mutableListOf(zxdt?.parent?.parent!!))
                findZxdt()
            }
            if (!tzdtBoolean && tzdt != null) {
                recycle(mutableListOf(tzdt?.parent?.parent!!))
                findTzdt()
            }
            if (!srsBoolean && srs != null) {
                recycle(mutableListOf(srs?.parent?.parent!!))
                findSrs()
            }
            if (!srdzBoolean && srdz != null) {
                recycle(mutableListOf(srdz?.parent?.parent!!))
                findSrdz()
            }
            if (!dyBoolean && dy != null) {
                recycle(mutableListOf(dy?.parent?.parent!!))
                findDy()
            }
            if (!fxBoolean && fx != null) {
                recycle(mutableListOf(fx?.parent?.parent!!))
                findFx()
            }
            if (!fbgdBoolean && fbgd != null) {
                recycle(mutableListOf(fbgd?.parent?.parent!!))
                findFbgd()
            }
            if (!bdpdBoolean && bdpd != null) {
                recycle(mutableListOf(bdpd?.parent?.parent!!))
                findBdpd()
            }
            if (!qgydBoolean && qgyd != null) {
                recycle(mutableListOf(qgyd?.parent?.parent!!))
                findQgyd()
            }
        }

        //
        if (qgydBoolean && !xxjfBack) {
            performGlobalAction(GLOBAL_ACTION_BACK)
            xxjfBack = true
        }

        // 发表观点
        // 分享
        if (fbgdClick != null) {
            if (fbgdClick!!.text == "去看看") {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/general_card_title_id")
                if (temp.size > 0) {
                    if (temp[0].parent.parent.isClickable) {
                        temp[0].parent.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.i(xue, "11")
                    }
                }
            }
        }
    }

    override fun onInterrupt() {

    }

    // 正式可变List
    private var mutableListAccessibilityNodeInfo: MutableList<AccessibilityNodeInfo> =
        mutableListOf()

    /**
     * 查找所有节点方法
     * 重要
     */
    private fun recycle(mani: MutableList<AccessibilityNodeInfo>) {
        // 临时可变List
        val mutableList: MutableList<AccessibilityNodeInfo> =
            mutableListOf()

        // 开始遍历
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

        // 只循环临时可变List
        if (mutableList.size > 0) {
            recycle(mutableList)
        }
    }

    /**
     * 查找学习积分标题并赋值
     */
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
        }
        mutableListAccessibilityNodeInfo.clear()
        if (qgyd != null) {
            xxjfBoolean = true
        }
    }

    // 查找登录
    private fun findDl() {
        Log.i(xue, dlText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    dlF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                dlClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        dlBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找我要选读文章
    private fun findWyxdwz() {
        Log.i(xue, wyxdwzText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    wyxdwzF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                wyxdwzClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        wyxdwzBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找视听学习
    private fun findStxx() {
        Log.i(xue, stxxText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    stxxF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                stxxClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        stxxBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找视听学习时长
    private fun findStxxsc() {
        Log.i(xue, stxxscText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    stxxscF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                stxxscClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        stxxscBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找每日答题
    private fun findMrdt() {
        Log.i(xue, mrdtText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    mrdtF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                mrdtClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        mrdtBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找每周答题
    private fun findMzdt() {
        Log.i(xue, mzdtText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    mzdtF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                mzdtClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        mzdtBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找专项答题
    private fun findZxdt() {
        Log.i(xue, zxdtText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    zxdtF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                zxdtClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        zxdtBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找挑战答题
    private fun findTzdt() {
        Log.i(xue, tzdtText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    tzdtF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                tzdtClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        tzdtBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找四人赛
    private fun findSrs() {
        Log.i(xue, srsText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    srsF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                srsClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        srsBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找双人对战
    private fun findSrdz() {
        Log.i(xue, srdzText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    srdzF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                srdzClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        srdzBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找订阅
    private fun findDy() {
        Log.i(xue, dyText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    dyF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                dyClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        dyBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找分享
    private fun findFx() {
        Log.i(xue, fxText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    fxF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                fxClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        fxBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找发表观点
    private fun findFbgd() {
        Log.i(xue, fbgdText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    fbgdF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                fbgdClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        fbgdBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找本地频道
    private fun findBdpd() {
        Log.i(xue, bdpdText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    bdpdF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                bdpdClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        bdpdBoolean = true
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找强国运动
    private fun findQgyd() {
        Log.i(xue, qgydText)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    qgydF = ani
                    Log.i(xue, "${ani.text}")
                }
            }
            if (ani.isClickable) {
                qgydClick = ani
                Log.i(xue, "${ani.text}")
            }
        }
        // 找到标记
        qgydBoolean = true
        mutableListAccessibilityNodeInfo.clear()
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
