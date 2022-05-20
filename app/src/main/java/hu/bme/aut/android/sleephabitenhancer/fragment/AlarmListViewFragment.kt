package hu.bme.aut.android.sleephabitenhancer.fragment

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.sleephabitenhancer.R
import hu.bme.aut.android.sleephabitenhancer.adapter.AlarmRecyclerViewAdapter
import hu.bme.aut.android.sleephabitenhancer.broadcastreceiver.AlarmBroadcastReceiver
import hu.bme.aut.android.sleephabitenhancer.callback.AlarmManagerStateChangeHandler
import hu.bme.aut.android.sleephabitenhancer.databinding.FragmentAlarmListViewBinding
import hu.bme.aut.android.sleephabitenhancer.model.Alarm
import hu.bme.aut.android.sleephabitenhancer.util.AlarmHelper
import hu.bme.aut.android.sleephabitenhancer.util.notification.NotificationHelper
import hu.bme.aut.android.sleephabitenhancer.util.timeDeltaFromNow
import hu.bme.aut.android.sleephabitenhancer.util.timeDeltaMinutesFromNow
import hu.bme.aut.android.sleephabitenhancer.viewmodel.SleepEnhancerViewModel

class AlarmListViewFragment : Fragment(), AlarmRecyclerViewAdapter.AlarmItemClickListener,
    AlarmSetterDialogFragment.TimeChooserDialogListener, AlarmManagerStateChangeHandler {


    private var _binding: FragmentAlarmListViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var alarmRecyclerViewAdapter: AlarmRecyclerViewAdapter
    private lateinit var sleepEnhancerViewModel: SleepEnhancerViewModel
    private val alarmManagerStateChangeHandler: AlarmManagerStateChangeHandler = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sleepEnhancerViewModel = ViewModelProvider(this)[SleepEnhancerViewModel::class.java]
        alarmRecyclerViewAdapter = AlarmRecyclerViewAdapter()
        sleepEnhancerViewModel.alarms.observe(this) { alarmsList ->
            alarmRecyclerViewAdapter.submitList(alarmsList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentAlarmListViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.btnAddAlarm.setOnClickListener {
            AlarmSetterDialogFragment(this).show(
                parentFragmentManager,
                AlarmSetterDialogFragment.TAG
            )
        }
    }

    private fun setupRecyclerView() {
        binding.rvAlarms.addItemDecoration(
            DividerItemDecoration(
                binding.rvAlarms.context,
                DividerItemDecoration.VERTICAL
            )
        )
        alarmRecyclerViewAdapter.itemClickListener = this
        binding.rvAlarms.adapter = alarmRecyclerViewAdapter
    }

    override fun onItemClick(alarm: Alarm) {
        AlarmSetterDialogFragment(this, alarm).show(
            parentFragmentManager,
            AlarmSetterDialogFragment.TAG
        )
    }

    override fun onItemLongClick(position: Int, view: View, alarm: Alarm): Boolean {
        val ctx = context ?: return false
        val popup = PopupMenu(ctx, view)
        popup.inflate(R.menu.longclick_menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    sleepEnhancerViewModel.delete(alarm)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
        return false
    }

    override fun onAlarmActiveStateChange(alarm: Alarm) {
        alarm.active = !alarm.active
        sleepEnhancerViewModel.update(alarm)
        context?.let {
            if (alarm.active) {
                // enable
                alarmManagerStateChangeHandler.setNotification(it, alarm)
                alarmManagerStateChangeHandler.setAlarm(it, alarm)
            } else {
                // disable
                alarmManagerStateChangeHandler.cancelNotification(it, alarm)
                alarmManagerStateChangeHandler.cancelAlarm(it, alarm)
            }
        }
        Snackbar.make(
            binding.rvAlarms,
            if (alarm.active) getString(
                R.string.message_alarm_on,
                alarm.alarmDue,
                alarm.reminderDue
            ) else getString(
                R.string.message_alarm_off
            ),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onAlarmCreated(newItem: Alarm) {
        sleepEnhancerViewModel.insert(newItem)
        context?.let {
            alarmManagerStateChangeHandler.setNotification(it, newItem)
            alarmManagerStateChangeHandler.setAlarm(it, newItem)
        }
        showAlarmSetSnackbar(newItem)
    }

    override fun onAlarmEdited(editedItem: Alarm) {
        sleepEnhancerViewModel.update(editedItem)
        context?.let {
            if (editedItem.active) {
                alarmManagerStateChangeHandler.cancelNotification(it, editedItem)
                alarmManagerStateChangeHandler.cancelAlarm(it, editedItem)
                alarmManagerStateChangeHandler.setNotification(it, editedItem)
                alarmManagerStateChangeHandler.setAlarm(it, editedItem)
            }
        }
        showAlarmSetSnackbar(editedItem)
    }

    override fun onInvalidValueOnSave() {
        Snackbar.make(
            binding.rvAlarms,
            getString(R.string.message_invalid_value_on_save),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showAlarmSetSnackbar(alarm: Alarm) {
        val timeUntilReminder = alarm.reminderDue.timeDeltaFromNow()
        val timeUntilAlarm = alarm.alarmDue.timeDeltaFromNow()

        Snackbar.make(
            binding.rvAlarms,
            // TODO: move to strings.xml
            "Alarm set. Time until bedtime: $timeUntilReminder, time until alarm: $timeUntilAlarm",
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun setNotification(ctx: Context, alarm: Alarm) {
        val pendingIntent =
            NotificationHelper.createPendingIntentForBedtimeNotification(ctx, alarm.id.toInt())
        AlarmHelper.scheduleNotification(
            ctx,
            alarm.reminderDue.timeDeltaMinutesFromNow(),
            pendingIntent
        )
    }

    override fun setAlarm(ctx: Context, alarm: Alarm) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.getBroadcast(
                context,
                alarm.id.toInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            ) else
                PendingIntent.getBroadcast(context, alarm.id.toInt(), intent, 0)
        AlarmHelper.scheduleAlarm(
            ctx,
            alarm.alarmDue.timeDeltaMinutesFromNow().toLong() * 60,
            alarmIntent
        )
    }

    override fun cancelNotification(ctx: Context, alarm: Alarm) {
        val pendingIntent =
            NotificationHelper.createPendingIntentForBedtimeNotification(ctx, alarm.id.toInt())
        AlarmHelper.cancelNotification(ctx, pendingIntent)
    }

    override fun cancelAlarm(ctx: Context, alarm: Alarm) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.getBroadcast(
                context,
                alarm.id.toInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            ) else
                PendingIntent.getBroadcast(context, alarm.id.toInt(), intent, 0)
        AlarmHelper.cancelNotification(ctx, alarmIntent)
    }
}
