package com.example.temanbelajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.media.MediaPlayer
import android.widget.ImageView
import android.content.Intent
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.utama)

        val btnMembaca = findViewById<Button>(R.id.btn_membaca)
        val btnBelajarAngka = findViewById<Button>(R.id.btn_belajar_angka)
        val btnMengenalWarna = findViewById<Button>(R.id.btn_mengenal_warna)
        val btnBernyanyi = findViewById<Button>(R.id.btn_bernyanyi)
        val infoIcon = findViewById<ImageView>(R.id.info_icon)

        btnMembaca.setOnClickListener {
            playAudio(R.raw.membaca)
            Toast.makeText(this, "Memutar audio Membaca", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, membaca::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btnBelajarAngka.setOnClickListener {
            playAudio(R.raw.belajarangka)
            Toast.makeText(this, "Memutar audio Belajar Angka", Toast.LENGTH_SHORT).show()
            // Optional: Tambahkan Intent untuk Activity Belajar Angka di sini
        }

        btnMengenalWarna.setOnClickListener {
            playAudio(R.raw.mengenalwarna)
            Toast.makeText(this, "Memutar audio Mengenal Warna", Toast.LENGTH_SHORT).show()
            // Optional: Tambahkan Intent untuk Activity Mengenal Warna di sini
        }

        btnBernyanyi.setOnClickListener {
            playAudio(R.raw.bernyanyi)
            Toast.makeText(this, "Memutar audio Bernyanyi", Toast.LENGTH_SHORT).show()
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, BernyanyiFragment())
                .addToBackStack(null)
                .commit()
        }

        infoIcon.setOnClickListener {
            val intent = Intent(this, tentangkami::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun playAudio(audioResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = null

        mediaPlayer = MediaPlayer.create(this, audioResId)

        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release()
            mediaPlayer = null
        }

        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}