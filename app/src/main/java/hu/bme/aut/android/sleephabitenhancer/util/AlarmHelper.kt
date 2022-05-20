package hu.bme.aut.android.sleephabitenhancer.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.util.Log

class AlarmHelper {
    companion object {
        const val DAY_IN_MILLISECONDS: Long = 24 * 60 * 60 * 1000

        fun scheduleNotification(ctx: Context, delayMinutes: Int, pendingIntent: PendingIntent) {
            val triggerDateTime = System.currentTimeMillis() + delayMinutes * 60 * 1000
            val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerDateTime,
                DAY_IN_MILLISECONDS,
                pendingIntent
            )
        }

        fun scheduleAlarm(ctx: Context, delaySeconds: Long, pendingIntent: PendingIntent) {
            val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            val triggerTime = System.currentTimeMillis() + delaySeconds * 1000
            Log.d("scheduleAlarm", "called; t: $triggerTime")
            val alarmInfo = AlarmManager.AlarmClockInfo(triggerTime, pendingIntent)
            alarmManager?.setAlarmClock(alarmInfo, pendingIntent)
        }

        fun cancelNotification(ctx: Context, pendingIntent: PendingIntent) {
            val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            alarmManager?.cancel(pendingIntent)
        }
    }
}