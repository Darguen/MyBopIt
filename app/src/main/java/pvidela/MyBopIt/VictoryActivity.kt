package pvidela.MyBopIt

import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class VictoryActivity : AppCompatActivity() {
    private lateinit var mediaPlayerVictory: MediaPlayer
    private lateinit var mediaPlayerDefeat: MediaPlayer
    private lateinit var mediaPlayerGame: MediaPlayer
    private var playbackParams: PlaybackParams? = null

    private val speedChange = 0.2f

    //gesture detection
    private lateinit var gestureDetector: GestureDetector
    private lateinit var textViewTouchEvent: TextView
    //private lateinit var touchEventHistory: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_victory)

        //gesture detection
        gestureDetector = GestureDetector(this, GestureListener())

        textViewTouchEvent = findViewById(R.id.textViewTouchEvent)

        mediaPlayerVictory = MediaPlayer.create(this, R.raw.success)
        mediaPlayerDefeat = MediaPlayer.create(this, R.raw.defeat)
        mediaPlayerGame = MediaPlayer.create(this, R.raw.gameloop)
        playbackParams = mediaPlayerVictory.playbackParams


        val buttonVictoryPlay = findViewById<Button>(R.id.buttonVictoryPlay)
        buttonVictoryPlay.setOnClickListener {
            if (!mediaPlayerVictory.isPlaying) {
                mediaPlayerVictory.start()
            }
        }

        val buttonVictoryStop = findViewById<Button>(R.id.buttonVictoryStop)
        buttonVictoryStop.setOnClickListener {
            if (mediaPlayerVictory.isPlaying) {
                mediaPlayerVictory.pause()
                mediaPlayerVictory.seekTo(0)
            }
        }

        val buttonDefeatPlay = findViewById<Button>(R.id.buttonDefeatPlay)
        buttonDefeatPlay.setOnClickListener {
            if (!mediaPlayerDefeat.isPlaying) {
                mediaPlayerDefeat.start()
            }
        }

        val buttonDefeatStop = findViewById<Button>(R.id.buttonDefeatStop)
        buttonDefeatStop.setOnClickListener {
            if (mediaPlayerDefeat.isPlaying) {
                mediaPlayerDefeat.pause()
                mediaPlayerDefeat.seekTo(0)
            }
        }

        val buttonGamePlay = findViewById<Button>(R.id.buttonGameLoopPlay)
        buttonGamePlay.setOnClickListener {
            if (!mediaPlayerGame.isPlaying) {
                mediaPlayerGame.start()
            }
        }

        val buttonGameStop = findViewById<Button>(R.id.buttonGameLoopStop)
        buttonGameStop.setOnClickListener {
            if (mediaPlayerGame.isPlaying) {
                resetPlaybackSpeed()
                mediaPlayerGame.pause()
                mediaPlayerGame.seekTo(0)

            }
        }

        val buttonGameSpeedUp = findViewById<Button>(R.id.buttonGameLoopSpeedUp)

        buttonGameSpeedUp.setOnClickListener {
            if (mediaPlayerGame.isPlaying) {
                changePlaybackSpeed(speedChange)
            }
        }
    }

    private fun changePlaybackSpeed(speedChange: Float) {
        playbackParams?.setSpeed(playbackParams!!.speed + speedChange)
        mediaPlayerGame.playbackParams = playbackParams!!
    }

    private fun resetPlaybackSpeed() {
        playbackParams?.setSpeed(1.0f)
        mediaPlayerGame.playbackParams = playbackParams!!
    }

    /*override fun onPause() {
        super.onPause()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }


    */override fun onDestroy() {
        super.onDestroy()
        mediaPlayerVictory.release()
        mediaPlayerDefeat.release()
        mediaPlayerGame.release()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }


    //gesture detection class
    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        /*override fun onDown(e: MotionEvent): Boolean {
            showToast("onDown")
            return true
        }*/

       /* override fun onSingleTapUp(e: MotionEvent): Boolean {
            showToast("onSingleTapUp")
            return true
        }*/

        override fun onLongPress(e: MotionEvent) {
            showToast("onLongPress")

        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            showToast("onDoubleTap")
            return super.onDoubleTap(e)
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            showToast("onFling")
            return true
        }

       /* override fun onScroll(
            e1: MotionEvent, e2: MotionEvent,
            distanceX: Float, distanceY: Float
        ): Boolean {
            showToast("onScroll")
            return true
        }*/
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}