package com.addin.androidintermediatecodelab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.addin.androidintermediatecodelab.custom_view.CustomViewActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mnCustomView: LinearLayoutCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mnCustomView = findViewById(R.id.mn_custom_view)
        mnCustomView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.mn_custom_view -> {
                    val customViewIntent = Intent(this@MainActivity, CustomViewActivity::class.java)
                    startActivity(customViewIntent)
                }
            }
        }
    }
}