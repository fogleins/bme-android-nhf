package hu.bme.aut.android.sleephabitenhancer.model

data class Alarm(
    var id: Int,
    var name: String,
    var reminderDue: String,
    var alarmDue: String,
    var active: Boolean
)
