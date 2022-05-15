package hu.bme.aut.android.sleephabitenhancer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.sleephabitenhancer.database.RoomAlarm
import hu.bme.aut.android.sleephabitenhancer.database.AlarmDao
import hu.bme.aut.android.sleephabitenhancer.model.Alarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val alarmDao: AlarmDao) {
    fun getAlarms(): LiveData<List<Alarm>> {
        return alarmDao.getAlarms().map { roomAlarms ->
            roomAlarms.map { roomAlarm ->
                roomAlarm.toDomainModel()
            }
        }
    }

    suspend fun insert(alarm: Alarm) = withContext(Dispatchers.IO) {
        alarmDao.insertAlarm(alarm.toRoomModel())
    }

    private fun RoomAlarm.toDomainModel(): Alarm {
        return Alarm(
            id = id!!,
            name = name,
            reminderDue = reminderDue,
            alarmDue = alarmDue,
            active = active
        )
    }

    private fun Alarm.toRoomModel(): RoomAlarm {
        return RoomAlarm(
            name = name,
            reminderDue = reminderDue,
            alarmDue = alarmDue,
            active = active
        )
    }
}