import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ghhccghk.hyperfocusnotifdemo.R

class NotificationHelper(private val context: Context) {
    private val CHANNEL_ID = "my_channel_id"

    init {
        createNotificationChannel()
    }

    // 创建通知渠道（Android 8.0+ 必须）
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "我的通知渠道"
            val descriptionText = "这个渠道用于示例通知"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // 注册渠道
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // 发送通知
    fun sendNotification(title: String, message: String): NotificationCompat.Builder {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setSubText(message)
            .setTicker(message)
            .setOngoing(true) // 设置为常驻通知
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            PendingIntent.getActivity(
                context, 0, launchIntent, PendingIntent.FLAG_MUTABLE
            )
        return builder
    }
}
