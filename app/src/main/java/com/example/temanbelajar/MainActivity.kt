package com.example.temanbelajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.media.MediaPlayer
import android.widget.ImageView
import android.content.Intent
import android.widget.LinearLayout
import android.view.View
import android.widget.FrameLayout // Pastikan FrameLayout diimpor
import androidx.activity.OnBackPressedCallback

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var mainContentLayout: LinearLayout
    private lateinit var fragmentContainer: FrameLayout // Deklarasikan FrameLayout di sini

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.utama)

        mainContentLayout = findViewById(R.id.main_content_layout)
        fragmentContainer = findViewById(R.id.fragment_container) // Inisialisasi FrameLayout

        // Secara default, tampilkan mainContentLayout dan sembunyikan fragmentContainer
        mainContentLayout.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE


        val btnMembaca = findViewById<Button>(R.id.btnmembaca)
        val btnBelajarAngka = findViewById<Button>(R.id.btn_belajarangka)
        val btnMengenalWarna = findViewById<Button>(R.id.btn_mengenalwarna)
        val btnBernyanyi = findViewById<Button>(R.id.btnbernyanyi)
        val infoIcon = findViewById<ImageView>(R.id.info_icon)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    // Setelah pop, cek apakah stack kosong untuk menampilkan kembali mainContentLayout
                    if (supportFragmentManager.backStackEntryCount == 0) {
                        mainContentLayout.visibility = View.VISIBLE
                        fragmentContainer.visibility = View.GONE // Sembunyikan container fragment lagi
                    }
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                mainContentLayout.visibility = View.VISIBLE
                fragmentContainer.visibility = View.GONE
            } else {
                mainContentLayout.visibility = View.GONE
                fragmentContainer.visibility = View.VISIBLE
            }
        }

        btnMembaca.setOnClickListener {
            playAudio(R.raw.membaca)
            Toast.makeText(this, "Memutar audio Membaca", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, membaca::class.java)
            startActivity(intent)
        }

        btnBelajarAngka.setOnClickListener {
            playAudio(R.raw.belajarangka)
            Toast.makeText(this, "Memutar audio Belajar Angka", Toast.LENGTH_SHORT).show()
            mainContentLayout.visibility = View.GONE // Sembunyikan layout utama
            fragmentContainer.visibility = View.VISIBLE // Tampilkan container fragment

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AngkaFragment())
                .addToBackStack(null)
                .commit()
        }

        btnMengenalWarna.setOnClickListener {
            playAudio(R.raw.mengenalwarna)
            Toast.makeText(this, "Memutar audio Mengenal Warna", Toast.LENGTH_SHORT).show()
        }

        btnBernyanyi.setOnClickListener {
            playAudio(R.raw.bernyanyi)
            Toast.makeText(this, "Memutar audio Bernyanyi", Toast.LENGTH_SHORT).show()
            mainContentLayout.visibility = View.GONE // Sembunyikan layout utama
            fragmentContainer.visibility = View.VISIBLE // Tampilkan container fragment

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BernyanyiFragment())
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
