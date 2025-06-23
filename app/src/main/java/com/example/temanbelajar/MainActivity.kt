package com.example.temanbelajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.media.MediaPlayer // Import MediaPlayer

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null // Deklarasikan MediaPlayer sebagai nullable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.utama)

        val btnMembaca = findViewById<Button>(R.id.btn_membaca)
        val btnBelajarAngka = findViewById<Button>(R.id.btn_belajar_angka)
        val btnMengenalWarna = findViewById<Button>(R.id.btn_mengenal_warna)
        val btnBernyanyi = findViewById<Button>(R.id.btn_bernyanyi)

        btnMembaca.setOnClickListener {
            // Pastikan membaca.mp3 ada di res/raw/membaca.mp3
            playAudio(R.raw.membaca)
            Toast.makeText(this, "Memutar audio Membaca", Toast.LENGTH_SHORT).show()
        }

        btnBelajarAngka.setOnClickListener {
            Toast.makeText(this, "Tombol Belajar Angka diklik", Toast.LENGTH_SHORT).show()
            // Jika Anda ingin menambahkan audio di sini, tambahkan: playAudio(R.raw.nama_audio_angka)
        }
        btnMengenalWarna.setOnClickListener {
            Toast.makeText(this, "Tombol Mengenal Warna diklik", Toast.LENGTH_SHORT).show()
            // Jika Anda ingin menambahkan audio di sini, tambahkan: playAudio(R.raw.nama_audio_warna)
        }
        btnBernyanyi.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, BernyanyiFragment()) // Ganti layout utama dengan fragment
                .addToBackStack(null)
                .commit()
            // Jika Anda ingin menambahkan audio di sini, tambahkan: playAudio(R.raw.nama_audio_bernyanyi)
        }
    }

    // Fungsi untuk memutar audio
    private fun playAudio(audioResId: Int) {
        // Hentikan dan bebaskan MediaPlayer yang sedang berjalan (jika ada)
        mediaPlayer?.release()
        mediaPlayer = null

        // Buat instance MediaPlayer baru
        mediaPlayer = MediaPlayer.create(this, audioResId)

        // Set listener untuk mengetahui kapan audio selesai diputar
        mediaPlayer?.setOnCompletionListener { mp ->
            // Bebaskan sumber daya MediaPlayer setelah selesai diputar
            mp.release()
            mediaPlayer = null // Set kembali ke null setelah dibebaskan
        }

        // Mulai memutar audio
        mediaPlayer?.start()
    }

    // Penting: Pastikan MediaPlayer dibebaskan saat Activity dihancurkan
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Bebaskan sumber daya
        mediaPlayer = null
    }
}