package edu.aims.mainacivity

// This is where the logic for our app goes

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var score = 0
    private var gameStarted = false

    private lateinit var gameScoreTextView: TextView
    private lateinit var timeLeftTextView: TextView
    private lateinit var tapMeButton: TextView

    private lateinit var countDownTimer: CountDownTimer
    private var initialCountDown: Long = 60000
    private var countDownInterval: Long = 1000
    private var timeLeft = 60


    // onCreate is the entry point to this activity. override meaning youll have to provide a custom
    // implementation from the base AppCompactActivity class.
    override fun onCreate(savedInstanceState: Bundle?) {
        // super.onCreate is required. by doing this it lets android know to set up before you do
        super.onCreate(savedInstanceState)
        // Below takes the layout I made and puts in on screen. R.layout.layout_name
        // Any logic I add must come after SetContentView
        setContentView(R.layout.activity_main)

        //1 findviewbyid searches the activity_main layout to find the view with the same id and
        // references it
        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapMeButton = findViewById(R.id.tap_me_button)

        // 2 setOnClickLisener listens for button clicks then increments
        tapMeButton.setOnClickListener {incrementScore()}
        // connect views to variables

        resetGame()



    }

    @SuppressLint("StringFormatInvalid")
    private fun incrementScore() {
        if (!gameStarted){
            startGame()
        }
        score ++

        val newScore = getString(R.string.your_score, score)
        gameScoreTextView.text = newScore
    }


    @SuppressLint("StringFormatInvalid")
    private fun resetGame(){
        // 1
        score = 0

        val initialScore = getString(R.string.your_score, score)
        gameScoreTextView.text = initialScore

        val initialTimeLeft = getString(R.string.time_left, 60)
        timeLeftTextView.text = initialTimeLeft
        // 2
        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval) {
            //3
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, timeLeft)
                timeLeftTextView.text = timeLeftString
            }
            override fun onFinish() {
                endGame()
            }
        }
        // 4
        gameStarted = false
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        // U make use of Toast to notify something to the user. alert to inform the user that an
        // event has occurred
        Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
        resetGame()
    }
}