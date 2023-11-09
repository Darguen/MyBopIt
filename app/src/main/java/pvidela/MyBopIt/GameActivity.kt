package pvidela.MyBopIt

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.preference.PreferenceManager
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import java.util.Timer
import kotlin.math.sqrt
import kotlin.random.Random

class GameActivity : AppCompatActivity(), SensorEventListener {

    private var randomText = Random
    private lateinit var wordListTextView: TextView
    private var words: Array<String> = arrayOf("Tap", "DoubleTap", "Fling", "Shake", "LongPress")
    private var wordToShow: String = ""
    private lateinit var mediaPlayerDefeat: MediaPlayer



    private var score = 0
    private var gameOver = false
    private var maxScore = 0

    private lateinit var updateText: Runnable
    private lateinit var updateTime: Runnable
    private var handler = Handler(Looper.getMainLooper())
    private var timeHandler = Handler(Looper.getMainLooper())
    private var timeInterval = 3000L
    private  var elapsedTime = 0L
    private var threshold = 10.0f

    //gesture detection
    private lateinit var gestureDetector: GestureDetector
    //sensor
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        wordListTextView = findViewById(R.id.instructionsText)







        //gesture detection
        gestureDetector = GestureDetector(this, GestureListener())
        //sounds
        mediaPlayerDefeat = MediaPlayer.create(this, R.raw.defeat)

        updateText = updateTextRunnable
        updateTime = updateTimeRunnable
        handler.postDelayed(updateText, timeInterval)
        timeHandler.postDelayed(updateTime, 1000)

        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)






        //showWord()
    }

    private fun showWord() : String?{
        val randomText = randomText.nextInt(words.size)
        wordToShow = words[randomText]
        return wordToShow


    }



    private val updateTextRunnable = object : Runnable {
        override fun run() {
            if (!gameOver) {

                wordListTextView.text = showWord()
                handler.postDelayed(updateText, timeInterval)

                if (elapsedTime*1000 >= timeInterval) {
                    mediaPlayerDefeat.start()
                    elapsedTime = 0L
                    gameOver = true

                }
            }
            else{
                setMaxScore()
            }

        }

    }

    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            elapsedTime++
            timeHandler.postDelayed(updateTime, 1000)
            println("Elapsed seconds: $elapsedTime")


            }
        }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateTextRunnable) // Remove the Runnable to stop updating when the activity is destroyed
        timeHandler.removeCallbacks(updateTimeRunnable)

    }

    //gesture detection
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    fun setMaxScore(){

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val highScores = sharedPreferences.getString("highScore", "0")
        maxScore = score
        sharedPreferences.edit().putInt(highScores ,maxScore)
        sharedPreferences.edit().commit()


    }


    //gesture detection class
    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if(wordToShow == "Tap"){
                score++
                showToast("score: " + score)
                elapsedTime = 0
                wordListTextView.text = showWord()
            }
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            if(wordToShow == "LongPress"){
                score++
                showToast("score: " + score)
                elapsedTime = 0
                wordListTextView.text = showWord()
            }
            super.onLongPress(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            if(wordToShow == "DoubleTap"){
                score++
                showToast("score: " + score)
                elapsedTime = 0
                wordListTextView.text = showWord()
            }
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if(wordToShow == "Fling"){
                score++
                showToast("score: " + score)
                elapsedTime = 0
                wordListTextView.text = showWord()
            }
            return true
        }


    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
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


            if(wordToShow == "Shake"){
                if(acceleration > threshold) {
                    score++
                    showToast("score: " + score)
                    elapsedTime = 0
                }
            }




        }
    }
}