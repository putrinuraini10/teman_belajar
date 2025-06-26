package com.example.temanbelajar

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment

class BernyanyiFragment : Fragment() {

    private var currentPlayingVideo: VideoView? = null
    private var currentPlayingPlayButton: ImageView? = null

    companion object {
        private const val TAG = "BernyanyiFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bernyanyi, container, false)

        val videoView1: VideoView = view.findViewById(R.id.videoView1)
        val playButton1: ImageView = view.findViewById(R.id.playButton1)

        val videoView2: VideoView = view.findViewById(R.id.videoView2)
        val playButton2: ImageView = view.findViewById(R.id.playButton2)

        val videoView3: VideoView = view.findViewById(R.id.videoView3)
        val playButton3: ImageView = view.findViewById(R.id.playButton3)

        // Ubah jalur video untuk videoPath2 menjadi lagupenjumlahan
        val videoPath1 = "android.resource://" + requireContext().packageName + "/" + R.raw.laguabc
        val videoPath2 =
            "android.resource://" + requireContext().packageName + "/" + R.raw.lagupenjumlahan // <-- INI YANG DIUBAH
        val videoPath3 =
            "android.resource://" + requireContext().packageName + "/" + R.raw.mengenalangka

        setupVideoPlayer(videoView1, playButton1, videoPath1)
        setupVideoPlayer(videoView2, playButton2, videoPath2)
        setupVideoPlayer(videoView3, playButton3, videoPath3)

        return view
    }

    private fun setupVideoPlayer(videoView: VideoView, playButton: ImageView, videoPath: String) {
        videoView.setVideoURI(Uri.parse(videoPath))

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.setOnPreparedListener { mp ->
            Log.d(TAG, "Video Prepared: ${videoView.id}")
            // Jika kamu ingin video langsung tersembunyi setelah prepared, biarkan ini.
            // Namun, untuk kasus play button, umumnya playButton akan tersembunyi setelah di-klik dan video mulai.
            // playButton.visibility = View.GONE
        }

        videoView.setOnCompletionListener { mp ->
            Log.d(TAG, "Video Completed: ${videoView.id}")
            playButton.visibility = View.VISIBLE
            videoView.seekTo(0)
            currentPlayingVideo = null
            currentPlayingPlayButton = null
        }

        videoView.setOnErrorListener { mp, what, extra ->
            Log.e(TAG, "Video Error: ${videoView.id}, what=$what, extra=$extra")
            playButton.visibility = View.VISIBLE
            currentPlayingVideo = null
            currentPlayingPlayButton = null
            false
        }

        playButton.setOnClickListener {
            Log.d(TAG, "Play button clicked for: ${videoView.id}")
            stopCurrentVideo()

            videoView.start()
            playButton.visibility = View.GONE
            currentPlayingVideo = videoView
            currentPlayingPlayButton = playButton
            Log.d(TAG, "New video started: ${videoView.id}")
        }

        videoView.setOnClickListener {
            Log.d(TAG, "VideoView clicked for: ${videoView.id}. Is playing: ${videoView.isPlaying}")
            // Tampilkan kembali tombol play jika video tidak sedang diputar saat VideoView diklik
            if (!videoView.isPlaying) {
                playButton.visibility = View.VISIBLE
            }
        }
    }

    private fun stopCurrentVideo() {
        Log.d(TAG, "Attempting to stop current video.")
        currentPlayingVideo?.let {
            Log.d(TAG, "currentPlayingVideo is not null. ID: ${it.id}, Is playing: ${it.isPlaying}")
            if (it.isPlaying) {
                it.stopPlayback()
                Log.d(TAG, "Video stopped: ${it.id}")
                currentPlayingPlayButton?.visibility = View.VISIBLE
                it.seekTo(0) // Reset posisi video ke awal
            } else {
                Log.d(TAG, "currentPlayingVideo is not playing.")
            }
        } ?: run {
            Log.d(TAG, "currentPlayingVideo is null.")
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called.")
        // Jeda video yang sedang diputar dan tampilkan tombol play-nya
        currentPlayingVideo?.pause()
        currentPlayingPlayButton?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView called. Stopping playback if any.")
        // Hentikan pemutaran sepenuhnya saat tampilan dihancurkan
        currentPlayingVideo?.stopPlayback()
        currentPlayingVideo = null
        currentPlayingPlayButton = null
    }
}