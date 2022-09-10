package com.addin.androidintermediatecodelab.seat_selector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.addin.androidintermediatecodelab.R

class SeatSelectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selector)

        title = "Seat Selector"

        val seatView = findViewById<SeatsView>(R.id.seatsView)
        val button = findViewById<Button>(R.id.finishButton)

        button.setOnClickListener {
            seatView.seat?.let {
                Toast.makeText(this, "Kursi Anda nomor ${it.name}", Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(this, "Silahkan pilih kursi terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}