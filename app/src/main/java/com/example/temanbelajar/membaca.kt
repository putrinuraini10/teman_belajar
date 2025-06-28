package com.example.temanbelajar

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.temanbelajar.Mengeja1Fragment

class membaca : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var fragmentContainer: FragmentContainerView
    private lateinit var mainContentLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.klikmembaca)

        fragmentContainer = findViewById(R.id.fragment_container_membaca)
        mainContentLayout = findViewById(R.id.main_content_layout_membaca)

        val btnHurufAbjad: Button = findViewById(R.id.btn_hurufabjad)
        val btnMengeja: Button = findViewById(R.id.btn_mengeja)
        val infoIcon: ImageView = findViewById(R.id.info_icon)

        btnHurufAbjad.setOnClickListener {
            playAudioAndNavigate(R.raw.hurufabjad, AbjadFragment())
        }

        btnMengeja.setOnClickListener {
            playAudioAndNavigate(R.raw.mengeja, Mengeja1Fragment())
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

    private fun playAudioAndNavigate(audioResId: Int, fragment: Fragment) {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaPlayer = MediaPlayer.create(this, audioResId)
        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release()
            mediaPlayer = null
            navigateToFragment(fragment)
        }
        mediaPlayer?.start()
    }

    private fun navigateToFragment(fragment: Fragment) {
        mainContentLayout.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_membaca, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            if (supportFragmentManager.backStackEntryCount == 1) {
                fragmentContainer.visibility = View.GONE
                mainContentLayout.visibility = View.VISIBLE
            }
        } else {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}