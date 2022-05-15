package hu.bme.aut.android.sleephabitenhancer

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.sleephabitenhancer.database.SleepEnhancerDatabase

class SleepEnhancerApplication : Application() {
    companion object {
        lateinit var sleepEnhancerDatabase: SleepEnhancerDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        sleepEnhancerDatabase = Room.databaseBuilder(
            applicationContext,
            SleepEnhancerDatabase::class.java,
            "sleep_enhancer_database"
        ).fallbackToDestructiveMigration().build()
    }
}