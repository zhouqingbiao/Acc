package com.zhouqingbiao.acc

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Bitmap
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.os.SystemClock.sleep
import android.view.Display
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.googlecode.tesseract.android.TessBaseAPI
import com.zhouqingbiao.acc.dao.MrdtDao
import com.zhouqingbiao.acc.dao.TzdtDao
import com.zhouqingbiao.acc.database.XxqgDatabase
import com.zhouqingbiao.acc.entity.Mrdt
import com.zhouqingbiao.acc.entity.Tzdt
import org.jsoup.Jsoup
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


@RequiresApi(Build.VERSION_CODES.N)
class AccService : AccessibilityService() {
    var tessBaseAPI = TessBaseAPI()

    var xxqgDatabase: XxqgDatabase? = null

    var mrdtDao: MrdtDao? = null

    var tzdtDao: TzdtDao? = null

    /*
     * Gets the number of available cores
     * (not always the same as the maximum number of cores)
     */
    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()

    // Instantiates the queue of Runnables as a LinkedBlockingQueue
    private val workQueue: BlockingQueue<Runnable> =
        LinkedBlockingQueue<Runnable>()

    // Sets the amount of time an idle thread waits before terminating
    private val KEEP_ALIVE_TIME = 1L

    // Sets the Time Unit to seconds
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    // Creates a thread pool manager
    private val threadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(
        // Initial pool size
        NUMBER_OF_CORES,
        // Max pool size
        NUMBER_OF_CORES,
        KEEP_ALIVE_TIME,
        KEEP_ALIVE_TIME_UNIT,
        workQueue
    )

    private var step = "开始获取积分"

    private var ywc = "已完成"

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
    private var hyfbndgdKj: AccessibilityNodeInfo? = null

    // 分享控件
    private var fxKj: AccessibilityNodeInfo? = null

    // 好观点将会被优先展示控件
    private var hgdjhbyxzsKj: AccessibilityNodeInfo? = null

    // 发布控件
    private var fbKj: AccessibilityNodeInfo? = null

    // 删除控件
    private var scKj: AccessibilityNodeInfo? = null

    // 分享次数
    private var fxcs = 0

    // 视频次数
    private var spcs = 0

    // 工作
    private var gz: AccessibilityNodeInfo? = null

    // 文章次数
    private var wzcs = 0

    private var roomId: Long = 0

    var t = ""
    var ts = ""
    var da = ""

    override fun onServiceConnected() {
        super.onServiceConnected()

        // 初始化

        val inputStream = applicationContext.assets.open("chi_sim.traineddata")
        var file = File(filesDir.path, File.separator + "tessdata")
        if (!file.exists()) {
            file.mkdirs()
        }
        file = File(file.path + File.separator + "chi_sim.traineddata")
        if (!file.exists()) {
            val fileOutputStream = FileOutputStream(file.path)
            val buffer = ByteArray(1024)
            var count: Int
            while (inputStream.read(buffer).also { count = it } > 0) {
                fileOutputStream.write(buffer, 0, count)
            }
            fileOutputStream.write(inputStream.read())
            fileOutputStream.flush()
            fileOutputStream.close()
            inputStream.close()
        }

        tessBaseAPI.init(filesDir.path, "chi_sim")


        try {
            applicationContext.assets.open("Xxqg.db")
            xxqgDatabase =
                Room.databaseBuilder(applicationContext, XxqgDatabase::class.java, "Xxqg")
                    .createFromAsset("Xxqg.db").allowMainThreadQueries().build()
        } catch (e: FileNotFoundException) {
            xxqgDatabase =
                Room.databaseBuilder(applicationContext, XxqgDatabase::class.java, "Xxqg")
                    .allowMainThreadQueries().build()
        }

        mrdtDao = xxqgDatabase!!.mrdtDao()

        mrdtDao!!.findByTAndTs("", "")

        tzdtDao = xxqgDatabase!!.tzdtDao()

        tzdtDao!!.findByT("")

        step = "开始获取积分"
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (step == "开始获取积分") {
            if (rootInActiveWindow != null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/comm_head_xuexi_score")
                if (temp.size > 0) {
                    if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "开始获取学习积分标题控件"
                    }
                }
            }
        }
        if (step == "开始获取学习积分标题控件") {
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
                // 删除
                step = "进入我的"
            }
        }
        if (step == "开始发表观点") {
            if (fbgdClick?.text != ywc || fxClick?.text != ywc) {
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
            if (fbgdClick?.text == ywc || fxClick?.text == ywc) {
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
            if (stxxClick?.text != ywc) {
                if (rootInActiveWindow != null) {
                    val temp =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/home_bottom_tab_button_ding")
                    if (temp.size > 0) {
                        if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            sleep(1000)
                            if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                                sleep(1000)
                                step = "开始播放视听"
                            }
                        }
                    }
                }
            }
            if (stxxClick?.text == ywc) {
                step = "开始本地频道"
            }
        }
        if (step == "开始播放视听") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("推荐")
                if (temp.size > 0) {
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
            if (bdpdClick?.text != ywc || stxxscClick?.text != ywc || wyxdwzClick?.text != ywc) {
                if (rootInActiveWindow != null) {
                    val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江")
                    if (temp.size > 0) {
                        if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            step = "开始浙江卫视"
                        }
                    }
                }
            }
            if (bdpdClick?.text == ywc && stxxscClick?.text == ywc && wyxdwzClick?.text == ywc) {
                step = "再次进入积分"
            }
        }
        if (step == "开始浙江卫视") {
            if (rootInActiveWindow != null) {
                val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("浙江卫视")
                if (temp.size > 0) {
                    if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "获取看电视控件"
                    }
                }
            }
        }
        if (step == "获取看电视控件") {
            if (rootInActiveWindow != null) {
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
            if (stxxscClick?.text != ywc) {
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
            if (stxxscClick?.text == ywc) {
                step = "开始阅读文章"
            }
        }
        if (step == "开始阅读文章" && wzcs <= 6) {
            if (wyxdwzClick?.text != ywc) {
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
                            step = "进入我的"
                        }
                    }
                    wzcs++
                }
            }
            if (wyxdwzClick?.text == ywc) {
                step = "进入我的"
            }
        }
        if (step == "进入我的") {
            if (rootInActiveWindow != null) {
                val temp =
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/comm_head_xuexi_mine")
                if (temp.size > 0) {
                    if (temp[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        step = "点击订阅"
                        // 删除
                        step = "点击我要答题"
                    }
                }
            }
        }
        if (step == "点击订阅") {
            sleep(1000)
            if (dyF?.text == "已获0分/每日上限2分" || dyF?.text == "已获1分/每日上限2分") {
                onDispatchGesture(540F, 510F, 0F, 0F, 50, 50)
                step = "点击添加"
            }
            if (dyF?.text == "已获2分/每日上限2分") {
                step = "点击我要答题"
            }
        }
        if (step == "点击添加") {
            sleep(1000)
            onDispatchGesture(990F, 150F, 0F, 0F, 50, 50)
            step = "点击强国号上新"
        }
        if (step == "点击强国号上新") {
            sleep(1000)
            onDispatchGesture(140F, 480F, 0F, 0F, 50, 50)
            step = "点击强国号上新订阅"
        }
        if (step == "点击强国号上新订阅") {
            sleep(1000)
            onDispatchGesture(980F, 340F, 0F, 0F, 50, 50)
            step = "点击地方平台"
        }
        if (step == "点击地方平台") {
            step = if (dyF?.text == "已获0分/每日上限2分") {
                sleep(1000)
                onDispatchGesture(650F, 180F, 0F, 0F, 50, 50)
                "点击地方平台上新"
            } else {
                "从订阅返回我的"
            }
        }
        if (step == "点击地方平台上新") {
            sleep(1000)
            onDispatchGesture(140F, 480F, 0F, 0F, 50, 50)
            step = "点击地方平台上新订阅"
        }
        if (step == "点击地方平台上新订阅") {
            sleep(1000)
            onDispatchGesture(980F, 340F, 0F, 0F, 50, 50)
            step = "从订阅返回我的"
        }
        if (step == "从订阅返回我的") {
            sleep(1000)
            if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                sleep(1000)
                if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                    step = "点击我要答题"
                }
            }
        }
        if (step == "点击我要答题") {
            sleep(1000)
            val temp = rootInActiveWindow.findAccessibilityNodeInfosByText("我要答题")
            if (temp != null) {
                if (temp[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    sleep(1000)
                    step = "进入每日答题"
                }
            }
//            step = "进入每周答题"
//            step = "进入专项答题"
//            step = "进入四人赛"
//            step = "进入双人对战"
//            step = "进入挑战答题"
        }
        if (step == "进入每日答题") {
            val temp = findByText(
                rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                "每日答题", false
            )
            if (temp != null) {
                if (temp.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    sleep(1000)
                    step = "点击查看提示"
                }
            }
            val zql = findByText(
                rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                "正确率：100%", false
            )
            if (zql != null) {
                if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                    sleep(1000)
                    step = "进入每周答题"
                }
            }
        }
        if (step == "点击查看提示") {
            val temp = findByText(
                rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                "查看提示", false
            )
            if (temp != null) {
                if (temp.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    sleep(1000)
                    step = "获取提示"
                }
            }
        }
        if (step == "获取提示") {
            if (rootInActiveWindow != null) {
                val temp = findByText(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "提示", false
                )
                if (temp != null) {
                    t = temp.parent.parent.parent.getChild(0).getChild(0).getChild(0)
                        .getChild(0).text.toString()
                    ts = temp.parent.parent.getChild(1).getChild(0).text.toString()
                    if (ts == "") {
                        val view = temp.parent.parent.getChild(1).getChild(0)
                        (0 until view.childCount).forEach { index ->
                            ts += view.getChild(index).text.toString()
                        }
                    }
                    if (ts == "请观看视频" || ts == "请观看视频。") {
                        ts = temp.parent.parent.parent.getChild(0).getChild(0).getChild(1)
                            .getChild(0).text.toString()
                    }
                    if (ts != "") {
                        val mrdt: List<Mrdt> = mrdtDao!!.findByTAndTs(t, ts)
                        if (mrdt.size > 1) {
                            mrdt.forEach { m ->
                                mrdtDao!!.delete(mrdtDao!!.findById(m.id))
                            }
                            step = "查找每日答案"
                        }
                        if (mrdt.size == 1) {
                            t = mrdt[0].t
                            da = mrdt[0].da
                            step = "选或填每日答题答案"
                            if (da == "") {
                                step = "查找每日答案"
                            }
                        }
                        if (mrdt.isEmpty()) {
                            step = "查找每日答案"
                        }
                        if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                            sleep(1000)
                        }
                    }
                }
            }
        }
        if (step == "查找每日答案") {
            if (t == "单选题" || t == "多选题") {
                val temp = findByText(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "A.", false
                )
                temp?.parent?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                sleep(1000)
                step = "得出每日答题答案"
            }
            if (t == "填空题") {
                val temp = findByClassName(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "android.widget.EditText"
                )
                if (temp.isNotEmpty()) {
                    val bundle = Bundle()
                    bundle.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                        "A"
                    )
                    temp.forEach { t ->
                        t.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle)
                    }
                    sleep(1000)
                    step = "得出每日答题答案"
                }
            }
        }
        if (step == "得出每日答题答案") {
            if (t == "单选题" || t == "多选题") {
                val azMutableMapOf =
                    mutableMapOf(
                        "A." to "",
                        "B." to "",
                        "C." to "",
                        "D." to "",
                        "E." to "",
                        "F." to ""
                    )
                azMutableMapOf.forEach { az ->
                    val temp = findByText(
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                        az.key, false
                    )
                    if (temp != null) {
                        azMutableMapOf[az.key] = temp.parent.getChild(2).text.toString()
                    }
                }
                val qd = findByText(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "确定", false
                )
                if (qd != null) {
                    if (qd.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        sleep(1000)
                        val xyt = findByText(
                            rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                            "下一题", false
                        )
                        if (xyt != null) {
                            val zqda = findByTextOfContains(
                                rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                                "正确答案： ", false
                            )
                            if (zqda != null) {
                                val azString = zqda.text.toString().replace("正确答案： ", "")
                                var zqdaString = ""
                                if (azString.length == 1) {
                                    mrdtDao!!.insert(
                                        Mrdt(
                                            0,
                                            t,
                                            ts,
                                            azMutableMapOf.getValue("$azString.")
                                        )
                                    )
                                    println("$t====$ts====${azMutableMapOf.getValue("$azString.")}")
                                }
                                if (azString.length > 1) {
                                    azString.forEach { az ->
                                        zqdaString += azMutableMapOf.getValue("$az.") + "|"
                                    }
                                    zqdaString = zqdaString.substring(0, zqdaString.length - 1)
                                    mrdtDao!!.insert(Mrdt(0, t, ts, zqdaString))
                                    println("$t====$ts====$zqdaString")
                                }
                                step = "从每日答题返回我要答题"
                            }
                        }
                        if (xyt == null) {
                            mrdtDao!!.insert(Mrdt(0, t, ts, azMutableMapOf.getValue("A.")))
                            println("$t====$ts====${azMutableMapOf.getValue("A.")}")
                            step = "点击查看提示"
                        }
                    }
                }
            }
            if (t == "填空题") {
                val qd = findByText(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "确定", false
                )
                if (qd != null) {
                    if (qd.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        sleep(1000)
                        val zqda = findByTextOfContains(
                            rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                            "正确答案： ", false
                        )
                        if (zqda != null) {
                            val temp = findByClassName(
                                rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                                "android.widget.EditText"
                            )
                            val azString = zqda.text.toString().replace("正确答案： ", "")
                            if (temp.size > 1) {
                                var zqdaString = ""
                                azString.split(" ").forEach { az ->
                                    zqdaString += "$az|"
                                }
                                zqdaString = zqdaString.substring(0, zqdaString.length - 1)
                                mrdtDao!!.insert(Mrdt(0, t, ts, zqdaString))
                                println("$t====$ts====$zqdaString")
                                step = "从每日答题返回我要答题"
                            }
                            if (temp.size == 1) {
                                mrdtDao!!.insert(Mrdt(0, t, ts, azString))
                                println("$t====$ts====$azString")
                                step = "从每日答题返回我要答题"
                            }
                        }
                    }
                }
            }
        }
        if (step == "从每日答题返回我要答题") {
            if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                sleep(1000)
                // onDispatchGesture(340F, 1300F, 0F, 0F, 50, 50)
                val tc = findByTextOfContains(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "退出", false
                )
                if (tc != null) {
                    if (tc.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        sleep(1000)
                        step = "进入每日答题"
                    }
                }
            }
        }
        if (step == "选或填每日答题答案") {
            if (t == "单选题") {
                val temp = findByText(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    da, false
                )
                temp?.parent?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                step = "点击确定"
            }
            if (t == "多选题") {
                val daSplit = da.split("|")
                (daSplit.indices).forEach { index ->
                    val temp = findByText(
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                        daSplit[index], false
                    )
                    temp?.parent?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
                step = "点击确定"
            }
            if (t == "填空题") {
                val temp = findByClassName(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "android.widget.EditText"
                )
                if (da.indexOf("|") != -1) {
                    val daSplit = da.split("|")
                    (daSplit.indices).forEach { index ->
                        val bundle = Bundle()
                        bundle.putCharSequence(
                            AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                            daSplit[index]
                        )
                        temp[index].performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle)
                    }
                    step = "点击确定"
                } else {
                    val bundle = Bundle()
                    bundle.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                        da
                    )
                    temp[0].performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle)
                    step = "点击确定"
                }
            }
        }
        if (step == "点击确定") {
            sleep(1000)
            var temp = findByText(
                rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                "确定", false
            )
            if (temp != null) {
                if (temp.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    sleep(1000)
                    step = "点击查看提示"
                }
            } else {
                temp = findByText(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "下一题", false
                )
                if (temp != null) {
                    mrdtDao!!.delete(mrdtDao!!.findById(roomId))
                    if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                        sleep(1000)
                        // onDispatchGesture(340F, 1300F, 0F, 0F, 50, 50)
                        val tc = findByTextOfContains(
                            rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                            "退出", false
                        )
                        if (tc != null) {
                            if (tc.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                                sleep(1000)
                                step = "进入每日答题"
                            }
                        }
                        // step = "进入每日答题"
                    }
                }
            }
        }
        if (step == "进入每周答题") {
            sleep(1000)
            onDispatchGesture(550F, 700F, 0F, 0F, 50, 50)
            sleep(1000)
            if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                step = "进入专项答题"
            }
        }
        if (step == "进入专项答题") {
            sleep(1000)
            onDispatchGesture(900F, 700F, 0F, 0F, 50, 50)
            sleep(1000)
            if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                step = "进入四人赛"
            }
        }
        if (step == "进入四人赛") {
            sleep(1000)
            val temp =
                findByText(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "排行榜",
                    false
                )
            if (temp != null) {
                if (temp.parent.getChild(8).performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    sleep(1000)
                    step = "点击四人赛"
                }
            }
        }
        if (step == "进入双人对战") {
            sleep(1000)
            val temp =
                findByText(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "排行榜",
                    false
                )
            if (temp != null) {
                if (temp.parent.getChild(9).performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    sleep(1000)
                    step = "点击随机匹配"
                }
            }
        }
        if (step == "点击随机匹配") {
            val temp =
                findByText(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "随机匹配",
                    false
                )
            if (temp != null) {
                if (temp.parent.getChild(0).performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    sleep(1000)
                    step = "选择双人对战答案"
                }
            }
        }
        if (step == "选择双人对战答案") {
            sleep(15000)
            println(step)
            recycle(rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"))
            mutableListAccessibilityNodeInfo.forEach { ani ->
                println("className=${ani.className}")
                println("text=${ani.text}")
                println("contentDescription=${ani.contentDescription}")
            }
            step = "3213213"
        }
        if (step == "进入挑战答题") {
            sleep(1000)
            val temp =
                findByText(
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                    "排行榜",
                    false
                )
            if (temp != null) {
                if (temp.parent.getChild(10).performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    sleep(3000)
                    step = "选择挑战答题答案"
                }
            }
        }
        if (step == "选择挑战答题答案") {
            val listView = findByClassName(
                rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                "android.widget.ListView"
            )[0]
            val random = (0 until listView.childCount).random()
            t = listView.parent.getChild(0).text.toString()
            da = listView.getChild(random).getChild(0).getChild(1).text.toString()
            val tzdt = tzdtDao!!.findByT(t)
            var delete = false
            if (tzdt.size > 1) {
                tzdt.forEach { t ->
                    tzdtDao!!.delete(tzdtDao!!.findById(t.id))
                }
            }
            if (tzdt.size == 1) {
                roomId = tzdt[0].id
                da = tzdt[0].da
                delete = true
            }
            var tzdtString = ""
            (0 until listView.childCount).forEach { index ->
                tzdtString += listView.getChild(index).getChild(0).getChild(1).text.toString() + "|"
            }
            tzdtString = tzdtString.substring(0, tzdtString.length - 1)
            val temp = findByText(
                rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                da,
                false
            )
            if (temp != null) {
                if (temp.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    sleep(3000)
                    val jsbj = findByText(
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.xuexi.android:id/webview_frame"),
                        "结束本局", false
                    )
                    if (jsbj == null) {
                        tzdtDao!!.insert(Tzdt(0, t, da))
                        step = "选择挑战答题答案"
                    } else {
                        if (delete) {
                            tzdtDao!!.delete(tzdtDao!!.findById(roomId))
                        }
                        println(t)
                        println(tzdtString)
                        if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                            sleep(1000)
                            if (performGlobalAction(GLOBAL_ACTION_BACK)) {
                                sleep(1000)
                                step = "进入挑战答题"
                                sleep(8000)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onInterrupt() {
        // 在这里结束tessBaseAPI
        tessBaseAPI.end()
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
        val mutableList: MutableList<AccessibilityNodeInfo> = mutableListOf()

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
                when (ani.text.toString()) {
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
     * dispatchGesture
     */
    private fun onDispatchGesture(
        moveToX: Float,
        moveToY: Float,
        lineToX: Float,
        lineToY: Float,
        startTime: Long,
        duration: Long
    ) {
        // 创建一个手势
        val path = Path()
        // 如果是点击/双击手势
        path.moveTo(moveToX, moveToY)
        // 如果是移动/快速手势
        if (lineToX != 0F && lineToY != 0F) {
            path.lineTo(lineToX, lineToY)
        }
        // startTime 手势开始的时间延迟 毫秒
        // duration 手势持续的时间 毫秒
        // 如果需要快速滑动效果 duration 设置成一个小值
        val stroke = GestureDescription.StrokeDescription(path, startTime, duration)
        // 手势执行回调
        val gestureResultCallback =
            object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                }

                override fun onCancelled(gestureDescription: GestureDescription?) {
                }
            }
        // 声明一个构造器
        val builder = GestureDescription.Builder()
        builder.addStroke(stroke)
        dispatchGesture(builder.build(), gestureResultCallback, null)
    }

    /**
     * 获取好观点将会被优先展示和发布控件
     */
    private fun findHgdAndFb() {
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                when (ani.text.toString()) {
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
                when (ani.text.toString()) {
                    "删除" -> scKj = ani
                }
            }
        }
        mutableListAccessibilityNodeInfo.clear()
    }

    /**
     * 根据ContentDescription查找控件
     */
    private fun findByContentDescription(
        mutableList: MutableList<AccessibilityNodeInfo>,
        contentDescription: String
    ): AccessibilityNodeInfo? {
        recycle(mutableList)
        var temp: AccessibilityNodeInfo? = null
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.contentDescription != null && ani.contentDescription != "") {
                if (ani.contentDescription.toString() == contentDescription) {
                    temp = ani
                }
            }
        }
        mutableListAccessibilityNodeInfo.clear()
        return temp
    }

    /**
     * 根据Text查找控件
     */
    private fun findByText(
        mutableList: MutableList<AccessibilityNodeInfo>,
        text: String, output: Boolean
    ): AccessibilityNodeInfo? {
        recycle(mutableList)
        var temp: AccessibilityNodeInfo? = null
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null && ani.text.toString() != "") {
                if (ani.text.toString() == text) {
                    temp = ani
                }
                if (output) {
                    println(ani.text)
                }
            }
        }
        mutableListAccessibilityNodeInfo.clear()
        return temp
    }

    /**
     * 根据Text查找控件
     * Contains
     */
    private fun findByTextOfContains(
        mutableList: MutableList<AccessibilityNodeInfo>,
        text: String, output: Boolean
    ): AccessibilityNodeInfo? {
        recycle(mutableList)
        var temp: AccessibilityNodeInfo? = null
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null && ani.text.toString() != "") {
                if (ani.text.toString().contains(text)) {
                    temp = ani
                }
                if (output) {
                    println(ani.text)
                }
            }
        }
        mutableListAccessibilityNodeInfo.clear()
        return temp
    }

    /**
     * 根据ClassName查找控件
     */
    private fun findByClassName(
        mutableList: MutableList<AccessibilityNodeInfo>,
        className: String
    ): MutableList<AccessibilityNodeInfo> {
        recycle(mutableList)
        // 临时可变List
        val mutableList: MutableList<AccessibilityNodeInfo> =
            mutableListOf()
        mutableListAccessibilityNodeInfo.forEach { ani ->
            if (ani.text != null) {
                if (ani.className.toString() == className) {
                    mutableList.add(ani)
                }
            }
        }
        mutableListAccessibilityNodeInfo.clear()
        return mutableList
    }

    /**
     * TakeScreenshotToFile
     */
    private fun takeScreenshotToFile(string: String, index: Int) {
        var mBitmap: Bitmap?
        val takeScreenshotCallback =
            @RequiresApi(Build.VERSION_CODES.R)
            object : TakeScreenshotCallback {
                override fun onSuccess(p0: ScreenshotResult) {
                    val bitmap = Bitmap.wrapHardwareBuffer(p0.hardwareBuffer, p0.colorSpace)
                    mBitmap = bitmap?.copy(Bitmap.Config.ARGB_8888, true)
                    if (mBitmap != null) {
                        val file =
                            File(filesDir.path + File.separator + "png" + File.separator + string)
                        if (!file.exists()) {
                            file.mkdirs()
                        }
                        var pngFile: File? = null
                        if (index == 1) {
                            pngFile =
                                File(file.path + File.separator + roomId + ".png")
                        }
                        if (index == 2) {
                            pngFile =
                                File(file.path + File.separator + roomId + "_" + roomId + ".png")
                        }
                        if (index == 3) {
                            pngFile =
                                File(file.path + File.separator + roomId + "_" + roomId + "_" + roomId + ".png")
                        }
                        if (pngFile != null) {
                            if (!pngFile.exists()) {
                                pngFile.createNewFile()
                            }
                        }
                        val fileOutputStream = FileOutputStream(pngFile)
                        mBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        tessBaseAPI.clear()
                    }
                }

                override fun onFailure(p0: Int) {
                }
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                takeScreenshot(
                    Display.DEFAULT_DISPLAY,
                    // applicationContext.mainExecutor
                    threadPoolExecutor,
                    takeScreenshotCallback
                )
            }
        }
    }

    /**
     * TessBaseAPI
     */
    private fun tessBaseAPI() {
        var mBitmap: Bitmap?
        val takeScreenshotCallback =
            @RequiresApi(Build.VERSION_CODES.R)
            object : TakeScreenshotCallback {
                override fun onSuccess(p0: ScreenshotResult) {
                    val bitmap = Bitmap.wrapHardwareBuffer(p0.hardwareBuffer, p0.colorSpace)
                    mBitmap = bitmap?.copy(Bitmap.Config.ARGB_8888, true)
                    if (mBitmap != null) {
                        tessBaseAPI.setImage(mBitmap)
                        val hOCRText = tessBaseAPI.getHOCRText(0)
                        println(hOCRText)
                        val doc = Jsoup.parse(hOCRText)
                        val a = doc.getElementsByClass("ocrx_word")
                        (0 until a.size).forEach { index ->
                            if (a[index].text() == "A.") {
                                println("=======================" + a[index].text())
                                val title = a[index].attributes().get("title")
                                println(title)
                                val xy = title.split(";")[0].replace("bbox ", "")
                                println(xy)
                                val x = xy.split(" ")[0].toFloat()
                                val y = xy.split(" ")[1].toFloat()
                                println("=======================$x")
                                println("=======================$y")
                                onDispatchGesture(x, y, 0F, 0F, 50, 50)
                                println("=======================")
                            }
                        }
                        tessBaseAPI.clear()
                    }
                }

                override fun onFailure(p0: Int) {
                }
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                takeScreenshot(
                    Display.DEFAULT_DISPLAY,
                    threadPoolExecutor,
                    takeScreenshotCallback
                )
            }
        }
    }
}