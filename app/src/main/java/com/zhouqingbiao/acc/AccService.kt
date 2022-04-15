package com.zhouqingbiao.acc

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.os.SystemClock.sleep
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi

class AccService : AccessibilityService() {

    private var xue = "学"
    private var step = "点击工作"

    // 发表观点 分享
    var fbgdAndFx: AccessibilityNodeInfo? = null

    // 发表观点
    var fbgd: AccessibilityNodeInfo? = null

    // 分享
    var fx: AccessibilityNodeInfo? = null

    // 好观点
    var hgd: AccessibilityNodeInfo? = null

    // 发布
    var fb: AccessibilityNodeInfo? = null

    // 删除
    var sc: AccessibilityNodeInfo? = null

    // 确认
    var qr: AccessibilityNodeInfo? = null

    // 分享到学习强国
    var fxd: AccessibilityNodeInfo? = null

    // 视频次数
    var spcs = 0

    // 工作
    var gz: AccessibilityNodeInfo? = null

    // 本地
    var bd: AccessibilityNodeInfo? = null

    // 文章次数
    var wzcs = 0

    override fun onServiceConnected() {
        super.onServiceConnected()
        // 初始化
        step = "点击工作"
    }


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // 点击需要发表观点和分享文章
        if (step == "开始") {
            if (rootInActiveWindow != null) {
                if (fbgdAndFx == null) {
                    val temp =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/general_card_title_id")
                    if (temp.size > 0) {
                        fbgdAndFx = temp[0].parent.parent
                        if (fbgdAndFx?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                            step = "发表观点和分享"
                        }
                    }
                }
            }
        }
        // 拿到发表观点和分享控件
        if (step == "发表观点和分享") {
            if (rootInActiveWindow != null) {
                if (fbgd == null && fx == null) {
                    val temp =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/BOTTOM_LAYER_VIEW_ID")
                    if (temp.size > 0) {
                        fbgd = temp[0].getChild(0)
                        fx = temp[0].getChild(3)
                        step = "发表观点"
                    }
                }
            }
        }
        // 点击发表观点控件
        // 获取好观点将会被优先展示和发布控件
        if (step == "发表观点") {
            if (rootInActiveWindow != null) {
                fbgd?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                // 获取好观点将会被优先展示和发布控件
                if (hgd == null && fb == null) {
                    recycle(mutableListOf(rootInActiveWindow))
                    findHgdAndFb()
                }
                if (hgd != null && fx != null) {
                    step = "好观点将会被优先展示"
                }
            }
        }
        // 发布观点
        if (step == "好观点将会被优先展示") {
            if (rootInActiveWindow != null) {
                if (hgd != null && fb != null) {
                    val bundle = Bundle()
                    bundle.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                        "学习强国！"
                    )
                    if (hgd?.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle) == true) {
                        if (fb?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                            step = "删除"
                        }
                    }
                }
            }
        }
        // 删除观点
        if (step == "删除") {
            if (rootInActiveWindow != null) {
                if (sc == null) {
                    recycle(mutableListOf(rootInActiveWindow))
                    findSc()
                    if (sc?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                        step = "确认"
                    }
                }
            }
        }
        // 确认删除观点
        if (step == "确认") {
            if (rootInActiveWindow != null) {
                if (qr == null) {
                    val temp =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/button1")
                    if (temp.size > 0) {
                        if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            step = "开始分享第一次"
                        }
                    }
                }
            }
        }
        // 开始分享第一次
        if (step == "开始分享第一次") {
            if (rootInActiveWindow != null) {
                if (fx != null) {
                    if (fx?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                        step = "获取分享到控件第一次"
                    }
                }
            }
        }
        // 点击分享到控件第一次
        if (step == "获取分享到控件第一次") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("分享到学习强国")
                if (temp.size > 0) {
                    if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "选择联系人第一次"
                    }
                }
            }
        }
        // 返回第一次
        if (step == "选择联系人第一次") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("选择联系人")
                if (temp.size > 0) {
                    if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                        step = "开始分享第二次"
                    }
                }
            }
        }
        // 开始分享第二次
        if (step == "开始分享第二次") {
            if (rootInActiveWindow != null) {
                if (fx != null) {
                    if (fx?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                        step = "获取分享到控件第二次"
                    }
                }
            }
        }
        // 点击分享到控件第二次
        if (step == "获取分享到控件第二次") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("分享到学习强国")
                if (temp.size > 0) {
                    if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "选择联系人第二次"
                    }
                }
            }
        }
        // 返回第二次
        if (step == "选择联系人第二次") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("选择联系人")
                if (temp.size > 0) {
                    if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                        step = "返回主页"
                    }
                }
            }
        }
        // 返回主页
        if (step == "返回主页") {
            if (rootInActiveWindow != null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/BOTTOM_LAYER_VIEW_ID")
                if (temp.size > 0) {
                    if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                        step = "开始视频"
                    }
                }
            }
        }
        // 开始视频
        if (step == "开始视频") {
            if (rootInActiveWindow != null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/home_bottom_tab_button_ding")
                if (temp.size > 0) {
                    if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "播放视频"
                    }
                }
            }
        }
        // 播放视频
        if (step == "播放视频") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("推荐")
                if (temp.size > 0) {
                    val temp1 = temp[1].parent.parent.parent.parent
                    val temp2 = temp1.getChild(1).getChild(0).getChild(0).getChild(1)
                    val temp3 = temp2.getChild(0).getChild(0).getChild(1).getChild(0)
                    val temp4 = temp3.getChild(0).getChild(1).getChild(0)
                    if (temp4.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "滑动视频"
                    }
                }
            }
        }
        // 滑动视频
        @RequiresApi(Build.VERSION_CODES.N)
        if (step == "滑动视频" && spcs <= 6) {
            sleep(8000)
            spcs++
            Log.i(xue, "$spcs")
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
            // 工作
            if (gz == null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/home_bottom_tab_button_work")
                if (temp.size > 0) {
                    gz = temp[0]
                    if (gz?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                        step = "开始本地"
                    }
                }
            }

        }
        if (step == "开始本地") {
            if (rootInActiveWindow != null) {
                // 本地
                if (bd == null) {
                    val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江")
                    if (temp.size > 0) {
                        bd = temp[0].parent
                        if (bd?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                            step = "浙江卫视"
                        }
                    }
                }
            }
        }
        if (step == "浙江卫视") {
            if (rootInActiveWindow != null) {
                // 浙江卫视
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江卫视")
                if (temp.size > 0) {
                    if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "看电视"
                    }
                }
            }
        }
        if (step == "看电视") {
            if (rootInActiveWindow != null) {
                // 浙江卫视
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江卫视")
                if (temp.size > 0) {
                    step = "返回看电视"
                }
            }
        }

        if (step == "返回看电视") {
            sleep(1000)
            if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                step = "浙江之声综合广播"
            }
        }
        if (step == "浙江之声综合广播") {
            sleep(1000)
            val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江之声综合广播")
            if (temp.size > 0) {
                if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    sleep(1000)
                    if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                        step = "阅读文章"
                    }
                }
            }
        }

        if (step == "阅读文章" && wzcs <= 6) {
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
                        println("完成阅读时长")
                        sleep(360000)
                        performGlobalAction(GLOBAL_ACTION_BACK)
                        sleep(1000)
                    }
                }
                wzcs++
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
     * 好观点将会被优先展示 发布
     */
    private fun findHgdAndFb() {
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

    /**
     * 删除
     */
    private fun findSc() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                when (ani.text) {
                    "删除" -> sc = ani
                }
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }
}