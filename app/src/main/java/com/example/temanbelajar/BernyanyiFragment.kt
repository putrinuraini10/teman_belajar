package com.example.temanbelajar

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BernyanyiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BernyanyiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var videoView1: VideoView
    private lateinit var videoView2: VideoView
    private lateinit var videoView3: VideoView
    private lateinit var playButton1: ImageView
    private lateinit var playButton2: ImageView
    private lateinit var playButton3: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bernyanyi, container, false)

        videoView1 = view.findViewById(R.id.videoView1)
        videoView2 = view.findViewById(R.id.videoView2)
        videoView3 = view.findViewById(R.id.videoView3)
        playButton1 = view.findViewById(R.id.playButton1)
        playButton2 = view.findViewById(R.id.playButton2)
        playButton3 = view.findViewById(R.id.playButton3)

        setupVideo(videoView1, playButton1, R.raw.laguhuruf)
        setupVideo(videoView2, playButton2, R.raw.lagupenjumlahan)
        setupVideo(videoView3, playButton3, R.raw.mengenalangka)

        return view
    }

    private fun setupVideo(videoView: VideoView, playButton: ImageView, videoResId: Int) {
        val uri = Uri.parse("android.resource://${requireActivity().packageName}/$videoResId")
        videoView.setVideoURI(uri)

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        playButton.setOnClickListener {
            playButton.visibility = View.GONE
            videoView.start()
        }


    }


        companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BernyanyiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                BernyanyiFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}