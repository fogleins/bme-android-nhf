package hu.bme.aut.android.sleephabitenhancer.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class RoomAlarm(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String,
    var reminderDue: String,
    var alarmDue: String,
    var active: Boolean
)
