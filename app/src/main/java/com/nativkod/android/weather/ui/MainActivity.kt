package com.nativkod.android.weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nativkod.android.weather.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = " "
    }
}