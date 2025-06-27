package com.example.temanbelajar

import android.media.MediaPlayer // Import MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast // Opsional: Untuk pesan toast saat keluar

class WarnaFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment. Pastikan R.layout.fragment_warna cocok dengan nama file XML Anda.
        return inflater.inflate(R.layout.fragment_warna, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Temukan semua View kotak warna dan tambahkan OnClickListener
        view.findViewById<View>(R.id.color_merah).setOnClickListener { playAudio(R.raw.merah) }
        view.findViewById<View>(R.id.color_kuning).setOnClickListener { playAudio(R.raw.kuning) }
        view.findViewById<View>(R.id.color_hijau).setOnClickListener { playAudio(R.raw.hijau) }
        view.findViewById<View>(R.id.color_biru).setOnClickListener { playAudio(R.raw.biru) }
        view.findViewById<View>(R.id.color_pink).setOnClickListener { playAudio(R.raw.pink) }
        view.findViewById<View>(R.id.color_abu).setOnClickListener { playAudio(R.raw.abuabu) }
        view.findViewById<View>(R.id.color_hitam).setOnClickListener { playAudio(R.raw.hitam) }
        view.findViewById<View>(R.id.color_oren).setOnClickListener { playAudio(R.raw.oren) }
        view.findViewById<View>(R.id.color_coklat).setOnClickListener { playAudio(R.raw.coklat) }
        view.findViewById<View>(R.id.color_cream).setOnClickListener { playAudio(R.raw.krim) }
        view.findViewById<View>(R.id.color_ungu).setOnClickListener { playAudio(R.raw.ungu) }
        // Perhatikan penamaan file audio: R.raw.birumuda jika nama filenya birumuda.mp3/ogg
        view.findViewById<View>(R.id.color_biru_muda).setOnClickListener { playAudio(R.raw.birumuda) }
        view.findViewById<View>(R.id.color_navy).setOnClickListener { playAudio(R.raw.navy) }
        // Perhatikan penamaan file audio: R.raw.kuningemas jika nama filenya kuningemas.mp3/ogg
        view.findViewById<View>(R.id.color_kuning_emas).setOnClickListener { playAudio(R.raw.kuningemas) }
        view.findViewById<View>(R.id.color_mint).setOnClickListener { playAudio(R.raw.mint) }
        view.findViewById<View>(R.id.color_lilac).setOnClickListener { playAudio(R.raw.lilac) }


        val btnKeluar: Button = view.findViewById(R.id.btn_keluar)
        btnKeluar.setOnClickListener {
            // Ini akan mengeluarkan fragmen saat ini dari back stack
            requireActivity().supportFragmentManager.popBackStack()
            Toast.makeText(requireContext(), "Kembali ke menu utama", Toast.LENGTH_SHORT).show() // Pesan opsional
        }
    }

    // Fungsi untuk memutar audio
    private fun playAudio(audioResId: Int) {
        mediaPlayer?.release() // Pastikan media player sebelumnya dilepaskan
        mediaPlayer = null // Set ke null
        mediaPlayer = MediaPlayer.create(context, audioResId) // Buat instance baru

        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release() // Lepaskan sumber daya setelah audio selesai diputar
            mediaPlayer = null
        }
        mediaPlayer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Sangat penting: Lepaskan sumber daya MediaPlayer saat tampilan fragmen dihancurkan
        mediaPlayer?.release()
        mediaPlayer = null
    }
}