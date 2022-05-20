package hu.bme.aut.android.sleephabitenhancer

import android.media.AudioAttributes
import android.media.AudioAttributes.USAGE_ALARM
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.sleephabitenhancer.databinding.ActivityAlarmTriggeredBinding
import kotlin.concurrent.thread

class AlarmTriggeredActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmTriggeredBinding
    private lateinit var fullscreenContent: TextView
    private lateinit var fullscreenContentControls: LinearLayout
    private var isFullscreen: Boolean = false
    private var alarmOn = false
    private lateinit var alarmRingtone: Ringtone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlarmTriggeredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmOn = true
        isFullscreen = true
        fullscreenContent = binding.fullscreenContent
        fullscreenContentControls = binding.fullscreenContentControls

        soundAlarm()
        binding.btnAlarmOff.setOnClickListener {
            alarmRingtone.stop()
            alarmOn = false
            this.finish()
        }
        supportActionBar?.hide()
    }

    private fun soundAlarm() {
        thread {
            while (alarmOn) {
                val vibratorPattern = longArrayOf(500, 500)
                val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    (getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager).getVibrator(0)
                } else {
                    getSystemService(VIBRATOR_SERVICE) as Vibrator
                }
                vibrator.vibrate(
                    vibratorPattern,
                    0,
                    AudioAttributes.Builder().setUsage(USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
                )
            }
        }
        thread {
            val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            alarmRingtone = RingtoneManager.getRingtone(this, alarmUri)
            alarmRingtone.audioAttributes = AudioAttributes.Builder().setUsage(USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
            alarmRingtone.play()
        }
    }
}