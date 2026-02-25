package com.ghhccghk.hyperfocusnotifdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.WindowCompat
import com.ghhccghk.hyperfocusnotifdemo.tools.FocusBgHelper.createPartBg
import com.hyperfocus.api.FocusApi
import com.hyperfocus.api.IslandApi
import org.json.JSONObject

class MainActivity : ComponentActivity() {

    //初始化Api
    private val focusApi = FocusApi
    private var time: Long = 1

    @androidx.annotation.RequiresPermission(android.Manifest.permission.POST_NOTIFICATIONS)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        time = System.currentTimeMillis()

        setContent {
            MainScreen(this, focusApi, time)
        }
    }
}
@SuppressLint("MissingPermission")
@Composable
private fun MainScreen(
    activity: MainActivity,
    focusApi: FocusApi,
    initialTime: Long
) {
    val context = LocalContext.current
    val notificationHelper = NotificationHelper(LocalContext.current)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                NotificationItem(
                    title = "带有baseInfo和hintInfo的通知，并设置背景和边上小图标",
                    onClick = {
                        sendBaseInfoNotification(
                            activity,
                            context,
                            notificationHelper,
                            focusApi
                        )
                    }
                )
            }

            item {
                NotificationItem(
                    title = "带有chatInfo和hintInfo的通知，并设置背景和边上小图标",
                    onClick = {
                        sendChatInfoNotification(
                            activity,
                            context,
                            notificationHelper,
                            focusApi
                        )
                    }
                )
            }

            item {
                NotificationItem(
                    title = "带有highlightInfo和hintInfo的通知，并设置背景和边上小图标",
                    onClick = {
                        sendHighlightInfoNotification(
                            activity,
                            context,
                            notificationHelper,
                            focusApi,
                            initialTime
                        )
                    }
                )
            }

            item {
                NotificationItem(
                    title = "带有ProgressInfo和baseInfo的通知，并设置背景和边上小图标",
                    onClick = {
                        sendProgressInfoBaseNotification(
                            activity,
                            context,
                            notificationHelper,
                            focusApi
                        )
                    }
                )
            }

            item {
                NotificationItem(
                    title = "带有ProgressInfo和chatinfo的通知，并设置背景和边上小图标",
                    onClick = {
                        sendProgressInfoChatNotification(
                            activity,
                            context,
                            notificationHelper,
                            focusApi
                        )
                    }
                )
            }

            item {
                NotificationItem(
                    title = "带有ProgressInfo和highlightInfo的通知，并设置背景和边上小图标",
                    onClick = {
                        sendProgressInfoHighlightNotification(
                            activity,
                            context,
                            notificationHelper,
                            focusApi,
                            initialTime
                        )
                    }
                )
            }

            item {
                NotificationItem(
                    title = "测试自定义焦点通知",
                    onClick = {
                        sendCustomFocusNotification(
                            activity,
                            context,
                            notificationHelper,
                            focusApi,
                            initialTime
                        )
                    }
                )
            }
        }
    }
}


@Composable
private fun NotificationItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// =============== 通知发送函数 ===============

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
private fun sendBaseInfoNotification(
    activity: MainActivity,
    context: Context,
    notificationHelper: NotificationHelper,
    focusApi: FocusApi
) {
    val sendNotification = notificationHelper.sendNotification("你好", "世界")
    val intent = Intent()
    intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
    intent.data = Uri.fromParts("package", context.packageName, null)

    val picProfiles = focusApi.addpics("pro", Icon.createWithResource(context, R.drawable.lycaon_icon))
    val pics = Bundle()
    pics.putAll(picProfiles)

    val actions = focusApi.actionInfo(actionIntent = intent, actionTitle = "test", actionTitleColor = "#FFFFFF")
    val baseInfo = focusApi.baseinfo(
        title = "title", colorTitle = "#FFFFFF",
        basetype = 1, content = "content", colorContent = "#FFFFFF",
        subContent = "subContent", colorSubContent = "#FFFFFF",
        extraTitle = "extraTitle", colorExtraTitle = "#FFFFFF",
        subTitle = "subTitle", colorsubTitle = "#FFFFFF",
        specialTitle = "special", colorSpecialTitle = "#FFFFFF",
        picFunction = "pro"
    )
    val hintInfo = focusApi.hintInfo(
        type = 1, titleLineCount = 6,
        title = "这是Hint里的title", colortitle = "#FFFFFF",
        content = "content", colorContent = "#FFFFFF",
        actionInfo = actions
    )
    val api = focusApi.sendFocus(
        title = "测试",
        cancel = false,
        baseInfo = baseInfo,
        hintInfo = hintInfo,
        addpics = pics,
        enableFloat = true,
        picbg = Icon.createWithResource(context, R.drawable.lycaon_bg_2),
        picInfo = Icon.createWithResource(context, R.drawable.wdlyjz),
        picbgtype = 2,
        picInfotype = 2,
        ticker = "ticker测试",
        picticker = Icon.createWithResource(context, R.drawable.ic_launcher_foreground)
    )
    val a = Bundle()
    a.putString("miui.effect.src", "true")
    a.putAll(api)
    sendNotification.addExtras(a)
    NotificationManagerCompat.from(context).notify(1, sendNotification.build())
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
private fun sendChatInfoNotification(
    activity: MainActivity,
    context: Context,
    notificationHelper: NotificationHelper,
    focusApi: FocusApi
) {
    val sendNotification = notificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
    val picProfiles = focusApi.addpics("pro", Icon.createWithResource(context, R.drawable.lycaon_icon))
    val pics = Bundle()
    pics.putAll(picProfiles)

    val chatinfo = focusApi.chatinfo(
        picProfile = "pro", title = "莱卡恩", content = "绳匠阁下，冒昧的打扰您了",
        colortitle = "#FFFFFF", colorcontent = "#FFFFFF"
    )
    val hintInfo = focusApi.hintInfo(
        type = 1, title = "发送时间",
        colortitle = "#FFFFFF", content = "7分钟前", colorContent = "#FFFFFF"
    )
    val api = focusApi.sendFocus(
        title = "莱卡恩",
        cancel = false,
        chatinfo = chatinfo,
        addpics = pics,
        enableFloat = true,
        hintInfo = hintInfo,
        ticker = "绳匠阁下，冒昧的打扰您了",
        picbg = Icon.createWithResource(context, R.drawable.lycaon_bg_2),
        picticker = Icon.createWithResource(context, R.drawable.lycaon_icon),
        picInfo = Icon.createWithResource(context, R.drawable.wdlyjz),
        picbgtype = 2,
        picInfotype = 2
    )
    sendNotification.addExtras(api)
    NotificationManagerCompat.from(context).notify(1, sendNotification.build())
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
private fun sendHighlightInfoNotification(
    activity: MainActivity,
    context: Context,
    notificationHelper: NotificationHelper,
    focusApi: FocusApi,
    initialTime: Long
) {
    val system = System.currentTimeMillis()
    val picProfiles = focusApi.addpics("pro", Icon.createWithResource(context, R.drawable.lycaon_icon))
    val pics = Bundle()
    pics.putAll(picProfiles)

    val sendNotification = notificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
    val timeout = focusApi.timerInfo(timerWhen = initialTime, timerSystemCurrent = system, timerType = 1)
    val highlightInfo = focusApi.highlightInfo(
        picFunction = "pro", colorTitle = "#FFFFFF",
        timerInfo = timeout, subContent = "前打开了应用", colorSubContent = "#FFFFFF"
    )
    val hintInfo = focusApi.hintInfo(
        type = 1, title = "title",
        colortitle = "#FFFFFF", content = "content", colorContent = "#FFFFFF"
    )
    val api = focusApi.sendFocus(
        isShowNotification = true,
        title = "莱卡恩",
        addpics = pics,
        enableFloat = true,
        cancel = false,
        coverInfo = focusApi.coverInfo(
            title = "绳匠阁下，冒昧的打扰您了",
            picCover = "miui.focus.pic_pro"
        ),
        ticker = "绳匠阁下，冒昧的打扰您了",
        picbg = Icon.createWithResource(context, R.drawable.lycaon_bg_2),
        picInfo = Icon.createWithResource(context, R.drawable.wdlyjz),
        picbgtype = 2,
        picInfotype = 2,
        picticker = Icon.createWithResource(context, R.drawable.ic_launcher_foreground)
    )
    sendNotification.addExtras(api)
    NotificationManagerCompat.from(context).notify(1, sendNotification.build())
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
private fun sendProgressInfoBaseNotification(
    activity: MainActivity,
    context: Context,
    notificationHelper: NotificationHelper,
    focusApi: FocusApi
) {
    val sendNotification = notificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
    val progressInfo = focusApi.progressInfo(progress = 20, colorProgress = "#FA5FFF")
    val baseInfo = focusApi.baseinfo(
        title = "title",
        colorTitle = "#FFFFFF", content = "content", colorContent = "#FFFFFF"
    )
    val api = focusApi.sendFocus(
        title = "莱卡恩",
        cancel = false,
        multiProgressInfo = JSONObject().put("progress", 88).put("points", 4).put("color", "#FA5FFF"),
        baseInfo = baseInfo,
        enableFloat = true,
        ticker = "绳匠阁下，冒昧的打扰您了",
        picbg = Icon.createWithResource(context, R.drawable.lycaon_bg_2),
        picInfo = Icon.createWithResource(context, R.drawable.wdlyjz),
        picbgtype = 2,
        picInfotype = 2,
        picticker = Icon.createWithResource(context, R.drawable.ic_launcher_foreground)
    )
    sendNotification.addExtras(api)
    NotificationManagerCompat.from(context).notify(1, sendNotification.build())
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
private fun sendProgressInfoChatNotification(
    activity: MainActivity,
    context: Context,
    notificationHelper: NotificationHelper,
    focusApi: FocusApi
) {
    val sendNotification = notificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
    val picProfiles = focusApi.addpics("pro", Icon.createWithResource(context, R.drawable.lycaon_icon))
    val pics = Bundle()
    pics.putAll(picProfiles)

    val progressInfo = focusApi.progressInfo(progress = 20, colorProgress = "#FA5FFF")
    val chatinfo = focusApi.chatinfo(
        picProfile = "pro", title = "莱卡恩", content = "绳匠阁下，冒昧的打扰您了",
        colortitle = "#FFFFFF", colorcontent = "#FFFFFF"
    )
    val api = focusApi.sendFocus(
        title = "莱卡恩",
        addpics = pics,
        enableFloat = true,
        cancel = false,
        progressInfo = progressInfo,
        chatinfo = chatinfo,
        ticker = "绳匠阁下，冒昧的打扰您了",
        picbg = Icon.createWithResource(context, R.drawable.lycaon_bg_2),
        picInfo = Icon.createWithResource(context, R.drawable.wdlyjz),
        picbgtype = 2,
        picInfotype = 2,
        picticker = Icon.createWithResource(context, R.drawable.ic_launcher_foreground)
    )
    sendNotification.addExtras(api)
    NotificationManagerCompat.from(context).notify(1, sendNotification.build())
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
private fun sendProgressInfoHighlightNotification(
    activity: MainActivity,
    context: Context,
    notificationHelper: NotificationHelper,
    focusApi: FocusApi,
    initialTime: Long
) {
    val system = System.currentTimeMillis()
    val picProfiles = focusApi.addpics("pro", Icon.createWithResource(context, R.drawable.lycaon_icon))
    val pics = Bundle()
    pics.putAll(picProfiles)

    val sendNotification = notificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
    val timeout = focusApi.timerInfo(timerWhen = initialTime, timerSystemCurrent = system, timerType = 1)
    val highlightInfo = focusApi.highlightInfo(
        picFunction = "pro", colorTitle = "#FFFFFF",
        timerInfo = timeout, subContent = "前打开了应用", colorSubContent = "#FFFFFF"
    )
    val progressInfo = focusApi.progressInfo(progress = 20, colorProgress = "#000000", colorProgressEnd = "#FA5FFF")
    val api = focusApi.sendFocus(
        title = "莱卡恩",
        addpics = pics,
        cancel = false,
        enableFloat = true,
        progressInfo = progressInfo,
        highlightInfo = highlightInfo,
        ticker = "绳匠阁下，冒昧的打扰您了",
        picbg = Icon.createWithResource(context, R.drawable.lycaon_bg_2),
        picInfo = Icon.createWithResource(context, R.drawable.wdlyjz),
        picbgtype = 2,
        picInfotype = 2,
        picticker = Icon.createWithResource(context, R.drawable.ic_launcher_foreground)
    )
    sendNotification.addExtras(api)
    NotificationManagerCompat.from(context).notify(1, sendNotification.build())
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
private fun sendCustomFocusNotification(
    activity: MainActivity,
    context: Context,
    notificationHelper: NotificationHelper,
    focusApi: FocusApi,
    initialTime: Long
) {
    val sendNotification = notificationHelper.sendNotification("莱卡恩", "绳匠阁下，冒昧的打扰您了")
    val remoteViews = RemoteViews(context.packageName, R.layout.focus_notification_module_background)
    remoteViews.setTextViewText(R.id.textView, "绳匠阁下，冒昧的打扰您了")
    remoteViews.setImageViewResource(R.id.imageView, R.drawable.lycaon_icon)
    createPartBg(context, Icon.createWithResource(context, R.drawable.lycaon_bg_2), remoteViews)

    val picProfiles = focusApi.addpics("pro", Icon.createWithResource(context, R.drawable.lycaon_icon))
    val pica = focusApi.addpics("abc", Icon.createWithResource(context, R.drawable.img))
    val pics = Bundle()
    pics.putAll(picProfiles)
    pics.putAll(pica)

    val system = System.currentTimeMillis()
    val timeout = IslandApi.TimerInfo(timerWhen = initialTime, timerSystemCurrent = system, timerType = 1)

    val pic = IslandApi.picInfo(
        autoplay = true,
        effectColor = "#33B5E5",
        effectSrc = "inEffectSrc",
        pic = "pro"
    )
    val pic1 = IslandApi.picInfo(
        autoplay = true,
        loop = true,
        type = 2,
        number = 99,
        effectColor = "#33B5E5",
        effectSrc = "inEffectSrc",
        pic = "musicWave"
    )
    val textInfo = IslandApi.TextInfo(
        turnAnim = false,
        showHighlightColor = false,
        title = "莱卡恩"
    )
    val textInfoa = IslandApi.TextInfo(
        isTitleDigit = false,
        turnAnim = false,
        showHighlightColor = true,
        title = "绳匠阁下"
    )
    val a = IslandApi.imageTextInfo(
        textInfo = textInfo,
        type = 1,
        picInfo = pic
    )
    val b = IslandApi.imageTextInfo(
        type = 2,
        textInfo = textInfoa,
        picInfo = pic1
    )

    val bigIslandArea = IslandApi.bigIslandArea(
        imageTextInfoLeft = a,
        sameWidthDigitInfo = IslandApi.sameWidthDigitInfo(
            timeInfo = timeout,
            content = "过去了",
            showHighlightColor = true
        )
    )
    val smallIslandArea = IslandApi.SmallIslandArea(
        combinePicInfo = IslandApi.combinePicInfo(
            picInfo = pic,
            smallPicInfo = JSONObject(),
            progressInfo = IslandApi.progressInfo(
                colorReach = "#FA5FFF",
                colorUnReach = "#FFFABC",
                progress = 33
            )
        )
    )

    val islandTemplate = IslandApi.IslandTemplate(
        highlightColor = "#FF47FF",
        bigIslandArea = bigIslandArea,
        smallIslandArea = smallIslandArea,
        expandedTime = 300
    )
    val focus = focusApi.sendDiyFocus(
        rv = remoteViews,
        rvNight = remoteViews,
        island = islandTemplate,
        ticker = "绳匠阁下，冒昧的打扰您了",
        outEffectSrc = "charger_light_wave",
        enableFloat = false,
        picticker = Icon.createWithResource(context, R.drawable.lycaon_icon),
        addpics = pics
    )

    sendNotification.addExtras(focus)
    NotificationManagerCompat.from(context).notify(1, sendNotification.build())
}