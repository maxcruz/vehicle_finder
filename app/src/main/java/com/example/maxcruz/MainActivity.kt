package com.example.maxcruz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.maxcruz.vehiclefinder.R

/**
 * Single Activity container
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
