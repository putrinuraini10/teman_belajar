package com.example.temanbelajar

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.FrameLayout
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
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.activity.OnBackPressedCallback

class BelajarAngkaActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var fragmentContainer: FragmentContainerView
    private lateinit var mainContentLayout: LinearLayout
    private val draggableZoomableImageViews = mutableListOf<ImageView>()
    private val animatedViews = mutableListOf<View>()
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private lateinit var quizQuestions: List<Question>
    private var quizAlertDialog: AlertDialog? = null
    private var selectedOptionButton: Button? = null
    private var isAnswerChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.klikbelajarangka)

        fragmentContainer = findViewById(R.id.fragment_container_belajarangka)
        mainContentLayout = findViewById(R.id.main_content_layout_belajarangka)

        val btnAngka: Button = findViewById(R.id.btn_angka)
        val btnKuis: Button = findViewById(R.id.btn_kuis)
        val infoIcon: ImageView = findViewById(R.id.info_icon)

        findViewById<ImageView>(R.id.hiasann)?.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }
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
        findViewById<ImageView>(R.id.logo_kecil)?.let {
            setupDraggableZoomableImageView(it)
            animatedViews.add(it)
        }

        animatedViews.add(btnAngka)
        animatedViews.add(btnKuis)
        animatedViews.add(infoIcon)

        animatedViews.forEach { setupLoopingScaleAnimation(it) }

        btnAngka.setOnClickListener {
            playAudioAndNavigate(R.raw.angkaa, AngkaFragment())
        }

        btnKuis.setOnClickListener {
            playAudio(R.raw.kuis)
            showQuizPopup()
        }

        infoIcon.setOnClickListener {
            showAboutUsPopup()
        }

        quizQuestions = getQuizQuestions()
    }

    private fun getQuizQuestions(): List<Question> {
        return listOf(
            Question("Ada berapa banyak matahari di langit?", listOf("1", "2", "3"), 0, R.drawable.image_matahari, R.raw.pertanyaansatu),
            Question("Ada berapa kelinci di gambar ini?", listOf("1", "2", "3"), 2, R.drawable.image_kelinci, R.raw.pertanyaandua),
            Question("Berapa banyak jari di satu tangan?", listOf("3", "5", "10"), 1, null, R.raw.pertanyaantiga),
            Question("Ada berapa apel merah di gambar?", listOf("2", "4", "5"), 0, R.drawable.image_apel, R.raw.pertanyaanempat),
            Question("Berapa banyak mata yang kamu punya?", listOf("1", "2", "3"), 1, null, R.raw.pertanyaanlima)
        )
    }

    private fun showQuizPopup() {
        currentQuestionIndex = 0
        correctAnswersCount = 0
        isAnswerChecked = false

        val dialogView = LayoutInflater.from(this).inflate(R.layout.quiz_dialog_layout, null)
        val questionContainer: FrameLayout = dialogView.findViewById(R.id.quiz_question_container)
        val btnBack: Button = dialogView.findViewById(R.id.btn_quiz_back)
        val btnNext: Button = dialogView.findViewById(R.id.btn_quiz_next)

        quizAlertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        quizAlertDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        displayQuestion(questionContainer)

        btnBack.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                displayQuestion(questionContainer)
                isAnswerChecked = false
            } else {
                quizAlertDialog?.dismiss()
                Toast.makeText(this, "Kuis ditutup.", Toast.LENGTH_SHORT).show()
                startAllLoopingAnimations()
            }
        }

        btnNext.setOnClickListener {
            if (!isAnswerChecked) {
                Toast.makeText(this, "Pilih jawaban dulu ya!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (currentQuestionIndex < quizQuestions.size - 1) {
                currentQuestionIndex++
                displayQuestion(questionContainer)
                isAnswerChecked = false
            } else {
                showQuizResult()
                quizAlertDialog?.dismiss()
                startAllLoopingAnimations()
            }
        }

        quizAlertDialog?.show()
    }

    private fun displayQuestion(container: FrameLayout) {
        container.removeAllViews()
        selectedOptionButton = null

        val currentQuestion = quizQuestions[currentQuestionIndex]

        currentQuestion.audioResId?.let { playAudio(it) }

        val questionView = LayoutInflater.from(this).inflate(R.layout.quiz_question_item, container, false)

        val tvQuestionNumber: TextView = questionView.findViewById(R.id.tv_question_number)
        val tvQuestion: TextView = questionView.findViewById(R.id.tv_question)
        val btnOptionA: Button = questionView.findViewById(R.id.btn_option_a)
        val btnOptionB: Button = questionView.findViewById(R.id.btn_option_b)
        val btnOptionC: Button = questionView.findViewById(R.id.btn_option_c)
        val ivQuestionImage: ImageView = questionView.findViewById(R.id.iv_question_image)

        tvQuestionNumber.text = "Pertanyaan ${currentQuestionIndex + 1}/${quizQuestions.size}"
        tvQuestion.text = currentQuestion.questionText

        btnOptionA.text = currentQuestion.options[0]
        btnOptionB.text = currentQuestion.options[1]
        btnOptionC.text = currentQuestion.options[2]

        if (currentQuestion.imageResId != null) {
            ivQuestionImage.setImageResource(currentQuestion.imageResId)
            ivQuestionImage.visibility = View.VISIBLE
        } else {
            ivQuestionImage.visibility = View.GONE
        }

        val optionButtons = listOf(btnOptionA, btnOptionB, btnOptionC)

        optionButtons.forEachIndexed { index, button ->
            button.setBackgroundResource(R.drawable.quiz_option_button)
            button.setOnClickListener {
                if (!isAnswerChecked) {
                    selectedOptionButton?.setBackgroundResource(R.drawable.quiz_option_button)
                    selectedOptionButton = button
                    selectedOptionButton?.setBackgroundColor(ContextCompat.getColor(this, R.color.quiz_default_orange))
                    checkAnswer(button, index, currentQuestion.correctAnswerIndex)
                }
            }
        }

        container.addView(questionView)
    }

    private fun checkAnswer(selectedButton: Button, selectedIndex: Int, correctAnswerIndex: Int) {
        isAnswerChecked = true

        if (selectedIndex == correctAnswerIndex) {
            playAudio(R.raw.correct_answer_sound)
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.quiz_correct_green))
            correctAnswersCount++
            Toast.makeText(this, "Hebat! Jawabanmu Benar!", Toast.LENGTH_SHORT).show()
        } else {
            playAudio(R.raw.wrong_answer_sound)
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.quiz_wrong_red))
            val parentView = selectedButton.parent as? View
            val correctButton = parentView?.findViewById<Button>(
                when (correctAnswerIndex) {
                    0 -> R.id.btn_option_a
                    1 -> R.id.btn_option_b
                    2 -> R.id.btn_option_c
                    else -> 0
                }
            )
            correctButton?.setBackgroundColor(ContextCompat.getColor(this, R.color.quiz_correct_green))
            Toast.makeText(this, "Wah, kurang tepat. Coba lagi ya!", Toast.LENGTH_SHORT).show()
        }

        (selectedButton.parent as? LinearLayout)?.let { parentLayout ->
            for (i in 0 until parentLayout.childCount) {
                val child = parentLayout.getChildAt(i)
                if (child is Button) {
                    child.isClickable = false
                }
            }
        }
    }

    private fun showQuizResult() {
        val resultMessage = when {
            correctAnswersCount == quizQuestions.size -> "Luar Biasa! Semua Jawabanmu Benar!\nKamu Hebat!"
            correctAnswersCount >= quizQuestions.size / 2 -> "Hebat! Kamu menjawab ${correctAnswersCount} dari ${quizQuestions.size} pertanyaan dengan benar!"
            else -> "Jangan menyerah! Kamu menjawab ${correctAnswersCount} dari ${quizQuestions.size} pertanyaan dengan benar. Coba lagi ya!"
        }

        val builder = AlertDialog.Builder(this)
            .setTitle("Hasil Kuis")
            .setMessage(resultMessage)
            .setPositiveButton("Main Lagi") { dialog, _ -> dialog.dismiss(); showQuizPopup() }
            .setNegativeButton("Selesai") { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()
        playAudio(R.raw.quiz_finish_sound)
    }

    private fun playAudio(audioResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaPlayer = MediaPlayer.create(this, audioResId)
        mediaPlayer?.setOnCompletionListener { mp -> mp.release(); mediaPlayer = null }
        mediaPlayer?.start()
    }

    private fun playAudioAndNavigate(audioResId: Int, fragment: Fragment) {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaPlayer = MediaPlayer.create(this, audioResId)
        mediaPlayer?.setOnCompletionListener { mp -> mp.release(); mediaPlayer = null; navigateToFragment(fragment) }
        mediaPlayer?.start()
    }

    private fun navigateToFragment(fragment: Fragment) {
        mainContentLayout.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
        setDecorativeImageViewsVisibility(View.GONE)
        stopAllLoopingAnimations()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_belajarangka, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showAboutUsPopup() {
        val title = "Tentang kami"
        val message = "Aplikasi ini merupakan aplikasi edukasi untuk anak-anak yang bertujuan membantu belajar mengeja, mengenal huruf abjad, angka, warna, dan bernyanyi sambil belajar.\n\nDibuat oleh:\nPutri Nuraini\nHikmatun Nazilah\nClara Putri Andini"

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
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
        var lastDownX = 0f
        var lastDownY = 0f
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
                    dy = (viewHeight - drawableHeight * scale) / 2f
                    dx = 0f
                } else {
                    scale = viewHeight / drawableHeight
                    dx = (viewWidth - drawableWidth * scale) / 2f
                    dy = 0f
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
        } else if (quizAlertDialog != null && quizAlertDialog!!.isShowing) {
            quizAlertDialog?.dismiss()
            startAllLoopingAnimations()
        } else {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    override fun onResume() {
        super.onResume()
        if (supportFragmentManager.backStackEntryCount == 0 && (quizAlertDialog == null || !quizAlertDialog!!.isShowing)) {
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
        quizAlertDialog?.dismiss()
    }
}
