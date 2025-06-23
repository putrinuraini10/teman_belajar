package com.example.temanbelajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.media.MediaPlayer
import android.widget.ImageView
import android.content.Intent

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
        }

        btnBelajarAngka.setOnClickListener {
            Toast.makeText(this, "Tombol Belajar Angka diklik", Toast.LENGTH_SHORT).show()
        }
        btnMengenalWarna.setOnClickListener {
            Toast.makeText(this, "Tombol Mengenal Warna diklik", Toast.LENGTH_SHORT).show()
        }
        btnBernyanyi.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, BernyanyiFragment())
                .addToBackStack(null)
                .commit()
        }

        infoIcon.setOnClickListener {
            val intent = Intent(this, tentangkami::class.java)
            startActivity(intent)
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