package hu.bme.aut.android.sleephabitenhancer.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.sleephabitenhancer.adapter.AlarmRecyclerViewAdapter
import hu.bme.aut.android.sleephabitenhancer.databinding.FragmentAlarmListViewBinding
import hu.bme.aut.android.sleephabitenhancer.model.Alarm
import hu.bme.aut.android.sleephabitenhancer.viewmodel.SleepEnhancerViewModel

class AlarmListViewFragment : Fragment(), AlarmRecyclerViewAdapter.AlarmItemClickListener {


    private var _binding: FragmentAlarmListViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var alarmRecyclerViewAdapter: AlarmRecyclerViewAdapter
    private lateinit var sleepEnhancerViewModel: SleepEnhancerViewModel

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
            // TODO: Add alarm dialog
            sleepEnhancerViewModel.insert(
                Alarm(
                    name = "my first alarm",
                    reminderDue = "23:15",
                    alarmDue = "8:30",
                    active = true,
                    id = 0
                )
            )
        }
    }

    private fun setupRecyclerView() {
//        val alarms = alarmRecyclerViewAdapter.currentList
        alarmRecyclerViewAdapter.itemClickListener = this
//        alarmRecyclerViewAdapter.setAlarmsList(alarms)
        binding.rvAlarms.adapter = alarmRecyclerViewAdapter
    }

    override fun onItemClick(alarm: Alarm) {
        // TODO: Edit alarm dialog
    }

    override fun onAlarmActiveStateChange(alarm: Alarm) {
        // TODO: gomb állapota változzon meg
        alarm.active = !alarm.active
        sleepEnhancerViewModel.update(alarm)
    }
}
