package com.zhouqingbiao.acc

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.os.SystemClock.sleep
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi

class AccService : AccessibilityService() {
    private var step = "开始获取积分"

    private var dl: AccessibilityNodeInfo? = null
    private var dlF: AccessibilityNodeInfo? = null
    private var dlClick: AccessibilityNodeInfo? = null

    private var wyxdwz: AccessibilityNodeInfo? = null
    private var wyxdwzF: AccessibilityNodeInfo? = null
    private var wyxdwzClick: AccessibilityNodeInfo? = null

    private var stxx: AccessibilityNodeInfo? = null
    private var stxxF: AccessibilityNodeInfo? = null
    private var stxxClick: AccessibilityNodeInfo? = null

    private var stxxsc: AccessibilityNodeInfo? = null
    private var stxxscF: AccessibilityNodeInfo? = null
    private var stxxscClick: AccessibilityNodeInfo? = null

    private var mrdt: AccessibilityNodeInfo? = null
    private var mrdtF: AccessibilityNodeInfo? = null
    private var mrdtClick: AccessibilityNodeInfo? = null

    private var mzdt: AccessibilityNodeInfo? = null
    private var mzdtF: AccessibilityNodeInfo? = null
    private var mzdtClick: AccessibilityNodeInfo? = null

    private var zxdt: AccessibilityNodeInfo? = null
    private var zxdtF: AccessibilityNodeInfo? = null
    private var zxdtClick: AccessibilityNodeInfo? = null

    private var tzdt: AccessibilityNodeInfo? = null
    private var tzdtF: AccessibilityNodeInfo? = null
    private var tzdtClick: AccessibilityNodeInfo? = null

    private var srs: AccessibilityNodeInfo? = null
    private var srsF: AccessibilityNodeInfo? = null
    private var srsClick: AccessibilityNodeInfo? = null

    private var srdz: AccessibilityNodeInfo? = null
    private var srdzF: AccessibilityNodeInfo? = null
    private var srdzClick: AccessibilityNodeInfo? = null

    private var dy: AccessibilityNodeInfo? = null
    private var dyF: AccessibilityNodeInfo? = null
    private var dyClick: AccessibilityNodeInfo? = null

    private var fx: AccessibilityNodeInfo? = null
    private var fxF: AccessibilityNodeInfo? = null
    private var fxClick: AccessibilityNodeInfo? = null

    private var fbgd: AccessibilityNodeInfo? = null
    private var fbgdF: AccessibilityNodeInfo? = null
    private var fbgdClick: AccessibilityNodeInfo? = null

    private var bdpd: AccessibilityNodeInfo? = null
    private var bdpdF: AccessibilityNodeInfo? = null
    private var bdpdClick: AccessibilityNodeInfo? = null

    private var qgyd: AccessibilityNodeInfo? = null
    private var qgydF: AccessibilityNodeInfo? = null
    private var qgydClick: AccessibilityNodeInfo? = null

    // 欢迎发表你的观点控件
    var hyfbndgdKj: AccessibilityNodeInfo? = null

    // 分享控件
    var fxKj: AccessibilityNodeInfo? = null

    // 好观点将会被优先展示控件
    var hgdjhbyxzsKj: AccessibilityNodeInfo? = null

    // 发布控件
    var fbKj: AccessibilityNodeInfo? = null

    // 删除控件
    var scKj: AccessibilityNodeInfo? = null

    // 分享到学习强国
    var fxdxxqg: AccessibilityNodeInfo? = null

    // 分享次数
    var fxcs = 0

    // 视频次数
    var spcs = 0

    // 工作
    var gz: AccessibilityNodeInfo? = null

    // 文章次数
    var wzcs = 0

    // 积分
    private var jfViewId = "cn.xuexi.android:id/comm_head_xuexi_score"
    private var jf: AccessibilityNodeInfo? = null

    override fun onServiceConnected() {
        super.onServiceConnected()
        // 初始化
        step = "开始获取积分"
    }


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (step == "开始获取积分") {
            if (rootInActiveWindow != null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/comm_head_xuexi_score")
                if (temp.size > 0) {
                    // 点击积分
                    if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "开始获取积分标题控件"
                    }
                }
            }
        }
        if (step == "开始获取积分标题控件") {
            if (rootInActiveWindow != null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame")
                if (temp.size > 0) {
                    // webview_frame必须循环获取
                    recycle(temp)
                    findXxjfBt()
                    if (qgyd != null) {
                        step = "开始获取积分明细控件"
                    }
                }
            }
        }
        if (step == "开始获取积分明细控件") {
            if (rootInActiveWindow != null) {
                dlF = dl!!.parent.parent.getChild(2)
                dlClick = dl!!.parent.parent.getChild(3)

                wyxdwzF = wyxdwz!!.parent.parent.getChild(2)
                wyxdwzClick = wyxdwz!!.parent.parent.getChild(3)

                stxxF = stxx!!.parent.parent.getChild(2)
                stxxClick = stxx!!.parent.parent.getChild(3)

                stxxscF = stxxsc!!.parent.parent.getChild(2)
                stxxscClick = stxxsc!!.parent.parent.getChild(3)

                mrdtF = mrdt!!.parent.parent.getChild(2)
                mrdtClick = mrdt!!.parent.parent.getChild(3)

                mzdtF = mzdt!!.parent.parent.getChild(2)
                mzdtClick = mzdt!!.parent.parent.getChild(3)

                zxdtF = zxdt!!.parent.parent.getChild(2)
                zxdtClick = zxdt!!.parent.parent.getChild(3)

                tzdtF = tzdt!!.parent.parent.getChild(2)
                tzdtClick = tzdt!!.parent.parent.getChild(3)

                srsF = srs!!.parent.parent.getChild(2)
                srsClick = srs!!.parent.parent.getChild(3)

                srdzF = srdz!!.parent.parent.getChild(2)
                srdzClick = srdz!!.parent.parent.getChild(3)

                dyF = dy!!.parent.parent.getChild(2)
                dyClick = dy!!.parent.parent.getChild(3)

                fxF = fx!!.parent.parent.getChild(2)
                fxClick = fx!!.parent.parent.getChild(3)

                fbgdF = fbgd!!.parent.parent.getChild(2)
                fbgdClick = fbgd!!.parent.parent.getChild(3)

                bdpdF = bdpd!!.parent.parent.getChild(2)
                bdpdClick = bdpd!!.parent.parent.getChild(3)

                qgydF = qgyd!!.parent.parent.getChild(2)
                qgydClick = qgyd!!.parent.parent.getChild(3)
            }
            if (qgydClick != null) {
                performGlobalAction(GLOBAL_ACTION_BACK)
                step = "开始发表观点"
            }
        }
        if (step == "开始发表观点") {
            if (fbgdClick?.text != "已完成" || fxClick?.text != "已完成") {
                if (rootInActiveWindow != null) {
                    val temp =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/general_card_title_id")
                    if (temp.size > 0) {
                        if (temp[0].parent.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            step = "获取欢迎发表你的观点和分享控件"
                        }
                    }
                }
            }
            if (fbgdClick?.text == "已完成" || fxClick?.text == "已完成") {
                step = "开始视听学习"
            }
        }
        if (step == "获取欢迎发表你的观点和分享控件") {
            if (rootInActiveWindow != null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/BOTTOM_LAYER_VIEW_ID")
                if (temp.size > 0) {
                    hyfbndgdKj = temp[0].getChild(0)
                    fxKj = temp[0].getChild(3)
                    if (hyfbndgdKj != null && fxKj != null) {
                        step = "获取好观点将会被优先展示和发布控件"
                    }
                }

            }
        }
        if (step == "获取好观点将会被优先展示和发布控件") {
            // 只能在此点击好观点将会被优先展示控件
            hyfbndgdKj?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            if (rootInActiveWindow != null) {
                if (hgdjhbyxzsKj == null && fbKj == null) {
                    recycle(mutableListOf(rootInActiveWindow))
                    findHgdAndFb()
                }
                if (hgdjhbyxzsKj != null && fbKj != null) {
                    step = "开始好观点将会被优先展示"
                }
            }
        }
        if (step == "开始好观点将会被优先展示") {
            if (rootInActiveWindow != null) {
                val bundle = Bundle()
                bundle.putCharSequence(
                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    "学习强国！"
                )
                if (hgdjhbyxzsKj?.performAction(
                        AccessibilityNodeInfo.ACTION_SET_TEXT,
                        bundle
                    ) == true
                ) {
                    if (fbKj?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                        step = "开始删除"
                    }
                }
            }
        }
        if (step == "开始删除") {
            if (rootInActiveWindow != null) {
                if (scKj == null) {
                    recycle(mutableListOf(rootInActiveWindow))
                    findSc()
                    if (scKj?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                        step = "开始确认"
                    }
                }
            }
        }
        if (step == "开始确认") {
            if (rootInActiveWindow != null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/button1")
                if (temp.size > 0) {
                    if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "开始分享"
                    }
                }
            }
        }
        if (step == "开始分享" && fxcs <= 2) {
            if (rootInActiveWindow != null) {
                if (fxKj?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                    step = "获取分享到学习强国控件"
                }
            }
        }
        if (step == "获取分享到学习强国控件") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("分享到学习强国")
                if (temp.size > 0) {
                    if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "获取选择联系人控件"
                    }
                }
            }
        }
        if (step == "获取选择联系人控件") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("选择联系人")
                if (temp.size > 0) {
                    if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                        fxcs++
                        if (fxcs == 1) {
                            step = "开始分享"
                        }
                        if (fxcs == 2) {
                            step = "返回工作"
                        }
                    }
                }
            }
        }
        if (step == "返回工作") {
            if (rootInActiveWindow != null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/BOTTOM_LAYER_VIEW_ID")
                if (temp.size > 0) {
                    if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                        step = "开始视听学习"
                    }
                }
            }
        }
        if (step == "开始视听学习") {
            if (stxxClick?.text != "已完成") {
                if (rootInActiveWindow != null) {
                    val temp =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/home_bottom_tab_button_ding")
                    if (temp.size > 0) {
                        if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            step = "开始播放视听"
                        }
                    }
                }
            }
            if (stxxClick?.text == "已完成") {
                step = "开始本地频道"
            }
        }
        if (step == "开始播放视听") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("推荐")
                if (temp.size > 0) {
                    println(temp.size)
                    val temp1 = temp[1].parent.parent.parent.parent
                    val temp2 = temp1.getChild(1).getChild(0).getChild(0).getChild(1)
                    val temp3 = temp2.getChild(0).getChild(0).getChild(1).getChild(0)
                    val temp4 = temp3.getChild(1).getChild(1).getChild(0)
                    if (temp4.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "开始视听次数"
                    }
                }
            }
        }
        @RequiresApi(Build.VERSION_CODES.N)
        if (step == "开始视听次数" && spcs <= 6) {
            sleep(8000)
            spcs++
            if (spcs < 6) {
                val builder = GestureDescription.Builder()

                val path = Path()
                path.moveTo(540F, 1800F)
                path.lineTo(540F, 200F)

                val stroke = GestureDescription.StrokeDescription(path, 20, 500)
                builder.addStroke(stroke)

                val gestureResultCallback =
                    object : AccessibilityService.GestureResultCallback() {
                        override fun onCompleted(gestureDescription: GestureDescription?) {
                        }

                        override fun onCancelled(gestureDescription: GestureDescription?) {
                        }
                    }
                dispatchGesture(builder.build(), gestureResultCallback, null)
            }
            if (spcs == 6) {
                if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                    step = "点击工作"
                }
            }
        }
        if (step == "点击工作") {
            if (gz == null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/home_bottom_tab_button_work")
                if (temp.size > 0) {
                    gz = temp[0]
                    if (gz?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                        step = "开始本地频道"
                    }
                }
            }
        }
        if (step == "开始本地频道") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江")
                if (temp.size > 0) {
                    if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "开始浙江卫视"
                    }
                }
            }
        }
        if (step == "开始浙江卫视") {
            if (bdpdClick?.text != "已完成") {
                if (rootInActiveWindow != null) {
                    val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江卫视")
                    if (temp.size > 0) {
                        if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            step = "获取看电视控件"
                        }
                    }
                }
            }
            if (bdpdClick?.text == "已完成") {
                step = "开始浙江之声综合广播"
            }
        }
        if (step == "获取看电视控件") {
            if (rootInActiveWindow != null) {
                // 浙江卫视
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("看电视")
                if (temp.size > 0) {
                    step = "返回本地"
                }
            }
        }

        if (step == "返回本地") {
            sleep(1000)
            if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                step = "开始浙江之声综合广播"
            }
        }
        if (step == "开始浙江之声综合广播") {
            if (stxxscClick?.text != "已完成") {
                sleep(1000)
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江之声综合广播")
                if (temp.size > 0) {
                    if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        sleep(1000)
                        if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                            step = "开始阅读文章"
                        }
                    }
                }
            }
            if (stxxscClick?.text == "已完成") {
                step = "开始阅读文章"
            }
        }

        if (step == "开始阅读文章" && wzcs <= 6) {
            if (wyxdwzClick?.text != "已完成") {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/general_card_title_id")
                if (temp.size > 0) {
                    if (temp[0].parent.parent.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        if (wzcs < 6) {
                            sleep(8000)
                            performGlobalAction(GLOBAL_ACTION_BACK)
                            sleep(1000)
                            rootInActiveWindow.findAccessibilityNodeInfosByText("浙江")[0].parent.performAction(
                                AccessibilityNodeInfo.ACTION_CLICK
                            )
                            sleep(1000)
                        } else {
                            sleep(360000)
                            performGlobalAction(GLOBAL_ACTION_BACK)
                            sleep(1000)
                            step = "开始答题"
                        }
                    }
                    wzcs++
                }
            }
            if (wyxdwzClick?.text == "已完成") {
                step = "开始答题"
            }
        }
        if (step == "开始答题") {
            if (rootInActiveWindow != null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/comm_head_xuexi_score")
                if (temp.size > 0) {
                    if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "开始每日答题"
                    }
                }
            }
        }
        // 开始答题
        if (step == "开始每日答题") {
            if (rootInActiveWindow != null) {

//                val temp = rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/comm_head_xuexi_score")
//                if (temp.size>0){
//                    if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)){
//                        step = "开始答题"
//                    }
//                }
            }
        }
        // wode
//        cn.xuexi.android:id/comm_head_xuexi_mine
        // 积分
//        cn.xuexi.android:id/comm_head_xuexi_score
        // 订阅
//     cn.xuexi.android:id/my_subscribe_tv
        // 我要答题
//        cn.xuexi.android:id/user_item_name

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
    private fun recycle(mlani: MutableList<AccessibilityNodeInfo>) {
        // 临时可变List
        val mutableList: MutableList<AccessibilityNodeInfo> =
            mutableListOf()

        // 开始遍历
        mlani.forEach { ani ->
            // 有子节点才进行添加操作
            if (ani.childCount > 0) {
                (0 until ani.childCount).forEach { index ->
                    // 同时添加
                    if (ani.getChild(index) != null) {
                        mutableListAccessibilityNodeInfo.add(ani.getChild(index))
                        mutableList.add(ani.getChild(index))
                    }
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
                    "登录" -> dl = ani
                    "我要选读文章" -> wyxdwz = ani
                    "视听学习" -> stxx = ani
                    "视听学习时长" -> stxxsc = ani
                    "每日答题" -> mrdt = ani
                    "每周答题" -> mzdt = ani
                    "专项答题" -> zxdt = ani
                    "挑战答题" -> tzdt = ani
                    "四人赛" -> srs = ani
                    "双人对战" -> srdz = ani
                    "订阅" -> dy = ani
                    "分享" -> fx = ani
                    "发表观点" -> fbgd = ani
                    "本地频道" -> bdpd = ani
                    "强国运动" -> qgyd = ani
                }
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    /**
     * 获取好观点将会被优先展示和发布控件
     */
    private fun findHgdAndFb() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                when (ani.text) {
                    "好观点将会被优先展示" -> hgdjhbyxzsKj = ani
                    "发布" -> fbKj = ani
                }
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    /**
     * 获取删除控件
     */
    private fun findSc() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                when (ani.text) {
                    "删除" -> scKj = ani
                }
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    /**
     * 根据字符串查找控件
     */
    private fun findByString(
        mutableList: MutableList<AccessibilityNodeInfo>,
        string: String
    ): AccessibilityNodeInfo? {
        recycle(mutableList)
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.text == string) {
                    return ani
                }
            }
        }
        return null
    }
}