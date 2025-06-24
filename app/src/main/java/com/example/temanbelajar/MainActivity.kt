package com.example.temanbelajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.media.MediaPlayer
import android.widget.ImageView
import android.content.Intent
import androidx.fragment.app.FragmentManager // Tambahkan ini jika belum ada

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.utama) // Pastikan ini mengacu ke utama.xml

        // Inisialisasi tombol-tombol dari layout utama.xml dengan ID yang telah diperbarui
        val btnMembaca = findViewById<Button>(R.id.btnmembaca) // Diperbarui
        val btnBelajarAngka = findViewById<Button>(R.id.btn_belajarangka) // Diperbarui
        val btnMengenalWarna = findViewById<Button>(R.id.btn_mengenalwarna) // Diperbarui
        val btnBernyanyi = findViewById<Button>(R.id.btnbernyanyi) // Diperbarui
        val infoIcon = findViewById<ImageView>(R.id.info_icon)

        // Listener untuk tombol "Membaca"
        btnMembaca.setOnClickListener {
            playAudio(R.raw.membaca)
            Toast.makeText(this, "Memutar audio Membaca", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, membaca::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        // Listener untuk tombol "Belajar Angka"
        btnBelajarAngka.setOnClickListener {
            playAudio(R.raw.belajarangka)
            Toast.makeText(this, "Memutar audio Belajar Angka", Toast.LENGTH_SHORT).show()
            // Optional: Tambahkan Intent untuk Activity Belajar Angka di sini jika ada
        }

        // Listener untuk tombol "Mengenal Warna"
        btnMengenalWarna.setOnClickListener {
            playAudio(R.raw.mengenalwarna)
            Toast.makeText(this, "Memutar audio Mengenal Warna", Toast.LENGTH_SHORT).show()
            // Optional: Tambahkan Intent untuk Activity Mengenal Warna di sini jika ada
        }

        // Listener untuk tombol "Bernyanyi"
        btnBernyanyi.setOnClickListener {
            playAudio(R.raw.bernyanyi)
            Toast.makeText(this, "Memutar audio Bernyanyi", Toast.LENGTH_SHORT).show()
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, BernyanyiFragment())
                .addToBackStack(null)
                .commit()
        }

        // Listener untuk ikon informasi
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