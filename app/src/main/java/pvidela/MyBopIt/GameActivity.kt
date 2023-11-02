package pvidela.MyBopIt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private var randomText = Random
    private lateinit var wordListTextView: TextView
    private var words: Array<String> = arrayOf("Tap", "DoubleTap", "Fling", "Shake")
    private lateinit var wordToShow: String

    private lateinit var updateText: Runnable
    private var handler = Handler(Looper.getMainLooper())
    private var timeInterval: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        wordListTextView = findViewById(R.id.instructionsText)

        updateText = updateTextRunnable





        //showWord()
    }

    private fun showWord(){
        val randomText = randomText.nextInt(words.size)
        wordToShow = words[randomText]
        wordListTextView.text = wordToShow

    }

    private val updateTextRunnable = object : Runnable{
        override fun run() {
            showWord()
            handler.postDelayed(updateText, timeInterval)
        }
    }
}