package hu.bme.aut.android.sleephabitenhancer

import hu.bme.aut.android.sleephabitenhancer.util.Time
import org.junit.Test

import org.junit.Assert.*

class TimeTest {
    private val t1 = Time(10, 32)

    @Test
    fun noUnderflow() {
        assertEquals(t1 - Time(5, 30), Time(5, 2))
    }

    @Test
    fun minuteUnderflow() {
        val t3 = Time(5, 35)
        assertEquals(t1 - t3, Time(4, 57))
    }

    @Test
    fun hourUnderflow() {
        val t4 = Time(11, 20)
        assertEquals(t1 - t4, Time(23, 12))
    }

    // 4.
    @Test
    fun hourAndMinuteUnderflow() {
        val t5 = Time(11, 33)
        assertEquals(t1 - t5, Time(22, 59))
    }

    // 5.
    @Test
    fun shouldEqualZero() {
        val t6 = Time(10, 32)
        assertEquals(t1 - t6, Time(0, 0))
        assertEquals(t1 - t1, Time(0, 0))
    }

    // 6.
    @Test
    fun minuteMidnightUnderflow() {
        val t7 = Time(10, 33)
        assertEquals(t1 - t7, Time(23, 59))
    }

    @Test(expected = IllegalArgumentException::class)
    fun exceptionTest1() {
        Time(-10, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun exceptionTest2() {
        Time(10, 120)
    }
}