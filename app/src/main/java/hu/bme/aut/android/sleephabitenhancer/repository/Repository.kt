package hu.bme.aut.android.sleephabitenhancer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.sleephabitenhancer.database.RoomAlarm
import hu.bme.aut.android.sleephabitenhancer.database.AlarmDao
import hu.bme.aut.android.sleephabitenhancer.model.Alarm
import hu.bme.aut.android.sleephabitenhancer.util.Time
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

    suspend fun delete(alarm: Alarm) = withContext(Dispatchers.IO) {
        alarmDao.deleteAlarm(alarm.toRoomModel())
    }

    suspend fun update(alarm: Alarm) = withContext(Dispatchers.IO) {
        val alarmToUpdate = alarmDao.getAlarmById(alarm.id) ?: return@withContext
        alarmToUpdate.name = alarm.name
        alarmToUpdate.active = alarm.active
        alarmToUpdate.alarmDue = alarm.alarmDue.toString()
        alarmToUpdate.reminderDue = alarm.reminderDue.toString()
        alarmDao.updateAlarm(alarmToUpdate)
    }

    private fun RoomAlarm.toDomainModel(): Alarm {
        return Alarm(
            id = id!!,
            name = name,
            reminderDue = Time.fromString(reminderDue),
            alarmDue = Time.fromString(alarmDue),
            active = active
        )
    }

    private fun Alarm.toRoomModel(): RoomAlarm {
        return RoomAlarm(
            name = name,
            reminderDue = reminderDue.toString(),
            alarmDue = alarmDue.toString(),
            active = active
        )
    }
}