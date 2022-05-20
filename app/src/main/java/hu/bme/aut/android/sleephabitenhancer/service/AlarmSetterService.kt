package hu.bme.aut.android.sleephabitenhancer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import hu.bme.aut.android.sleephabitenhancer.callback.AlarmManagerStateChangeHandler
import hu.bme.aut.android.sleephabitenhancer.fragment.AlarmListViewFragment
import hu.bme.aut.android.sleephabitenhancer.viewmodel.SleepEnhancerViewModel
import kotlin.concurrent.thread

class AlarmSetterService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.turnOnActiveAlarms()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun turnOnActiveAlarms() {
        thread {
            val viewModel = SleepEnhancerViewModel()
            val alarms = viewModel.alarms.value
            val handler: AlarmManagerStateChangeHandler = AlarmListViewFragment()
            if (alarms != null) {
                for (alarm in alarms) {
                    if (alarm.active) {
                        handler.setNotification(applicationContext, alarm)
                        handler.setAlarm(applicationContext, alarm)
                    }
                }
            }
            stopSelf()
        }
    }
}