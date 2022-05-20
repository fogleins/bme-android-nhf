package hu.bme.aut.android.sleephabitenhancer.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import hu.bme.aut.android.sleephabitenhancer.service.AlarmSetterService

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != "android.intent.action.BOOT_COMPLETED") {
            return
        }
        val serviceStarterIntent = Intent(context, AlarmSetterService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(serviceStarterIntent)
        } else {
            context?.startService(serviceStarterIntent)
        }
    }
}