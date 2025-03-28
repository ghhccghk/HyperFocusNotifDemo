package com.ghhccghk.hyperfocusnotifdemo

import NotificationHelper
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.app.NotificationManagerCompat
import com.hyperfocus.api.FocusApi

class MainActivity : ComponentActivity() {

    //初始化Api
    val FocusApi = FocusApi()
    var time : Long = 1

    @androidx.annotation.RequiresPermission(android.Manifest.permission.POST_NOTIFICATIONS)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val NotificationHelper = NotificationHelper(this)
        enableEdgeToEdge()
        // 设置 Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setActionBar(toolbar)
        setContentView(R.layout.activity_main)
        val container = findViewById<LinearLayout>(R.id.root_container)
        // 带有baseInfo和hintInfo的通知
        val newView = Preferences(this)
        newView.setTitle("带有baseInfo和hintInfo的通知，并设置背景和边上小图标")
        newView.setSummary("")
        container.addView(newView.getView())
        time = System.currentTimeMillis()

        newView.getView().setOnClickListener  {
            val sendNotification = NotificationHelper.sendNotification("你好", "世界")
            val intent = Intent()
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"  // 设置 Action，跳转到应用详情页
            intent.data = Uri.fromParts("package", this.packageName, null)  // 指定要打开的应用包名
            val actions = FocusApi.ActionInfo(actionsIntent = intent, actionsTitle = "test")
            val baseInfo = FocusApi.baseinfo(title = "title", colorTitle = "#FFFFFF",
                basetype = 1, content = "content", colorContent = "#FFFFFF", subContent = "subContent",
                colorSubContent = "#FFFFFF", extraTitle = "extraTitle", colorExtraTitle = "#FFFFFF",
                subTitle = "subTitle", colorsubTitle = "#FFFFFF",
                specialTitle = "special", colorSpecialTitle = "#FFFFFF",)
            val hintInfo = FocusApi.HintInfo(type = 1 ,titleLineCount = 6,title = "这是Hint里的title", colortitle = "#FFFFFF" , content = "content",  colorContent = "#FFFFFF", actionInfo = actions)
            val api = FocusApi.sendFocus(
                title = "测试",
                baseInfo = baseInfo,
                hintInfo = hintInfo,
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picmarkv2 = Icon.createWithResource(this,R.drawable.wdlyjz),
                picbgtype = 2,
                picmarkv2type = 2,
                builder = sendNotification,
                ticker = "ticker测试",
                picticker = Icon.createWithResource(this,R.drawable.ic_launcher_foreground)
            )
            sendNotification.addExtras(api)
            NotificationManagerCompat.from(this).notify(1, sendNotification.build())
        }

        // 带有chatInfo和hintInfo的通知
        val chatInfoandhintInfo = Preferences(this)
        chatInfoandhintInfo.setTitle("带有chatInfo和hintInfo的通知，并设置背景和边上小图标")
        chatInfoandhintInfo.setSummary("")
        container.addView(chatInfoandhintInfo.getView())
        chatInfoandhintInfo.getView().setOnClickListener {
            val sendNotification = NotificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
            val chatinfo = FocusApi.chatinfo(title = "莱卡恩", content = "绳匠阁下，冒昧的打扰您了", colortitle = "#FFFFFF", colorcontent = "#FFFFFF")
            val hintInfo = FocusApi.HintInfo(type = 1 ,title = "发送时间",
                colortitle = "#FFFFFF" , content = "7分钟前",colorContent = "#FFFFFF" )
            val api = FocusApi.sendFocus(
                title = "莱卡恩",
                chatinfo = chatinfo,
                hintInfo = hintInfo,
                builder = sendNotification,
                ticker = "绳匠阁下，冒昧的打扰您了",
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picticker = Icon.createWithResource(this,R.drawable.lycaon_icon),
                picmarkv2 = Icon.createWithResource(this,R.drawable.wdlyjz),
                picbgtype = 2,
                picmarkv2type = 2,
                picProfile = Icon.createWithResource(this,R.drawable.lycaon_icon)
            )
            sendNotification.addExtras(api)
            NotificationManagerCompat.from(this).notify(1, sendNotification.build())
        }

        // 带有highlightInfo和hintInfo的通知
        val highlightInfoandhintInfo = Preferences(this)
        highlightInfoandhintInfo.setTitle("带有highlightInfo和hintInfo的通知，并设置背景和边上小图标")
        highlightInfoandhintInfo.setSummary("")
        container.addView(highlightInfoandhintInfo.getView())
        highlightInfoandhintInfo.getView().setOnClickListener {
            val system = System.currentTimeMillis()
            val sendNotification = NotificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
            val timeout = FocusApi.timerInfo(timerWhen = time, timerSystemCurrent = system, timerType = 1 )
            val highlightInfo = FocusApi.highlightInfo(colorTitle = "#FFFFFF", timerInfo = timeout, subContent = "前打开了应用", colorSubContent = "#FFFFFF")
            val hintInfo = FocusApi.HintInfo(type = 1 ,title = "title",
                colortitle = "#FFFFFF" , content = "content",colorContent = "#FFFFFF" )
            val api = FocusApi.sendFocus(
                title = "莱卡恩",
                highlightInfo = highlightInfo,
                hintInfo = hintInfo,
                builder = sendNotification,
                ticker = "绳匠阁下，冒昧的打扰您了",
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picmarkv2 = Icon.createWithResource(this,R.drawable.wdlyjz),
                picFunction = Icon.createWithResource(this,R.drawable.lycaon_icon),
                picbgtype = 2,
                picmarkv2type = 2,
                picticker = Icon.createWithResource(this,R.drawable.ic_launcher_foreground)
            )
            sendNotification.addExtras(api)
            NotificationManagerCompat.from(this).notify(1, sendNotification.build())
        }

        // 带有ProgressInfo和baseInfo的通知
        val ProgressInfoandhintInfo = Preferences(this)
        ProgressInfoandhintInfo.setTitle("带有ProgressInfo和baseInfo的通知，并设置背景和边上小图标")
        ProgressInfoandhintInfo.setSummary("")
        container.addView(ProgressInfoandhintInfo.getView())
        ProgressInfoandhintInfo.getView().setOnClickListener {
            val sendNotification = NotificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
            val ProgressInfo =  FocusApi.ProgressInfo(progress = 20, colorProgress ="#000000", colorProgressEnd = "#FA5FFF")
            val baseInfo = FocusApi.baseinfo(title = "title",
                colorTitle = "#FFFFFF" , content = "content",colorContent = "#FFFFFF" )
            val api = FocusApi.sendFocus(
                title = "莱卡恩",
                ProgressInfo = ProgressInfo,
                baseInfo = baseInfo,
                builder = sendNotification,
                ticker = "绳匠阁下，冒昧的打扰您了",
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picmarkv2 = Icon.createWithResource(this,R.drawable.wdlyjz),
                picFunction = Icon.createWithResource(this,R.drawable.lycaon_icon),
                picbgtype = 2,
                picmarkv2type = 2,
                picticker = Icon.createWithResource(this,R.drawable.ic_launcher_foreground)
            )
            sendNotification.addExtras(api)
            NotificationManagerCompat.from(this).notify(1, sendNotification.build())
        }

        // 带有ProgressInfo和chatInfo的通知
        val ProgressInfoandchatInfo = Preferences(this)
        ProgressInfoandchatInfo.setTitle("带有ProgressInfo和chatinfo的通知，并设置背景和边上小图标")
        ProgressInfoandchatInfo.setSummary("")
        container.addView(ProgressInfoandchatInfo.getView())
        ProgressInfoandchatInfo.getView().setOnClickListener {
            val sendNotification = NotificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
            val ProgressInfo =  FocusApi.ProgressInfo(progress = 20, colorProgress ="#000000", colorProgressEnd = "#FA5FFF")
            val chatinfo = FocusApi.chatinfo(title = "莱卡恩", content = "绳匠阁下，冒昧的打扰您了", colortitle = "#FFFFFF", colorcontent = "#FFFFFF")
            val api = FocusApi.sendFocus(
                title = "莱卡恩",
                ProgressInfo = ProgressInfo,
                chatinfo = chatinfo,
                builder = sendNotification,
                ticker = "绳匠阁下，冒昧的打扰您了",
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picmarkv2 = Icon.createWithResource(this,R.drawable.wdlyjz),
                picProfile = Icon.createWithResource(this,R.drawable.lycaon_icon),
                picbgtype = 2,
                picmarkv2type = 2,
                picticker = Icon.createWithResource(this,R.drawable.ic_launcher_foreground)
            )
            sendNotification.addExtras(api)
            NotificationManagerCompat.from(this).notify(1, sendNotification.build())
        }

        // 带有ProgressInfo和highlightInfo的通知
        val ProgressInfoandhighlightInfo = Preferences(this)
        ProgressInfoandhighlightInfo.setTitle("带有ProgressInfo和highlightInfo的通知，并设置背景和边上小图标,无subContent前的小图标")
        ProgressInfoandhighlightInfo.setSummary("")
        container.addView(ProgressInfoandhighlightInfo.getView())
        ProgressInfoandhighlightInfo.getView().setOnClickListener {
            val system = System.currentTimeMillis()
            val sendNotification = NotificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
            val timeout = FocusApi.timerInfo(timerWhen = time, timerSystemCurrent = system, timerType = 1 )
            val highlightInfo = FocusApi.highlightInfo(colorTitle = "#FFFFFF", timerInfo = timeout, subContent = "前打开了应用", colorSubContent = "#FFFFFF")
            val ProgressInfo =  FocusApi.ProgressInfo(progress = 20, colorProgress ="#000000", colorProgressEnd = "#FA5FFF")
            val api = FocusApi.sendFocus(
                title = "莱卡恩",
                ProgressInfo = ProgressInfo,
                highlightInfo = highlightInfo,
                builder = sendNotification,
                ticker = "绳匠阁下，冒昧的打扰您了",
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picmarkv2 = Icon.createWithResource(this,R.drawable.wdlyjz),
                picProfile = Icon.createWithResource(this,R.drawable.lycaon_icon),
                picbgtype = 2,
                picmarkv2type = 2,
                picticker = Icon.createWithResource(this,R.drawable.ic_launcher_foreground)
            )
            sendNotification.addExtras(api)
            NotificationManagerCompat.from(this).notify(1, sendNotification.build())
        }
    }


}