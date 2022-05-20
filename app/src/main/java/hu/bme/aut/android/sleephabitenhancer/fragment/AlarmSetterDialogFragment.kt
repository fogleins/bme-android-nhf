package hu.bme.aut.android.sleephabitenhancer.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.sleephabitenhancer.R
import hu.bme.aut.android.sleephabitenhancer.databinding.FragmentAlarmSetterDialogBinding
import hu.bme.aut.android.sleephabitenhancer.model.Alarm
import hu.bme.aut.android.sleephabitenhancer.util.Time

class AlarmSetterDialogFragment(
    private val listener: TimeChooserDialogListener,
    private val item: Alarm? = null
) : DialogFragment() {

    interface TimeChooserDialogListener {
        fun onAlarmCreated(newItem: Alarm)
        fun onAlarmEdited(editedItem: Alarm)
        fun onInvalidValueOnSave()
    }

    private lateinit var binding: FragmentAlarmSetterDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentAlarmSetterDialogBinding.inflate(LayoutInflater.from(context))

        // Alarm picker
        val alarmTimePickerListener: TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                binding.etAlarmTime.setText(Time(hourOfDay, minute).toString())
            }
        val alarmTimePicker = TimePickerDialog(
            context,
            alarmTimePickerListener,
            item?.alarmDue?.hour ?: 8,
            item?.alarmDue?.minute ?: 0,
            true
        )
        binding.etAlarmTime.setOnClickListener {
            val etAlarmTimeText = binding.etAlarmTime.text
            if (etAlarmTimeText.isNotBlank()) {
                val time = Time.fromString(etAlarmTimeText.toString())
                alarmTimePicker.updateTime(time.hour, time.minute)
            }
            alarmTimePicker.show()
        }

        // Reminder picker
        val reminderTimePickerListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                binding.etNotificationTime.setText(Time(hourOfDay, minute).toString())
            }
        val reminderTimePicker = TimePickerDialog(
            context,
            reminderTimePickerListener,
            item?.reminderDue?.hour ?: 23,
            item?.alarmDue?.minute ?: 0,
            true
        )
        binding.etNotificationTime.setOnClickListener {
            val etNotificationTimeText = binding.etNotificationTime.text
            if (etNotificationTimeText.isNotBlank()) {
                val time = Time.fromString(etNotificationTimeText.toString())
                reminderTimePicker.updateTime(time.hour, time.minute)
            }
            reminderTimePicker.show()
        }


        // if editing an existing item
        if (item != null) {
            binding.etAlarmName.setText(item.name)
            binding.etAlarmTime.setText(item.alarmDue.toString())
            binding.etNotificationTime.setText(item.reminderDue.toString())
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(if (item == null) getString(R.string.create_alarm) else getString(R.string.edit_alarm) + " [" + (item.name) + "]")
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { _, _ ->
                if (item == null) {
                    if (inputIsValid()) {
                        listener.onAlarmCreated(getAlarmObject())
                    } else {
                        listener.onInvalidValueOnSave()
                    }
                } else {
                    if (inputIsValid()) {
                        item.name = binding.etAlarmName.text.toString()
                        item.alarmDue = Time.fromString(binding.etAlarmTime.text.toString())
                        item.reminderDue =
                            Time.fromString(binding.etNotificationTime.text.toString())
                        listener.onAlarmEdited(item)
                        item.active = true
                    }
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    companion object {
        const val TAG = "AlarmSetterDialogFragment"
    }

    private fun inputIsValid() =
        binding.etAlarmTime.text.isNotBlank() && binding.etNotificationTime.text.isNotBlank() && binding.etAlarmName.text.isNotBlank()

    private fun getAlarmObject() = Alarm(
        name = binding.etAlarmName.text.toString(),
        alarmDue = Time.fromString(binding.etAlarmTime.text.toString()),
        reminderDue = Time.fromString(binding.etNotificationTime.text.toString()),
        active = true,
        id = -1
    )
}