package com.addin.androidintermediatecodelab.custom_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.addin.androidintermediatecodelab.R

class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        title = "Custom View"
    }
}