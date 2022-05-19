package hu.bme.aut.android.sleephabitenhancer.util

import java.util.*

fun Time.timeDeltaFromNow(): Time {
    val now = Calendar.getInstance()
    val h = now.get(Calendar.HOUR_OF_DAY)
    val m = now.get(Calendar.MINUTE)
    return this - Time(h, m)
}

fun Time.timeDeltaMinutesFromNow(): Int {
    val time = timeDeltaFromNow()
    return time.hour * 60 + time.minute
}