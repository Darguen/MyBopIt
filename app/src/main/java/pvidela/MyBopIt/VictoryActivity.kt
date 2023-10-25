package pvidela.MyBopIt

import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class VictoryActivity : AppCompatActivity() {
    private lateinit var mediaPlayerVictory: MediaPlayer
    private lateinit var mediaPlayerDefeat: MediaPlayer
    private lateinit var mediaPlayerGame: MediaPlayer
    private var playbackParams: PlaybackParams? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_victory)

        mediaPlayerVictory = MediaPlayer.create(this, R.raw.success)
        mediaPlayerDefeat = MediaPlayer.create(this, R.raw.gameover)
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
                mediaPlayerGame.pause()
                mediaPlayerGame.seekTo(0)
            }
        }
    }

    /*private fun changePlaybackSpeed(speedChange: Float) {
        playbackParams?.setSpeed(playbackParams!!.speed + speedChange)
        mediaPlayer.playbackParams = playbackParams!!
    }

    private fun resetPlaybackSpeed() {
        playbackParams?.setSpeed(1.0f)
        mediaPlayer.playbackParams = playbackParams!!
    }*/

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
}