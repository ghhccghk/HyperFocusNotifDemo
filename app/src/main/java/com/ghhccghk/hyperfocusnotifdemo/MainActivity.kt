package com.ghhccghk.hyperfocusnotifdemo

import NotificationHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.RemoteViews
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.app.NotificationManagerCompat
import com.ghhccghk.hyperfocusnotifdemo.tools.FocusBgHelper.createPartBg
import com.hyperfocus.api.FocusApi
import com.hyperfocus.api.IslandApi

class MainActivity : ComponentActivity() {

    //初始化Api
    val focusApi = FocusApi
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
            val picProfiles = focusApi.addpics("pro",Icon.createWithResource(this,R.drawable.lycaon_icon))
            val pics = Bundle()
            pics.putAll(picProfiles)
            val actions = focusApi.actionInfo(actionIntent = intent, actionTitle = "test", actionTitleColor = "#FFFFFF")
            val baseInfo = focusApi.baseinfo(title = "title", colorTitle = "#FFFFFF",
                basetype = 1, content = "content", colorContent = "#FFFFFF", subContent = "subContent",
                colorSubContent = "#FFFFFF", extraTitle = "extraTitle", colorExtraTitle = "#FFFFFF",
                subTitle = "subTitle", colorsubTitle = "#FFFFFF",
                specialTitle = "special", colorSpecialTitle = "#FFFFFF",
                picFunction = "pro")
            val hintInfo = focusApi.hintInfo(type = 1 ,
                titleLineCount = 6,
                title = "这是Hint里的title", colortitle = "#FFFFFF" ,
                content = "content",  colorContent = "#FFFFFF",
                actionInfo = actions)
            val api = focusApi.sendFocus(
                title = "测试",
                baseInfo = baseInfo,
                hintInfo = hintInfo,
                addpics = pics,
                enableFloat = true,
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picInfo = Icon.createWithResource(this,R.drawable.wdlyjz),
                picbgtype = 2,
                picInfotype = 2,
                ticker = "ticker测试",
                picticker = Icon.createWithResource(this,R.drawable.ic_launcher_foreground)
            )
            val a = Bundle()
            a.putString("miui.effect.src","true")
            a.putAll(api)
            sendNotification.addExtras(a)
            NotificationManagerCompat.from(this).notify(1, sendNotification.build())
        }

        // 带有chatInfo和hintInfo的通知
        val chatInfoandhintInfo = Preferences(this)
        chatInfoandhintInfo.setTitle("带有chatInfo和hintInfo的通知，并设置背景和边上小图标")
        chatInfoandhintInfo.setSummary("")
        container.addView(chatInfoandhintInfo.getView())
        chatInfoandhintInfo.getView().setOnClickListener {
            val sendNotification = NotificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
            val picProfiles = focusApi.addpics("pro",Icon.createWithResource(this,R.drawable.lycaon_icon))
            val pics = Bundle()
            pics.putAll(picProfiles)
            val chatinfo = focusApi.chatinfo(
                picProfile = "pro",title = "莱卡恩", content = "绳匠阁下，冒昧的打扰您了",
                colortitle = "#FFFFFF", colorcontent = "#FFFFFF")
            val hintInfo = focusApi.hintInfo(type = 1 ,title = "发送时间",
                colortitle = "#FFFFFF" , content = "7分钟前",colorContent = "#FFFFFF" )
            val api = focusApi.sendFocus(
                title = "莱卡恩",
                chatinfo = chatinfo,
                addpics = pics,
                enableFloat = true,
                hintInfo = hintInfo,
                ticker = "绳匠阁下，冒昧的打扰您了",
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picticker = Icon.createWithResource(this,R.drawable.lycaon_icon),
                picInfo = Icon.createWithResource(this,R.drawable.wdlyjz),
                picbgtype = 2,
                picInfotype = 2,
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
            val picProfiles = focusApi.addpics("pro",Icon.createWithResource(this,R.drawable.lycaon_icon))
            val pics = Bundle()
            pics.putAll(picProfiles)
            val sendNotification = NotificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
            val timeout = focusApi.timerInfo(timerWhen = time, timerSystemCurrent = system, timerType = 1 )
            val highlightInfo = focusApi.highlightInfo(picFunction = "pro", colorTitle = "#FFFFFF",
                timerInfo = timeout, subContent = "前打开了应用", colorSubContent = "#FFFFFF")
            val hintInfo = focusApi.hintInfo(type = 1 ,title = "title",
                colortitle = "#FFFFFF" , content = "content",colorContent = "#FFFFFF" )
            val api = focusApi.sendFocus(
                title = "莱卡恩",
                addpics = pics,
                enableFloat = true,
                highlightInfo = highlightInfo,
                hintInfo = hintInfo,
                ticker = "绳匠阁下，冒昧的打扰您了",
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picInfo = Icon.createWithResource(this,R.drawable.wdlyjz),
                picbgtype = 2,
                picInfotype = 2,
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
            val progressInfo =  focusApi.progressInfo(progress = 20, colorProgress ="#FA5FFF")
            val baseInfo = focusApi.baseinfo(title = "title",
                colorTitle = "#FFFFFF" , content = "content",colorContent = "#FFFFFF" )
            val api = focusApi.sendFocus(
                title = "莱卡恩",
                progressInfo = progressInfo,
                baseInfo = baseInfo,
                enableFloat = true,
                ticker = "绳匠阁下，冒昧的打扰您了",
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picInfo = Icon.createWithResource(this,R.drawable.wdlyjz),
                picbgtype = 2,
                picInfotype = 2,
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
            val picProfiles = focusApi.addpics("pro",Icon.createWithResource(this,R.drawable.lycaon_icon))
            val pics = Bundle()
            pics.putAll(picProfiles)
            val ProgressInfo =  focusApi.progressInfo(progress = 20, colorProgress = "#FA5FFF")
            val chatinfo = focusApi.chatinfo(picProfile = "pro", title = "莱卡恩", content = "绳匠阁下，冒昧的打扰您了", colortitle = "#FFFFFF", colorcontent = "#FFFFFF")
            val api = focusApi.sendFocus(
                title = "莱卡恩",
                addpics = pics,
                enableFloat = true,
                progressInfo = ProgressInfo,
                chatinfo = chatinfo,
                ticker = "绳匠阁下，冒昧的打扰您了",
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picInfo = Icon.createWithResource(this,R.drawable.wdlyjz),
                picbgtype = 2,
                picInfotype = 2,
                picticker = Icon.createWithResource(this,R.drawable.ic_launcher_foreground)
            )
            sendNotification.addExtras(api)
            NotificationManagerCompat.from(this).notify(1, sendNotification.build())
        }

        // 带有ProgressInfo和highlightInfo的通知
        val ProgressInfoandhighlightInfo = Preferences(this)
        ProgressInfoandhighlightInfo.setTitle("带有ProgressInfo和highlightInfo的通知，并设置背景和边上小图标")
        ProgressInfoandhighlightInfo.setSummary("")
        container.addView(ProgressInfoandhighlightInfo.getView())
        ProgressInfoandhighlightInfo.getView().setOnClickListener {
            val system = System.currentTimeMillis()
            val picProfiles = focusApi.addpics("pro",Icon.createWithResource(this,R.drawable.lycaon_icon))
            val pics = Bundle()
            pics.putAll(picProfiles)
            val sendNotification = NotificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
            val timeout = focusApi.timerInfo(timerWhen = time, timerSystemCurrent = system, timerType = 1 )
            val highlightInfo = focusApi.highlightInfo(picFunction = "pro", colorTitle = "#FFFFFF", timerInfo = timeout, subContent = "前打开了应用", colorSubContent = "#FFFFFF")
            val ProgressInfo =  focusApi.progressInfo(progress = 20, colorProgress ="#000000", colorProgressEnd = "#FA5FFF")
            val api = focusApi.sendFocus(
                title = "莱卡恩",
                addpics = pics,
                enableFloat = true,
                progressInfo = ProgressInfo,
                highlightInfo = highlightInfo,
                ticker = "绳匠阁下，冒昧的打扰您了",
                picbg = Icon.createWithResource(this,R.drawable.lycaon_bg_2),
                picInfo = Icon.createWithResource(this,R.drawable.wdlyjz),
                picbgtype = 2,
                picInfotype = 2,
                picticker = Icon.createWithResource(this,R.drawable.ic_launcher_foreground)
            )
            sendNotification.addExtras(api)
            NotificationManagerCompat.from(this).notify(1, sendNotification.build())
        }

        val test = Preferences(this)
        test.setTitle("测试自定义焦点通知")
        test.setSummary("")
        container.addView(test.getView())
        test.getView().setOnClickListener {
            val sendNotification = NotificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
            val remoteViews = RemoteViews(this.packageName, R.layout.focus_notification_module_background)
            remoteViews.setTextViewText(R.id.textView, "绳匠阁下，冒昧的打扰您了")
            remoteViews.setImageViewResource(R.id.imageView, R.drawable.lycaon_icon)
            createPartBg(this,Icon.createWithResource(this,R.drawable.lycaon_bg_2),remoteViews)
            val picProfiles = focusApi.addpics("pro",Icon.createWithResource(this,R.drawable.lycaon_icon))
            val pics = Bundle()
            pics.putAll(picProfiles)

            val pic = IslandApi.PicInfo(
                autoplay = true,
                pic = "miui.focus.pic_pro"
            )
            val textInfo = IslandApi.TextInfo(
                turnAnim = true,
                showHighlightColor = false,
                content = "绳匠阁下，冒昧的打扰您了",
                title = "莱卡恩"
            )
            val textInfoa = IslandApi.TextInfo(
                isTitleDigit = true,
                turnAnim = true,
                showHighlightColor = true,
                title = "绳匠阁下，冒昧的打扰您了"
            )
            val a = IslandApi.ImageTextInfo(
                textInfo = textInfo,
                type = 1,
                picInfo = pic
            )
            val b = IslandApi.ImageTextInfo(
                textInfo = textInfoa,
                type = 2,
                picInfo = pic
            )

            val bigIslandArea = IslandApi.BigIslandArea(
                imageTextInfoLeft = a,
                imageTextInfoRight = b,
                textInfo = textInfo
            )
            val smallIslandArea = IslandApi.SmallIslandArea(
                picInfo = pic
            )

            val islandTemplate = IslandApi.IslandTemplate(
                highlightColor = "#FF47FF",
                bigIslandArea = bigIslandArea,
                smallIslandArea = smallIslandArea,
                expandedTime = 300
            )
            val focus = focusApi.senddiyFocus(
                rv = remoteViews,
                rvNight = remoteViews,
                island = islandTemplate,
                ticker = "绳匠阁下，冒昧的打扰您了",
                enableFloat = false,
                picticker = Icon.createWithResource(this,R.drawable.lycaon_icon),
                addpics = pics)

            sendNotification.addExtras(focus)
            NotificationManagerCompat.from(this).notify(1, sendNotification.build())
        }
    }

}