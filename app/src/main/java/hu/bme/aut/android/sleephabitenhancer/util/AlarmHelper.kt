package hu.bme.aut.android.sleephabitenhancer.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.util.Log

class AlarmHelper {
    companion object {
        fun scheduleNotification(ctx: Context, delayMinutes: Int, pendingIntent: PendingIntent) {
            val triggerDateTime = System.currentTimeMillis() + delayMinutes * 60 * 1000
            Log.d("scheduleNotification", "called; t: $triggerDateTime")
            val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            alarmManager?.set(AlarmManager.RTC_WAKEUP, triggerDateTime, pendingIntent)
        }

        // TODO: enable all active alarms
        // TODO: testing
        fun scheduleAlarm(ctx: Context, delaySeconds: Long, pendingIntent: PendingIntent) {
            val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            val triggerTime = System.currentTimeMillis() + delaySeconds * 60 * 1000
            val alarmInfo = AlarmManager.AlarmClockInfo(triggerTime, pendingIntent)
            alarmManager?.setAlarmClock(alarmInfo, pendingIntent)
        }
    }
}