package hu.bme.aut.android.sleephabitenhancer.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmDao {
    @Insert
    fun insertAlarm(alarm: RoomAlarm): Long

    @Query("SELECT * FROM alarm")
    fun getAlarms(): LiveData<List<RoomAlarm>>

    @Query("SELECT * FROM alarm WHERE id = :id")
    fun getAlarmById(id: Long): RoomAlarm?

    @Update
    fun updateAlarm(alarm: RoomAlarm): Int

    @Delete
    fun deleteAlarm(alarm: RoomAlarm)
}