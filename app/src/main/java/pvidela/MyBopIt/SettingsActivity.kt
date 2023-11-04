package pvidela.MyBopIt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import pvidela.MyBopIt.GameActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var scoreTextView: TextView
    private val gameActivity = GameActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<Button>(R.id.backButtonSettings)
        buttonBack.setOnClickListener {
            val intentAbout = Intent(this, MainActivity::class.java)
            startActivity(intentAbout)
        }

        scoreTextView = findViewById(R.id.scoreNumber)
        //showMaxScore()



    }

    private fun showMaxScore(){
        scoreTextView.text = gameActivity.setMaxScore().toString()
    }

}