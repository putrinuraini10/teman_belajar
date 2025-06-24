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
            // Contoh: Pindah ke Activity lain untuk belajar huruf abjad
            // val intent = Intent(this, HurufAbjadActivity::class.java)
            // startActivity(intent)
            // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btnMengeja.setOnClickListener {
            // Contoh: Pindah ke Activity lain untuk belajar mengeja
            // val intent = Intent(this, MengejaActivity::class.java)
            // startActivity(intent)
            // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        infoIcon.setOnClickListener {
            val intent = Intent(this, tentangkami::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Animasi saat tombol kembali fisik ditekan dari activity 'membaca'
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}