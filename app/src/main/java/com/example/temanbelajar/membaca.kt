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
import androidx.appcompat.app.AlertDialog
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Matrix
import android.graphics.PointF
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator

class membaca : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var fragmentContainer: FragmentContainerView
    private lateinit var mainContentLayout: LinearLayout

    private val draggableZoomableImageViews = mutableListOf<ImageView>()
    private val animatedViews = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.klikmembaca)

        fragmentContainer = findViewById(R.id.fragment_container_membaca)
        mainContentLayout = findViewById(R.id.main_content_layout_membaca)

        val btnHurufAbjad: Button = findViewById(R.id.btn_hurufabjad)
        val btnMengeja: Button = findViewById(R.id.btn_mengeja)
        val infoIcon: ImageView = findViewById(R.id.info_icon)

        findViewById<ImageView>(R.id.desain)?.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }
        findViewById<ImageView>(R.id.desainn)?.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }
        findViewById<ImageView>(R.id.bawah)?.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }
        findViewById<ImageView>(R.id.bawahl)?.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }
        findViewById<ImageView>(R.id.bubble)?.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }

        infoIcon.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }
        findViewById<ImageView>(R.id.logo_kecil)?.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }

        animatedViews.add(btnHurufAbjad)
        animatedViews.add(btnMengeja)

        animatedViews.forEach { setupLoopingScaleAnimation(it) }

        btnHurufAbjad.setOnClickListener {
            playAudioAndNavigate(R.raw.hurufabjad, AbjadFragment())
            stopAllLoopingAnimations()
        }

        btnMengeja.setOnClickListener {
            playAudio(R.raw.mengeja)
            stopAllLoopingAnimations()
            navigateToFragment(Mengeja1Fragment()) // Tambahkan navigasi ke MengejaFragment
        }

        infoIcon.setOnClickListener {
            showAboutUsPopup()
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
        setDecorativeImageViewsVisibility(View.GONE)
        stopAllLoopingAnimations()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_membaca, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showAboutUsPopup() {
        val title = "Tentang kami"
        val message = """
            Aplikasi ini merupakan aplikasi edukasi untuk anak-anak yang bertujuan membantu belajar mengeja, mengenal huruf abjad, angka, warna, dan bernyanyi sambil belajar.

            Dibuat oleh:
            Putri Nuraini
            Hikmatun Nazilah
            Clara Putri Andini
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupLoopingScaleAnimation(view: View) {
        val scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.98f)
        val scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.98f)

        scaleDownX.duration = 1000
        scaleDownY.duration = 1000

        scaleDownX.repeatCount = ObjectAnimator.INFINITE
        scaleDownY.repeatCount = ObjectAnimator.INFINITE
        scaleDownX.repeatMode = ObjectAnimator.REVERSE
        scaleDownY.repeatMode = ObjectAnimator.REVERSE
        scaleDownX.interpolator = AccelerateDecelerateInterpolator()
        scaleDownY.interpolator = AccelerateDecelerateInterpolator()

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleDownX, scaleDownY)
        view.setTag(R.id.view_animation_tag, animatorSet)
        animatorSet.start()
    }

    private fun stopAllLoopingAnimations() {
        animatedViews.forEach {
            val animatorSet = it.getTag(R.id.view_animation_tag) as? AnimatorSet
            animatorSet?.cancel()
            it.scaleX = 1.0f
            it.scaleY = 1.0f
        }
    }

    private fun startAllLoopingAnimations() {
        animatedViews.forEach {
            val animatorSet = it.getTag(R.id.view_animation_tag) as? AnimatorSet
            animatorSet?.start()
        }
    }

    private fun setDecorativeImageViewsVisibility(visibility: Int) {
        draggableZoomableImageViews.forEach { it.visibility = visibility }
    }

    private fun setupDraggableZoomableImageView(imageView: ImageView) {
        draggableZoomableImageViews.add(imageView)

        val matrix = Matrix()
        val savedMatrix = Matrix()

        val NONE = 0
        val DRAG = 1
        val ZOOM = 2
        var mode = NONE

        val start = PointF()
        val mid = PointF()
        var oldDist = 1f

        var lastDownX: Float = 0f
        var lastDownY: Float = 0f
        var clickThreshold = 10f

        imageView.post {
            val drawable = imageView.drawable
            if (drawable != null) {
                val viewWidth = imageView.width.toFloat()
                val viewHeight = imageView.height.toFloat()
                val drawableWidth = drawable.intrinsicWidth.toFloat()
                val drawableHeight = drawable.intrinsicHeight.toFloat()

                val scaleX = viewWidth / drawableWidth
                val scaleY = viewHeight / drawableHeight

                val scale: Float
                val dx: Float
                val dy: Float

                if (drawableWidth * viewHeight > viewWidth * drawableHeight) {
                    scale = viewWidth / drawableWidth
                    dy = (viewHeight - drawableHeight * scale) / 2.0f
                    dx = 0.0f
                } else {
                    scale = viewHeight / drawableHeight
                    dx = (viewWidth - drawableWidth * scale) / 2.0f
                    dy = 0.0f
                }

                matrix.setScale(scale, scale)
                matrix.postTranslate(dx, dy)

                imageView.imageMatrix = matrix
                savedMatrix.set(matrix)
            }
        }

        imageView.setOnTouchListener { v, event ->
            if (v.visibility != View.VISIBLE) return@setOnTouchListener false

            val view = v as ImageView
            matrix.set(view.imageMatrix)

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val animatorSet = view.getTag(R.id.view_animation_tag) as? AnimatorSet
                    animatorSet?.cancel()
                    view.scaleX = 1.0f
                    view.scaleY = 1.0f

                    savedMatrix.set(matrix)
                    start.set(event.x, event.y)
                    lastDownX = event.x
                    lastDownY = event.y
                    mode = DRAG
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    val animatorSet = view.getTag(R.id.view_animation_tag) as? AnimatorSet
                    animatorSet?.cancel()
                    view.scaleX = 1.0f
                    view.scaleY = 1.0f

                    oldDist = spacing(event)
                    if (oldDist > 10f) {
                        savedMatrix.set(matrix)
                        midPoint(mid, event)
                        mode = ZOOM
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = Math.abs(event.x - lastDownX)
                    val dy = Math.abs(event.y - lastDownY)
                    if (mode == DRAG && (dx > clickThreshold || dy > clickThreshold)) {
                        matrix.set(savedMatrix)
                        matrix.postTranslate(event.x - start.x, event.y - start.y)
                        view.imageMatrix = matrix
                        return@setOnTouchListener true
                    } else if (mode == ZOOM) {
                        val newDist = spacing(event)
                        if (newDist > 10f) {
                            matrix.set(savedMatrix)
                            val scale = newDist / oldDist
                            matrix.postScale(scale, scale, mid.x, mid.y)
                            view.imageMatrix = matrix
                            return@setOnTouchListener true
                        }
                    }
                    return@setOnTouchListener false
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    mode = NONE
                    view.scaleX = 1.0f
                    view.scaleY = 1.0f
                    val animatorSet = view.getTag(R.id.view_animation_tag) as? AnimatorSet
                    animatorSet?.start()

                    val dx = Math.abs(event.x - lastDownX)
                    val dy = Math.abs(event.y - lastDownY)
                    if (dx < clickThreshold && dy < clickThreshold) {
                        v.performClick()
                        return@setOnTouchListener false
                    }
                    return@setOnTouchListener false
                }
            }
            return@setOnTouchListener true
        }
    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point.set(x / 2, y / 2)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            if (supportFragmentManager.backStackEntryCount == 0) {
                fragmentContainer.visibility = View.GONE
                mainContentLayout.visibility = View.VISIBLE
                setDecorativeImageViewsVisibility(View.VISIBLE)
                startAllLoopingAnimations()
            }
        } else {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    override fun onResume() {
        super.onResume()
        if (supportFragmentManager.backStackEntryCount == 0) {
            startAllLoopingAnimations()
        }
    }

    override fun onPause() {
        super.onPause()
        stopAllLoopingAnimations()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        stopAllLoopingAnimations()
    }
}