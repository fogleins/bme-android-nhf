package hu.bme.aut.android.sleephabitenhancer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.sleephabitenhancer.SleepEnhancerApplication
import hu.bme.aut.android.sleephabitenhancer.model.Alarm
import hu.bme.aut.android.sleephabitenhancer.repository.Repository
import kotlinx.coroutines.launch

class SleepEnhancerViewModel : ViewModel() {
    private val repository: Repository
    val alarms: LiveData<List<Alarm>>

    init {
        val alarmDao = SleepEnhancerApplication.sleepEnhancerDatabase.alarmDao()
        repository = Repository(alarmDao)
        alarms = repository.getAlarms()
    }

    fun insert(alarm: Alarm) = viewModelScope.launch {
        repository.insert(alarm)
    }

    fun delete(alarm: Alarm) = viewModelScope.launch {
        repository.delete(alarm)
    }

    fun update(alarm: Alarm) = viewModelScope.launch {
        repository.update(alarm)
    }
}