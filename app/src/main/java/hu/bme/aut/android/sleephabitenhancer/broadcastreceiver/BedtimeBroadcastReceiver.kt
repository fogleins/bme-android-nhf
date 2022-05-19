package hu.bme.aut.android.sleephabitenhancer.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hu.bme.aut.android.sleephabitenhancer.util.notification.NotificationHelper

class BedtimeBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationHelper.createBedtimeNotification(context.applicationContext)
    }
}