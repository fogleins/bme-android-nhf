package hu.bme.aut.android.sleephabitenhancer.model

import hu.bme.aut.android.sleephabitenhancer.util.Time

data class Alarm(
    var id: Long,
    var name: String,
    var reminderDue: Time,
    var alarmDue: Time,
    var active: Boolean
)
