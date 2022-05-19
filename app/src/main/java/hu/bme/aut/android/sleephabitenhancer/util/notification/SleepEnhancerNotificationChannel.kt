package hu.bme.aut.android.sleephabitenhancer.util.notification

enum class SleepEnhancerNotificationChannel(
    val id: String,
    val channelName: String,
    val channelDescription: String
) {
    BEDTIME_REMINDER(
        "hu.bme.aut.android.sleephabitenhancer.bedtimereminder",
        "Bedtime reminders",
        "Reminders sent when you should go to bed"
    ),
    ALARM("hu.bme.aut.android.sleephabitenhancer.alarm", "Alarms", "Wakeup alarms"),
    BOOT_COMPLETED(
        "hu.bme.aut.android.sleephabitenhancer.bootcompleted",
        "Active alarms after restart",
        "Info about the alarms set after the device restarts"
    )
}