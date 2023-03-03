package com.albusaidyy.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.albusaidyy.a7minutesworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private  var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

       // setSupportActionBar(binding?.toolbarHistoryActivity)
        if (supportActionBar != null) {
            //activate back button
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            //go back to previous screen
            onBackPressed()
        }
    }
}