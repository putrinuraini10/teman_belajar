package com.example.temanbelajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.utama)

        val btnMembaca = findViewById<ImageButton>(R.id.btnMembaca)
        val btnBelajarAngka = findViewById<ImageButton>(R.id.btnBelajarAngka)
        val btnMengenalWarna = findViewById<ImageButton>(R.id.btnMengenalWarna)
        val btnBernyanyi = findViewById<ImageButton>(R.id.btnBernyanyi)

        btnMembaca.setOnClickListener {
            Toast.makeText(this, "Tombol Membaca diklik", Toast.LENGTH_SHORT).show()
        }
        btnBelajarAngka.setOnClickListener {
            Toast.makeText(this, "Tombol Belajar Angka diklik", Toast.LENGTH_SHORT).show()
        }
        btnMengenalWarna.setOnClickListener {
            Toast.makeText(this, "Tombol Mengenal Warna diklik", Toast.LENGTH_SHORT).show()
        }
        btnBernyanyi.setOnClickListener {
            Toast.makeText(this, "Tombol Bernyanyi diklik", Toast.LENGTH_SHORT).show()
        }
    }
}
