package com.zhouqingbiao.acc

import android.accessibilityservice.AccessibilityService
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AccService : AccessibilityService() {

    private var xue = "学"
    private var step = "开始"

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

    override fun onServiceConnected() {
        super.onServiceConnected()
        // 初始化
        step = "开始"
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (rootInActiveWindow != null) {
            // 发表观点 分享
            if (step == "开始") {
                if (fbgdAndFx == null) {
                    val temp =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/general_card_title_id")
                    if (temp.size > 0) {
                        fbgdAndFx = temp[0].parent.parent
                        fbgdAndFx?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        step = "发表观点 分享"
                    }
                }
            }
            // 发表观点 分享
            if (step == "发表观点 分享") {
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
            if (step == "发表观点") {
                fbgd?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                // 好观点将会被优先展示 发布
                if (hgd == null && fb == null) {
                    recycle(mutableListOf(rootInActiveWindow))
                    findHgdAndFb()
                }
                if (hgd != null && fx != null) {
                    step = "好观点将会被优先展示"
                }
            }
            if (step == "好观点将会被优先展示") {
                if (hgd != null && fb != null) {
                    val bundle = Bundle()
                    bundle.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                        "学习强国！"
                    )
                    hgd?.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle)
                    if (fb?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                        step = "删除"
                    }
                }
            }

            if (step == "删除") {
                if (sc == null) {
                    recycle(mutableListOf(rootInActiveWindow))
                    findSc()
                    if (sc?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true) {
                        step = "确认"
                    }
                }
            }
            if (step == "确认") {
                if (qr == null) {
                    val temp =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/button1")
                    if (temp.size > 0) {
                        if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            performGlobalAction(GLOBAL_ACTION_BACK)
                        }
                    }
                }
            }
        }
//        if (rootInActiveWindow != null) {
//            // 发表观点 分享
//            if (fbgdAndFx == null) {
//                val temp =
//                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/general_card_title_id")
//                if (temp.size > 0) {
//                    fbgdAndFx = temp[0].parent.parent
//                }
//            }
//            // 发表观点 分享
//            if (fbgd == null && fx == null) {
//                val temp =
//                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/BOTTOM_LAYER_VIEW_ID")
//                if (temp.size > 0) {
//                    fbgd = temp[0].getChild(0)
//                    fx = temp[0].getChild(3)
//                }
//            }
//            // 确认
//            if (qr == null) {
//                val temp =
//                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/button1")
//                if (temp.size > 0) {
//                    qr = temp[0]
//                }
//            }
//        }
//        if (step == "开始") {
//            if (fbgdAndFx != null) {
//                fbgdAndFx!!.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                if (fbgd != null && fx != null) {
//                    step = "发表观点 分享"
//                }
//            }
//        }
//        if (step == "发表观点 分享") {
//            if (fbgd != null) {
//                fbgd!!.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//            }
//
//            // 好观点将会被优先展示 发布
//            if (hgd == null && fb == null) {
//                recycle(mutableListOf(rootInActiveWindow))
//                findHgdAndFb()
//            }
//
//            if (hgd != null && fx != null) {
//                step = "好观点将会被优先展示"
//            }
//        }

//        if (step == "好观点将会被优先展示") {
//            var aa = false
//            if (hgd != null && fb != null) {
//                val bundle = Bundle()
//                bundle.putCharSequence(
//                    AccessibilityNodeInfo
//                        .ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "学习强国！"
//                )
//                hgd?.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle)
//                aa = fb?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true
//            }
//
//            if (aa) {
//                // 删除
//                if (sc == null) {
//                    val temp =
//                        rootInActiveWindow.findAccessibilityNodeInfosByText("删除")
//                    if (temp.size > 0) {
//                        sc = temp[0]
//                    }
//                }
//
//                if (sc != null) {
//                    step = "删除"
//                }
//            }
//
//        }
//        if (step == "删除") {
//            if (sc != null) {
//                sc?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//            }
//            if (qr != null) {
//                step = "确认"
//            }
//        }
//        if (step == "确认") {
//            if (qr != null) {
//                qr?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//            }
//        }
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
