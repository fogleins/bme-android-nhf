package hu.bme.aut.android.sleephabitenhancer.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.sleephabitenhancer.R
import hu.bme.aut.android.sleephabitenhancer.adapter.AlarmRecyclerViewAdapter
import hu.bme.aut.android.sleephabitenhancer.databinding.FragmentAlarmListViewBinding
import hu.bme.aut.android.sleephabitenhancer.model.Alarm
import hu.bme.aut.android.sleephabitenhancer.viewmodel.SleepEnhancerViewModel

class AlarmListViewFragment : Fragment(), AlarmRecyclerViewAdapter.AlarmItemClickListener,
    AlarmSetterDialogFragment.TimeChooserDialogListener {


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
            AlarmSetterDialogFragment(this).show(
                parentFragmentManager,
                AlarmSetterDialogFragment.TAG
            )
        }
    }

    private fun setupRecyclerView() {
        alarmRecyclerViewAdapter.itemClickListener = this
        binding.rvAlarms.adapter = alarmRecyclerViewAdapter
    }

    override fun onItemClick(alarm: Alarm) {
        AlarmSetterDialogFragment(this, alarm).show(
            parentFragmentManager,
            AlarmSetterDialogFragment.TAG
        )
    }

    override fun onAlarmActiveStateChange(alarm: Alarm) {
        alarm.active = !alarm.active
        sleepEnhancerViewModel.update(alarm)
    }

    override fun onAlarmCreated(newItem: Alarm) {
        sleepEnhancerViewModel.insert(newItem)
    }

    override fun onAlarmEdited(editedItem: Alarm) {
        sleepEnhancerViewModel.update(editedItem)
    }

    override fun onInvalidValueOnSave() {
        Snackbar.make(
            binding.rvAlarms,
            getString(R.string.message_invalid_value_on_save),
            Snackbar.LENGTH_LONG
        ).show()
    }
}
