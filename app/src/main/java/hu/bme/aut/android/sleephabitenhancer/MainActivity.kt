package hu.bme.aut.android.sleephabitenhancer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.sleephabitenhancer.adapter.AlarmRecyclerViewAdapter
import hu.bme.aut.android.sleephabitenhancer.databinding.ActivityMainBinding
import hu.bme.aut.android.sleephabitenhancer.model.Alarm
import hu.bme.aut.android.sleephabitenhancer.viewmodel.SleepEnhancerViewModel

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var fragment: AlarmListViewFragment
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
////        val fragmentTransaction = supportFragmentManager.beginTransaction()
////        fragment = AlarmListViewFragment()
////        fragmentTransaction.add(binding.mainContent.id, fragment)
////        fragmentTransaction.commit()
//    }
//}

class MainActivity : AppCompatActivity(), AlarmRecyclerViewAdapter.AlarmItemClickListener {
    private lateinit var alarmRecyclerViewAdapter: AlarmRecyclerViewAdapter

    private lateinit var binding: ActivityMainBinding
    private lateinit var sleepEnhancerViewModel: SleepEnhancerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnAddAlarm.setOnClickListener { view ->
            // TODO
        }

        setupRecyclerView()

        sleepEnhancerViewModel = ViewModelProvider(this).get(SleepEnhancerViewModel::class.java)
        sleepEnhancerViewModel.alarms.observe(this) { alarms ->
            alarmRecyclerViewAdapter.submitList(alarms)
        }
    }

    private fun setupRecyclerView() {
        alarmRecyclerViewAdapter = AlarmRecyclerViewAdapter()
        alarmRecyclerViewAdapter.itemClickListener = this
        binding.root.findViewById<RecyclerView>(R.id.rvAlarms).adapter =
            alarmRecyclerViewAdapter
    }

    override fun onItemClick(alarm: Alarm) {
        // TODO
    }

    override fun onAlarmActiveStateChange(alarm: Alarm) {
        // TODO
        alarm.active = false
        sleepEnhancerViewModel.update(alarm)
    }

//    override fun onItemLongClick(position: Int, view: View, todo: Todo): Boolean {
//        val popup = PopupMenu(this, view)
//        popup.inflate(R.menu.menu_todo)
//        popup.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                R.id.delete -> {
//                    sleepEnhancerViewModel.delete(todo)
//                    return@setOnMenuItemClickListener true
//                }
//            }
//            false
//        }
//        popup.show()
//        return false
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_list, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.itemCreateTodo) {
//            val todoCreateFragment = TodoCreateFragment()
//            todoCreateFragment.show(supportFragmentManager, "TAG")
//        } else if (item.itemId == R.id.itemDeleteTodos) {
//            sleepEnhancerViewModel.deleteAll()
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onTodoCreated(todo: Todo) {
//        sleepEnhancerViewModel.insert(todo)
//    }
}