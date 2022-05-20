package hu.bme.aut.android.sleephabitenhancer.util.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import hu.bme.aut.android.sleephabitenhancer.MainActivity
import hu.bme.aut.android.sleephabitenhancer.R
import hu.bme.aut.android.sleephabitenhancer.broadcastreceiver.BedtimeBroadcastReceiver
import kotlin.random.Random

class NotificationHelper {
    companion object {
        fun createNotificationChannels(ctx: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                SleepEnhancerNotificationChannel.values().forEach {
                    val name = it.channelName
                    val descriptionText = it.channelDescription
                    val importance =
                        if (it.id == "hu.bme.aut.android.sleephabitenhancer.bedtimereminder")
                            NotificationManager.IMPORTANCE_HIGH
                        else NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel(it.id, name, importance).apply {
                        description = descriptionText
                    }
                    with(NotificationManagerCompat.from(ctx)) {
                        createNotificationChannel(channel)
                    }
                }

            }
        }

        fun createPendingIntentForBedtimeNotification(ctx: Context, alarmId: Int): PendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.getBroadcast(
                    ctx, alarmId, Intent(ctx, BedtimeBroadcastReceiver::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            } else {
                PendingIntent.getBroadcast(
                    ctx,
                    alarmId,
                    Intent(ctx, BedtimeBroadcastReceiver::class.java),
                    0
                )
            }

        fun createBedtimeNotification(ctx: Context) {
            val intent = Intent(ctx, MainActivity::class.java)
            val pendingIntent: PendingIntent =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                } else {
                    PendingIntent.getActivity(ctx, 0, intent, 0)
                }

            val builder =
                NotificationCompat.Builder(
                    ctx,
                    SleepEnhancerNotificationChannel.BEDTIME_REMINDER.id
                )
                    .setSmallIcon(R.drawable.ic_baseline_king_bed_24)
                    .setContentTitle(ctx.getString(R.string.notification_title_time_to_sleep))
                    .setContentText(ctx.getString(R.string.notification_content_good_night))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            with(NotificationManagerCompat.from(ctx)) {
                notify(Random.Default.nextInt(10000, 100000), builder.build())
            }
        }
    }
}