package hu.bme.aut.android.sleephabitenhancer.util

/**
 * Represents 24-hour format time.
 */
data class Time(var hour: Int, var minute: Int) {

    companion object {
        fun fromString(timeString: String): Time {
            val s = timeString.split(":")
            return Time(s[0].toInt(), s[1].toInt())
        }
    }

    init {
        if (hour < 0 || minute < 0) {
            throw IllegalArgumentException("Hour and minute of Time object must not be negative")
        } else if (hour > 24 || minute > 60) {
            throw IllegalArgumentException("Hour and minute of Time object must not be greater than 24 and 60, respectively")
        }
    }

    operator fun minus(t2: Time): Time {
        val minDelta = this.minute - t2.minute
        val min = if (minDelta >= 0) minDelta else minDelta + 60
        val hDelta = if (minDelta < 0) this.hour - t2.hour - 1 else this.hour - t2.hour
        val hour = if (hDelta >= 0) hDelta % 24 else hDelta % 24 + 24
        return Time(hour, min)
    }

    override fun toString(): String {
        return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
    }
}
