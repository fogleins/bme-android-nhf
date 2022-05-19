package hu.bme.aut.android.sleephabitenhancer.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hu.bme.aut.android.sleephabitenhancer.AlarmTriggeredActivity

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmTriggeredIntent = Intent(context, AlarmTriggeredActivity::class.java)
        alarmTriggeredIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(alarmTriggeredIntent)
    }
}