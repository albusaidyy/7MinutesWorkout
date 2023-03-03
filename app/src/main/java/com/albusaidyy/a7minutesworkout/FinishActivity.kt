package com.albusaidyy.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.albusaidyy.a7minutesworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private  var binding:ActivityFinishBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar != null) {
            //activate back button
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            //go back to previous screen
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            finish()
        }
    }
}