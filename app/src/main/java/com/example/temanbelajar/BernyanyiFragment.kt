package com.example.temanbelajar

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.VideoView
import android.widget.Toast
import android.media.MediaPlayer

import androidx.fragment.app.Fragment

class BernyanyiFragment : Fragment() {

    private var currentlyPlayingVideoView: VideoView? = null
    private var currentPlayButton: ImageView? = null

    private lateinit var fullScreenVideoContainer: RelativeLayout
    private lateinit var fullScreenVideoView: VideoView
    private lateinit var closeButton: ImageView

    private var originalOrientation: Int = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    private val thumbnailVideoInfo = mutableMapOf<VideoView, Pair<ImageView, String>>()

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

        fullScreenVideoContainer = view.findViewById(R.id.full_screen_video_container)
        fullScreenVideoView = view.findViewById(R.id.fullScreenVideoView)
        closeButton = view.findViewById(R.id.closeButton)

        originalOrientation = requireActivity().requestedOrientation

        // GANTI PATH VIDEO KEMBALI KE LOKAL DENGAN NAMA BARU
        val videoPath1 = "android.resource://" + requireContext().packageName + "/" + R.raw.abcde // Perubahan di sini
        val videoPath2 = "android.resource://" + requireContext().packageName + "/" + R.raw.lagupenjumlahan // Tetap sama
        val videoPath3 = "android.resource://" + requireContext().packageName + "/" + R.raw.angka // Perubahan di sini

        setupThumbnailVideoPlayer(videoView1, playButton1, videoPath1)
        setupThumbnailVideoPlayer(videoView2, playButton2, videoPath2)
        setupThumbnailVideoPlayer(videoView3, playButton3, videoPath3)

        closeButton.setOnClickListener {
            exitFullScreenVideo()
        }

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.action == android.view.KeyEvent.ACTION_UP) {
                if (fullScreenVideoContainer.visibility == View.VISIBLE) {
                    exitFullScreenVideo()
                    return@setOnKeyListener true
                }
            }
            false
        }

        return view
    }

    private fun setupThumbnailVideoPlayer(videoView: VideoView, playButton: ImageView, videoPath: String) {
        thumbnailVideoInfo[videoView] = Pair(playButton, videoPath)

        videoView.setOnPreparedListener { mp ->
            Log.d(TAG, "Thumbnail Video Prepared: ${videoView.id}")
            playButton.visibility = View.GONE
        }

        videoView.setOnCompletionListener {
            Log.d(TAG, "Thumbnail Video Completed: ${videoView.id}")
            videoView.seekTo(0)
            playButton.visibility = View.VISIBLE
        }

        videoView.setOnErrorListener { mp, what, extra ->
            Log.e(TAG, "Thumbnail Video Error: ${videoView.id}, what=$what, extra=$extra")
            playButton.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "Error pada video thumbnail. Coba lagi.", Toast.LENGTH_SHORT).show()
            false
        }

        playButton.setOnClickListener {
            Log.d(TAG, "Play button clicked for thumbnail: ${videoView.id}")
            stopAllThumbnailVideos()
            enterFullScreenVideo(videoPath)
        }

        videoView.setOnClickListener {
            Log.d(TAG, "VideoView thumbnail clicked: ${videoView.id}. Is playing: ${videoView.isPlaying}")
            if (!videoView.isPlaying) {
                playButton.visibility = View.VISIBLE
            }
        }
    }

    private fun stopAllThumbnailVideos() {
        thumbnailVideoInfo.forEach { (videoView, pair) ->
            val playButton = pair.first
            val videoPath = pair.second

            if (videoView.isPlaying) {
                videoView.stopPlayback()
                Log.d(TAG, "Stopped thumbnail video: ${videoView.id}")
            }
            videoView.setVideoURI(Uri.parse(videoPath))
            videoView.seekTo(0)
            playButton.visibility = View.VISIBLE
        }
        currentlyPlayingVideoView = null
        currentPlayButton = null
    }

    private fun enterFullScreenVideo(videoPath: String) {
        view?.findViewById<ScrollView>(R.id.video_list_scroll_view)?.visibility = View.GONE
        view?.findViewById<LinearLayout>(R.id.header_layout)?.visibility = View.GONE

        fullScreenVideoContainer.visibility = View.VISIBLE

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(fullScreenVideoView)
        fullScreenVideoView.setMediaController(mediaController)

        fullScreenVideoView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )

        fullScreenVideoView.setVideoURI(Uri.parse(videoPath))
        fullScreenVideoView.start()

        fullScreenVideoView.setOnCompletionListener {
            Log.d(TAG, "Full Screen Video Completed.")
            exitFullScreenVideo()
        }

        fullScreenVideoView.setOnErrorListener { mp, what, extra ->
            Log.e(TAG, "Full Screen Video Error: what=$what, extra=$extra")
            val errorMessage = when (what) {
                android.media.MediaPlayer.MEDIA_ERROR_SERVER_DIED -> "Server media mati. Coba lagi."
                android.media.MediaPlayer.MEDIA_ERROR_UNKNOWN -> "Format video tidak didukung atau rusak."
                else -> "Tidak dapat memutar video ini (Error: $what)."
            }
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            exitFullScreenVideo()
            true
        }

        Log.d(TAG, "Entered full screen video for: $videoPath")
    }

    private fun exitFullScreenVideo() {
        fullScreenVideoView.stopPlayback()

        fullScreenVideoContainer.visibility = View.GONE

        view?.findViewById<ScrollView>(R.id.video_list_scroll_view)?.visibility = View.VISIBLE
        view?.findViewById<LinearLayout>(R.id.header_layout)?.visibility = View.VISIBLE

        requireActivity().requestedOrientation = originalOrientation

        fullScreenVideoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE)

        stopAllThumbnailVideos()
        Log.d(TAG, "Exited full screen video.")
    }

    override fun onResume() {
        super.onResume()
        stopAllThumbnailVideos()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called. Stopping playback if any.")
        if (fullScreenVideoContainer.visibility == View.VISIBLE) {
            fullScreenVideoView.pause()
        }
        stopAllThumbnailVideos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView called. Stopping playback completely.")
        if (fullScreenVideoContainer.visibility == View.VISIBLE) {
            fullScreenVideoView.stopPlayback()
        }
        stopAllThumbnailVideos()
        thumbnailVideoInfo.clear()
        requireActivity().requestedOrientation = originalOrientation
    }
}