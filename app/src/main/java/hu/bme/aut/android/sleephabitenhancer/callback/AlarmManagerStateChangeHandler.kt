package hu.bme.aut.android.sleephabitenhancer.callback

import android.content.Context
import hu.bme.aut.android.sleephabitenhancer.model.Alarm

interface AlarmManagerStateChangeHandler {
    fun setNotification(ctx: Context, alarm: Alarm)
    fun setAlarm(ctx: Context, alarm: Alarm)
    fun cancelNotification(ctx: Context, alarm: Alarm)
    fun cancelAlarm(ctx: Context, alarm: Alarm)
}