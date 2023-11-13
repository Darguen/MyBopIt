package pvidela.MyBopIt

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import kotlin.math.sqrt
import kotlin.random.Random

class GameActivity : AppCompatActivity(), SensorEventListener {

    private var randomText = Random
    private lateinit var wordListTextView: TextView
    private var words: Array<String> = arrayOf("Tap", "DoubleTap", "Fling", "Shake", "LongPress")
    private var wordToShow: String = ""
    private lateinit var mediaPlayerDefeat: MediaPlayer
    private lateinit var mediaPlayerGame: MediaPlayer
    private lateinit var mediaPlayerGoodInput: MediaPlayer
    private var playbackParams: PlaybackParams? = null
    private val speedChange = 0.2f
    private val gameOverText = "Game Over"
    private lateinit var scoreTextView: TextView
    private lateinit var highScoreTextView: TextView

    private var score = 0
    private var gameOver = false
    private var maxScore = 0

    private lateinit var updateText: Runnable
    private lateinit var updateTime: Runnable
    private var handler = Handler(Looper.getMainLooper())
    private var timeHandler = Handler(Looper.getMainLooper())
    private var timeInterval = 3000L
    private var timePreferences = ""
    private var elapsedTime = 0L

    private var isCorrectInput = false

    //gesture detection
    private lateinit var gestureDetector: GestureDetector

    //sensor
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    private lateinit var sharedPreferences: SharedPreferences
    private var highScores = 0
    private var successInputCount = 0
    private var maxSuccessCountPerLevel = 4
    private var successPreferences = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        wordListTextView = findViewById(R.id.instructionsText)
        //highScoreTextView = findViewById(R.xml.root_preferences)
        scoreTextView = findViewById(R.id.scoreText)
        highScoreTextView = findViewById(R.id.highScoreText)


        //val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        //val highScores = sharedPreferences.getInt("highScore", 0)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val txtHighScore = sharedPreferences.getString("highScore", maxScore.toString())
        if (txtHighScore != null) {
            highScores = txtHighScore.toInt()
        }

        timePreferences = sharedPreferences.getString("gameTimeList", "gameTime_Values")!!
        timeInterval = timePreferences.toLong()

        successPreferences = sharedPreferences.getString("gameSuccesses", "gameSuccesses_Values")!!
        maxSuccessCountPerLevel = successPreferences.toInt()


        //gesture detection
        gestureDetector = GestureDetector(this, GestureListener())
        //sounds
        mediaPlayerDefeat = MediaPlayer.create(this, R.raw.defeat)
        mediaPlayerGame = MediaPlayer.create(this, R.raw.gameloop)
        mediaPlayerGoodInput = MediaPlayer.create(this, R.raw.goodinput)
        playbackParams = mediaPlayerGame.playbackParams
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        updateText = updateTextRunnable
        updateTime = updateTimeRunnable
        handler.postDelayed(updateText, 1)
        timeHandler.postDelayed(updateTime, 1)



        //edit maxScore in preferences


        //showWord()
    }

    private fun showWord(): String {
        mediaPlayerGame.start()
        val randomText = randomText.nextInt(words.size)
        wordToShow = words[randomText]
        return wordToShow


    }


    private val updateTextRunnable = object : Runnable {
        override fun run() {
            if (!gameOver) {

                wordListTextView.text = showWord()
                handler.postDelayed(updateText, timeInterval)



                if (elapsedTime * 1000 >= timeInterval) {
                    mediaPlayerGame.pause()
                    mediaPlayerGame.seekTo(0)
                    mediaPlayerDefeat.start()
                    elapsedTime = 0L
                    gameOver = true

                }

                if (successInputCount >= maxSuccessCountPerLevel && timeInterval >= 500L) {
                    timeInterval -= 500L
                    showToast("Increased difficulty, actual difficult: " + timeInterval + "seconds")
                    successInputCount = 0
                    if (mediaPlayerGame.isPlaying) {
                        changePlaybackSpeed(speedChange)
                    }
                }


            } else {
                if (score > maxScore) {
                    setMaxScore()
                    highScoreTextView.text = "New high score"

                }

                wordListTextView.text = gameOverText
                scoreTextView.text = "Score: " + score

            }

        }

    }

    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            elapsedTime++
            timeHandler.postDelayed(updateTime, 1000)
            println("Elapsed seconds: $elapsedTime")

            if (elapsedTime * 1000 >= timeInterval && isCorrectInput) {
                isCorrectInput = false
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateTextRunnable) // Remove the Runnable to stop updating when the activity is destroyed
        timeHandler.removeCallbacks(updateTimeRunnable)
        mediaPlayerGame.release()
        mediaPlayerDefeat.release()

    }

    //gesture detection
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    fun setMaxScore() {


        maxScore = score


        with(sharedPreferences.edit()) {
            putString("highScore", maxScore.toString()).apply()

            //showToast("New HighScore: $maxScore")
        }
        //sharedPreferences.edit().putInt(highScores, maxScore).apply()
        //Toast.makeText(this,"highScore: " + maxScore.toString(), Toast.LENGTH_LONG).show()


    }


    //gesture detection class
    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (!isCorrectInput) {
                if (wordToShow == "Tap") {
                    score++
                    //showToast("score: $score")
                    elapsedTime = 0
                    //wordListTextView.text = showWord()
                    isCorrectInput = true
                    successInputCount++
                    mediaPlayerGoodInput.start()
                }
            }
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            if (!isCorrectInput) {
                if (wordToShow == "LongPress") {
                    score++
                    //showToast("score: $score")
                    elapsedTime = 0
                    //wordListTextView.text = showWord()
                    isCorrectInput = true
                    successInputCount++
                    mediaPlayerGoodInput.start()
                }
            }
            super.onLongPress(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (!isCorrectInput) {
                if (wordToShow == "DoubleTap") {
                    score++
                    //showToast("score: $score")
                    elapsedTime = 0
                    //wordListTextView.text = showWord()
                    isCorrectInput = true
                    successInputCount++
                    mediaPlayerGoodInput.start()
                }
            }
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (!isCorrectInput) {
                if (wordToShow == "Fling") {
                    score++
                    //showToast("score: $score")
                    elapsedTime = 0
                    //wordListTextView.text = showWord()
                    isCorrectInput = true
                    successInputCount++
                    mediaPlayerGoodInput.start()
                }
            }
            return true
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun changePlaybackSpeed(speedChange: Float) {
        playbackParams?.speed = playbackParams!!.speed + speedChange
        mediaPlayerGame.playbackParams = playbackParams!!
    }

    private fun resetPlaybackSpeed() {
        playbackParams?.speed = 1.0f
        mediaPlayerGame.playbackParams = playbackParams!!
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
        mediaPlayerGame.release()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not in use in this example
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val acceleration = sqrt(x * x + y * y + z * z.toDouble()).toFloat()


            // You can adjust the acceleration threshold based on your needs
            val threshold = 25.0f

            if (!isCorrectInput && acceleration > threshold && wordToShow == "Shake") {
                score++
                showToast("score: $score")
                elapsedTime = 0
                isCorrectInput = true
                successInputCount++
                mediaPlayerGoodInput.start()
            }


        }
    }
}