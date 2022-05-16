package hu.bme.aut.android.sleephabitenhancer.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RoomAlarm::class],
    version = 1
)
abstract class SleepEnhancerDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}
