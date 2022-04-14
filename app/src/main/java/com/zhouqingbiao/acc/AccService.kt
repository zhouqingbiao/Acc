package com.zhouqingbiao.acc

import android.accessibilityservice.AccessibilityService
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AccService : AccessibilityService() {

    // 学
    private var xue = "学"

    // 步骤
    private var step = 1

    // 积分
    private var jfViewId = "cn.xuexi.android:id/comm_head_xuexi_score"
    private var jf: AccessibilityNodeInfo? = null

    // 本地
    private var bdText = "浙江"
    private var bd: AccessibilityNodeInfo? = null

    // 发表观点&分享
    private var fbgdAndFxViewId = "cn.xuexi.android:id/general_card_title_id"
    private var fbgdAndFx: AccessibilityNodeInfo? = null

    // 欢迎发表你的观点
    private var hyButton: AccessibilityNodeInfo? = null

    // 分享
    private var fxButton: AccessibilityNodeInfo? = null

    // 好观点
    private var hgd: AccessibilityNodeInfo? = null

    // 发布
    private var fb: AccessibilityNodeInfo? = null

    // 百灵
    private var blViewId = "cn.xuexi.android:id/home_bottom_tab_button_ding"
    private var bl: AccessibilityNodeInfo? = null

    // 工作
    private var gzViewId = "cn.xuexi.android:id/home_bottom_tab_button_work"
    private var gz: AccessibilityNodeInfo? = null

    // 学习积分
    private var xxjfViewId = "cn.xuexi.android:id/webview_frame"
    private var xxjf: MutableList<AccessibilityNodeInfo> = mutableListOf()


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
    private var fxFinish = false

    // 发表观点1分
    private var fbgdText = "发表观点"
    private var fbgdBoolean = false
    private var fbgd: AccessibilityNodeInfo? = null
    private var fbgdF: AccessibilityNodeInfo? = null
    private var fbgdClick: AccessibilityNodeInfo? = null
    private var fbgdFinsh = false

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
        step = 1

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // 获取 积分 本地 发表观点&分享 百灵 工作
        if (step == 1) {
            if (rootInActiveWindow != null) {
                // 积分
                if (jf == null) {
                    val temp = rootInActiveWindow.findAccessibilityNodeInfosByViewId(jfViewId)
                    if (temp.size > 0) {
                        jf = temp[0]
                        Log.i(xue, "${jf?.text}积分")
                    }
                }

                // 本地
                if (bd == null) {
                    val temp = rootInActiveWindow.findAccessibilityNodeInfosByText(bdText)
                    if (temp.size > 0) {
                        bd = temp[0].parent
                        Log.i(xue, bdText)
                    }
                }

                // 发表观点&分享
                if (fbgdAndFx == null) {
                    val temp =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId(fbgdAndFxViewId)
                    if (temp.size > 0) {
                        Log.i(xue, "${temp[0].text}")
                        fbgdAndFx = temp[0].parent.parent

                        Log.i(xue, "发表观点&分享")
                    }
                }

                // 百灵
                if (bl == null) {
                    val temp = rootInActiveWindow.findAccessibilityNodeInfosByViewId(blViewId)
                    if (temp.size > 0) {
                        bl = temp[0]
                        Log.i(xue, "百灵")
                    }
                }

                // 工作
                if (gz == null) {
                    val temp = rootInActiveWindow.findAccessibilityNodeInfosByViewId(gzViewId)
                    if (temp.size > 0) {
                        gz = temp[0]
                        Log.i(xue, "工作")
                    }
                }
            }

            if (jf != null && bd != null && fbgdAndFx != null && bl != null && gz != null) {
                step = 2
            }
        }

        // 点击积分进入学习积分
        if (step == 2) {
            jf?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            step = 3
        }

        // 获取学习积分标题
        if (step == 3) {
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
            if (qgyd != null) {
                step = 4
            }
        }

        // 获取学习积分明细
        if (step == 4) {
            recycle(mutableListOf(dl?.parent?.parent!!))
            findDl()
            recycle(mutableListOf(wyxdwz?.parent?.parent!!))
            findWyxdwz()
            recycle(mutableListOf(stxx?.parent?.parent!!))
            findStxx()
            recycle(mutableListOf(stxxsc?.parent?.parent!!))
            findStxxsc()
            recycle(mutableListOf(mrdt?.parent?.parent!!))
            findMrdt()
            recycle(mutableListOf(mzdt?.parent?.parent!!))
            findMzdt()
            recycle(mutableListOf(zxdt?.parent?.parent!!))
            findZxdt()
            recycle(mutableListOf(tzdt?.parent?.parent!!))
            findTzdt()
            recycle(mutableListOf(srs?.parent?.parent!!))
            findSrs()
            recycle(mutableListOf(srdz?.parent?.parent!!))
            findSrdz()
            recycle(mutableListOf(dy?.parent?.parent!!))
            findDy()
            recycle(mutableListOf(fx?.parent?.parent!!))
            findFx()
            recycle(mutableListOf(fbgd?.parent?.parent!!))
            findFbgd()
            recycle(mutableListOf(bdpd?.parent?.parent!!))
            findBdpd()
            recycle(mutableListOf(qgyd?.parent?.parent!!))
            findQgyd()

            if (qgydClick != null) {
                step = 5
            }
        }

        if (step == 5) {
            // 拿到所有积分明细后BACK
            performGlobalAction(GLOBAL_ACTION_BACK)
            step = 6
        }

        if (step == 6 && fbgdClick?.text == "已完成" && fxClick?.text == "已完成") {
            step = 8888
        }

        if (rootInActiveWindow != null) {
            val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("欢迎发表你的观点")
            if (temp.size > 0) {

                hyButton = temp[0]

                fxButton = hyButton?.parent?.getChild(3)
            }
        }

        if (step == 6 && fbgdClick?.text != "已完成") {
            fbgdAndFx?.performAction(AccessibilityNodeInfo.ACTION_CLICK)

            if (hyButton != null) {
                step = 7
            }

        } else if (step == 6 && fbgdClick?.text == "已完成") {
            fbgdAndFx?.performAction(AccessibilityNodeInfo.ACTION_CLICK)

            if (hyButton != null) {
                step = 10
            }
        }

        if (step == 7) {
            val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("欢迎发表你的观点")
            if (temp.size > 0) {
                if (temp[0].isClickable) {
                    temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
            }
            recycle(mutableListOf(rootInActiveWindow))
            findGd()
            if (hgd != null && fb != null) {
                step = 8
            }
        }

        // 发布观点
        if (step == 8) {
            val bundle = Bundle()
            bundle.putCharSequence(
                AccessibilityNodeInfo
                    .ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "学习强国！"
            )
            hgd?.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle)
            fb?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            step = 9
        }

        // 删除观点 2022年4月14日
        if (step == 9) {
            step = 10
        }

        if (step >= 10 && step <= 11) {
            fxButton?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            performGlobalAction(GLOBAL_ACTION_BACK)
            step++
        }

        if (step == 12) {
            performGlobalAction(GLOBAL_ACTION_BACK)
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
    }

    // 查找登录
    private fun findDl() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    dlF = ani
                }
            }
            if (ani.isClickable) {
                dlClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找我要选读文章
    private fun findWyxdwz() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    wyxdwzF = ani
                }
            }
            if (ani.isClickable) {
                wyxdwzClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找视听学习
    private fun findStxx() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    stxxF = ani
                }
            }
            if (ani.isClickable) {
                stxxClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找视听学习时长
    private fun findStxxsc() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    stxxscF = ani
                }
            }
            if (ani.isClickable) {
                stxxscClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找每日答题
    private fun findMrdt() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    mrdtF = ani
                }
            }
            if (ani.isClickable) {
                mrdtClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找每周答题
    private fun findMzdt() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    mzdtF = ani
                }
            }
            if (ani.isClickable) {
                mzdtClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找专项答题
    private fun findZxdt() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    zxdtF = ani
                }
            }
            if (ani.isClickable) {
                zxdtClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找挑战答题
    private fun findTzdt() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    tzdtF = ani
                }
            }
            if (ani.isClickable) {
                tzdtClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找四人赛
    private fun findSrs() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    srsF = ani
                }
            }
            if (ani.isClickable) {
                srsClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找双人对战
    private fun findSrdz() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    srdzF = ani
                }
            }
            if (ani.isClickable) {
                srdzClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找订阅
    private fun findDy() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    dyF = ani
                }
            }
            if (ani.isClickable) {
                dyClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找分享
    private fun findFx() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    fxF = ani
                }
            }
            if (ani.isClickable) {
                fxClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找发表观点
    private fun findFbgd() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    fbgdF = ani
                }
            }
            if (ani.isClickable) {
                fbgdClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找本地频道
    private fun findBdpd() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    bdpdF = ani
                }
            }
            if (ani.isClickable) {
                bdpdClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找强国运动
    private fun findQgyd() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text.contains("已获")) {
                    qgydF = ani
                }
            }
            if (ani.isClickable) {
                qgydClick = ani
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    // 查找 好观点将会被优先展示
    private fun findGd() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                when (ani.text) {
                    "好观点将会被优先展示" -> hgd = ani
                    "发布" -> fb = ani
                }
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }
}
