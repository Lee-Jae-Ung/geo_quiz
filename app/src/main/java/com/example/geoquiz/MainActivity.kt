package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView


    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    private var count = 0.0
    private var countCor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)

        //chap3 챌린지 : 점수 보여주기
        trueButton.setOnClickListener {
            checkAnswer(true)
            if(countCor == questionBank.size){
                Toast.makeText(this,"정답률 : "+countCor*100/count+"%",Toast.LENGTH_SHORT).show()
                Log.d("예아","countCor ="+countCor+"count ="+count)
            }
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            if(countCor == questionBank.size){
                Toast.makeText(this,"정답률 : "+countCor*100/count+"%",Toast.LENGTH_SHORT).show()
                Log.d("예아","countCor ="+countCor+"count ="+count)
            }
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()



        }

        previousButton.setOnClickListener {
            if(currentIndex > 0) {
                currentIndex = (currentIndex - 1) % questionBank.size
                updateQuestion()
            } else {
                currentIndex = questionBank.size - 1
                updateQuestion()
            }
        }

        //chap2 챌린지1 textview 에 리스터 추가하기
        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
//chap3 챌린지: 정답 맞춘 문제를 건너뛰기
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        if (!questionBank[currentIndex].isCor) {
            trueButton.visibility = Button.VISIBLE
            falseButton.visibility = Button.VISIBLE

        } else {
            trueButton.visibility = Button.INVISIBLE
            falseButton.visibility = Button.INVISIBLE


        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        if (messageResId == R.string.correct_toast) {

            questionBank[currentIndex].isCor = true
            trueButton.visibility = Button.INVISIBLE
            falseButton.visibility = Button.INVISIBLE
            count += 1
            countCor += 1

        } else {
            count += 1
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

    }



}