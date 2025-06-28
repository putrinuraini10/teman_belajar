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
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import android.graphics.Matrix
import android.graphics.PointF
import android.view.MotionEvent
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var mainContentLayout: LinearLayout
    private lateinit var fragmentContainer: FrameLayout
    private val draggableZoomableImageViews = mutableListOf<ImageView>()
    private val animatedViews = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.utama)

        mainContentLayout = findViewById(R.id.main_content_layout)
        fragmentContainer = findViewById(R.id.fragment_container)

        mainContentLayout.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE

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

        val infoIcon = findViewById<ImageView>(R.id.info_icon)
        infoIcon?.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }
        val logoKecil = findViewById<ImageView>(R.id.logo_kecil)
        logoKecil?.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }

        val btnMembaca = findViewById<Button>(R.id.btnmembaca)
        val btnBelajarAngka = findViewById<Button>(R.id.btn_belajarangka)
        val btnMengenalWarna = findViewById<Button>(R.id.btn_mengenalwarna)
        val btnBernyanyi = findViewById<Button>(R.id.btnbernyanyi) // KOREKSI DI SINI

        animatedViews.add(btnMembaca)
        animatedViews.add(btnBelajarAngka)
        animatedViews.add(btnMengenalWarna)
        animatedViews.add(btnBernyanyi)

        animatedViews.forEach { setupLoopingScaleAnimation(it) }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    if (supportFragmentManager.backStackEntryCount == 0) {
                        mainContentLayout.visibility = View.VISIBLE
                        fragmentContainer.visibility = View.GONE
                        setDecorativeImageViewsVisibility(View.VISIBLE)
                        startAllLoopingAnimations()
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
                setDecorativeImageViewsVisibility(View.VISIBLE)
                startAllLoopingAnimations()
            } else {
                mainContentLayout.visibility = View.GONE
                fragmentContainer.visibility = View.VISIBLE
                setDecorativeImageViewsVisibility(View.GONE)
                stopAllLoopingAnimations()
            }
        }

        btnMembaca.setOnClickListener {
            playAudio(R.raw.membaca)
            Toast.makeText(this, "Memutar audio Membaca", Toast.LENGTH_SHORT).show()
            stopAllLoopingAnimations()
            val intent = Intent(this, membaca::class.java)
            startActivity(intent)
        }

        btnBelajarAngka.setOnClickListener {
            playAudio(R.raw.belajarangka)
            Toast.makeText(this, "Memutar audio Belajar Angka", Toast.LENGTH_SHORT).show()
            stopAllLoopingAnimations()
            val intent = Intent(this, BelajarAngkaActivity::class.java)
            startActivity(intent)
        }

        btnMengenalWarna.setOnClickListener {
            playAudio(R.raw.mengenalwarna)
            Toast.makeText(this, "Memutar audio Mengenal Warna", Toast.LENGTH_SHORT).show()
            mainContentLayout.visibility = View.GONE
            fragmentContainer.visibility = View.VISIBLE
            setDecorativeImageViewsVisibility(View.GONE)
            stopAllLoopingAnimations()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WarnaFragment())
                .addToBackStack(null)
                .commit()
        }

        btnBernyanyi.setOnClickListener {
            playAudio(R.raw.bernyanyi)
            Toast.makeText(this, "Memutar audio Bernyanyi", Toast.LENGTH_SHORT).show()
            mainContentLayout.visibility = View.GONE
            fragmentContainer.visibility = View.VISIBLE
            setDecorativeImageViewsVisibility(View.GONE)
            stopAllLoopingAnimations()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BernyanyiFragment())
                .addToBackStack(null)
                .commit()
        }

        infoIcon.setOnClickListener {
            showAboutUsPopup()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        stopAllLoopingAnimations()
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
}