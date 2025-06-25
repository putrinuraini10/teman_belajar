package com.example.temanbelajar

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Button
import androidx.fragment.app.Fragment

class AngkaFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_angka, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNumberButtons(view)
        setupExitButton(view)
    }

    private fun setupNumberButtons(view: View) {
        val btn1 = view.findViewById<ImageButton>(R.id.btn_1)
        btn1.setOnClickListener { playAudio(R.raw.satu) }

        val btn2 = view.findViewById<ImageButton>(R.id.btn_2)
        btn2.setOnClickListener { playAudio(R.raw.dua) }

        val btn3 = view.findViewById<ImageButton>(R.id.btn_3)
        btn3.setOnClickListener { playAudio(R.raw.tiga) }

        val btn4 = view.findViewById<ImageButton>(R.id.btn_4)
        btn4.setOnClickListener { playAudio(R.raw.empat) }

        val btn5 = view.findViewById<ImageButton>(R.id.btn_5)
        btn5.setOnClickListener { playAudio(R.raw.lima) }

        val btn6 = view.findViewById<ImageButton>(R.id.btn_6)
        btn6.setOnClickListener { playAudio(R.raw.enam) }

        val btn7 = view.findViewById<ImageButton>(R.id.btn_7)
        btn7.setOnClickListener { playAudio(R.raw.tujuh) }

        val btn8 = view.findViewById<ImageButton>(R.id.btn_8)
        btn8.setOnClickListener { playAudio(R.raw.delapan) }

        val btn9 = view.findViewById<ImageButton>(R.id.btn_9)
        btn9.setOnClickListener { playAudio(R.raw.sembilan) }

        val btn10 = view.findViewById<ImageButton>(R.id.btn_10)
        btn10.setOnClickListener { playAudio(R.raw.sepuluh) } // Diubah

        val btn11 = view.findViewById<ImageButton>(R.id.btn_11)
        btn11.setOnClickListener { playAudio(R.raw.sebelas) } // Diubah

        val btn20 = view.findViewById<ImageButton>(R.id.btn_20)
        btn20.setOnClickListener { playAudio(R.raw.duapuluh) } // Diubah

        val btn100 = view.findViewById<ImageButton>(R.id.btn_100)
        btn100.setOnClickListener { playAudio(R.raw.seratus) } // Diubah

        val btn1000 = view.findViewById<ImageButton>(R.id.btn_1000)
        btn1000.setOnClickListener { playAudio(R.raw.seribu) } // Diubah

        val btn10000 = view.findViewById<ImageButton>(R.id.btn_10000)
        btn10000.setOnClickListener { playAudio(R.raw.sepuluh_ribu) } // Diubah

        val btn100000 = view.findViewById<ImageButton>(R.id.btn_100000)
        btn100000.setOnClickListener { playAudio(R.raw.seratus_ribu) } // Diubah
    }

    private fun setupExitButton(view: View) {
        val btnKeluar: Button = view.findViewById(R.id.btn_keluar)
        btnKeluar.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun playAudio(audioResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaPlayer = MediaPlayer.create(context, audioResId)
        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release()
            mediaPlayer = null
        }
        mediaPlayer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
