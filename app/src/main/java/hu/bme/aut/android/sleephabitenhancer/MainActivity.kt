package hu.bme.aut.android.sleephabitenhancer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.sleephabitenhancer.databinding.ActivityMainBinding
import hu.bme.aut.android.sleephabitenhancer.viewmodel.SleepEnhancerViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sleepEnhancerViewModel: SleepEnhancerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sleepEnhancerViewModel = ViewModelProvider(this).get(SleepEnhancerViewModel::class.java)
        sleepEnhancerViewModel.alarms.observe(this) { alarms2 ->
            // TODO: pass alarms to recycler view adapter
        }
    }
}