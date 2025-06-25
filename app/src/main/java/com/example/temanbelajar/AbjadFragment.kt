package com.example.temanbelajar

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Button
import androidx.fragment.app.Fragment

class AbjadFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_abjad, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLetterButtons(view)
        setupExitButton(view)
    }

    private fun setupLetterButtons(view: View) {
        val btnA = view.findViewById<ImageButton>(R.id.btn_huruf_a)
        btnA.setOnClickListener { playAudio(R.raw.hurufa) }

        val btnB = view.findViewById<ImageButton>(R.id.btn_huruf_b)
        btnB.setOnClickListener { playAudio(R.raw.hurufb) }

        val btnC = view.findViewById<ImageButton>(R.id.btn_huruf_c)
        btnC.setOnClickListener { playAudio(R.raw.hurufc) }

        val btnD = view.findViewById<ImageButton>(R.id.btn_huruf_d)
        btnD.setOnClickListener { playAudio(R.raw.hurufd) }

        val btnE = view.findViewById<ImageButton>(R.id.btn_huruf_e)
        btnE.setOnClickListener { playAudio(R.raw.hurufe) }

        val btnF = view.findViewById<ImageButton>(R.id.btn_huruf_f)
        btnF.setOnClickListener { playAudio(R.raw.huruff) }

        val btnG = view.findViewById<ImageButton>(R.id.btn_huruf_g)
        btnG.setOnClickListener { playAudio(R.raw.hurufg) }

        val btnH = view.findViewById<ImageButton>(R.id.btn_huruf_h)
        btnH.setOnClickListener { playAudio(R.raw.hurufh) }

        val btnI = view.findViewById<ImageButton>(R.id.btn_huruf_i)
        btnI.setOnClickListener { playAudio(R.raw.hurufi) }

        val btnJ = view.findViewById<ImageButton>(R.id.btn_huruf_j)
        btnJ.setOnClickListener { playAudio(R.raw.hurufj) }

        val btnK = view.findViewById<ImageButton>(R.id.btn_huruf_k)
        btnK.setOnClickListener { playAudio(R.raw.hurufk) }

        val btnL = view.findViewById<ImageButton>(R.id.btn_huruf_l)
        btnL.setOnClickListener { playAudio(R.raw.hurufl) }

        val btnM = view.findViewById<ImageButton>(R.id.btn_huruf_m)
        btnM.setOnClickListener { playAudio(R.raw.hurufm) }

        val btnN = view.findViewById<ImageButton>(R.id.btn_huruf_n)
        btnN.setOnClickListener { playAudio(R.raw.hurufn) }

        val btnO = view.findViewById<ImageButton>(R.id.btn_huruf_o)
        btnO.setOnClickListener { playAudio(R.raw.hurufo) }

        val btnP = view.findViewById<ImageButton>(R.id.btn_huruf_p)
        btnP.setOnClickListener { playAudio(R.raw.hurufp) }

        val btnQ = view.findViewById<ImageButton>(R.id.btn_huruf_q)
        btnQ.setOnClickListener { playAudio(R.raw.hurufq) }

        val btnR = view.findViewById<ImageButton>(R.id.btn_huruf_r)
        btnR.setOnClickListener { playAudio(R.raw.hurufr) }

        val btnS = view.findViewById<ImageButton>(R.id.btn_huruf_s)
        btnS.setOnClickListener { playAudio(R.raw.hurufs) }

        val btnT = view.findViewById<ImageButton>(R.id.btn_huruf_t)
        btnT.setOnClickListener { playAudio(R.raw.huruft) }

        val btnU = view.findViewById<ImageButton>(R.id.btn_huruf_u)
        btnU.setOnClickListener { playAudio(R.raw.hurufu) }

        val btnV = view.findViewById<ImageButton>(R.id.btn_huruf_v)
        btnV.setOnClickListener { playAudio(R.raw.hurufv) }

        val btnW = view.findViewById<ImageButton>(R.id.btn_huruf_w)
        btnW.setOnClickListener { playAudio(R.raw.hurufw) }

        val btnX = view.findViewById<ImageButton>(R.id.btn_huruf_x)
        btnX.setOnClickListener { playAudio(R.raw.hurufx) }

        val btnY = view.findViewById<ImageButton>(R.id.btn_huruf_y)
        btnY.setOnClickListener { playAudio(R.raw.hurufy) }

        val btnZ = view.findViewById<ImageButton>(R.id.btn_huruf_z)
        btnZ.setOnClickListener { playAudio(R.raw.hurufz) }
    }

    private fun setupExitButton(view: View) {
        val btnKeluar: Button = view.findViewById(R.id.btn_keluar)
        btnKeluar.setOnClickListener {
            val intent = Intent(activity, membaca::class.java)
            startActivity(intent)
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
