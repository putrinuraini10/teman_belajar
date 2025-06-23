package com.example.temanbelajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.utama)

        val btnBelajarAngka = findViewById<Button>(R.id.btn_belajar_angka)
        val btnMengenalWarna = findViewById<Button>(R.id.btn_mengenal_warna)
        val btnMembaca = findViewById<Button>(R.id.btn_membaca)
        val btnBernyanyi = findViewById<Button>(R.id.btn_bernyanyi)

        btnBelajarAngka.setOnClickListener {
            Toast.makeText(this, "Tombol Belajar Angka diklik", Toast.LENGTH_SHORT).show()
        }

        btnMengenalWarna.setOnClickListener {
            Toast.makeText(this, "Tombol Mengenal Warna diklik", Toast.LENGTH_SHORT).show()
        }

        btnMembaca.setOnClickListener {
            Toast.makeText(this, "Tombol Membaca diklik", Toast.LENGTH_SHORT).show()
        }

        btnBernyanyi.setOnClickListener {
            Toast.makeText(this, "Tombol Bernyanyi diklik", Toast.LENGTH_SHORT).show()
        }
    }
}