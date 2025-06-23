package com.example.temanbelajar # Sesuaikan dengan package aplikasi Anda

import android.content.Intent // Import Intent untuk navigasi
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class tentangkami : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tentangkami)

        val kembaliIcon: ImageView = findViewById(R.id.kembali_icon)

        kembaliIcon.setOnClickListener {
            // Membuat Intent untuk pindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Memulai MainActivity
            finish()
        }
    }
}