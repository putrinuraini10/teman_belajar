package com.example.temanbelajar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class membaca : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.klikmembaca)

        val btnHurufAbjad: Button = findViewById(R.id.btn_hurufabjad)
        val btnMengeja: Button = findViewById(R.id.btn_mengeja)
        val infoIcon: ImageView = findViewById(R.id.info_icon)

        btnHurufAbjad.setOnClickListener {

        }

        btnMengeja.setOnClickListener {

        }

        infoIcon.setOnClickListener {
            val intent = Intent(this, tentangkami::class.java)
            startActivity(intent)
        }
    }
}