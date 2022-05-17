package hu.bme.aut.android.sleephabitenhancer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.sleephabitenhancer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}