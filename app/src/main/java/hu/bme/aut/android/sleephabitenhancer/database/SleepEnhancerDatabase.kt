package hu.bme.aut.android.sleephabitenhancer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RoomAlarm::class],
    version = 1
)
abstract class SleepEnhancerDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        fun getDatabase(applicationContext: Context): SleepEnhancerDatabase {
            return Room.databaseBuilder(
                applicationContext,
                SleepEnhancerDatabase::class.java,
                "sleepenhancer"
            ).build()
        }
    }
}
