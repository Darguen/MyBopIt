package pvidela.MyBopIt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val buttonBack = findViewById<Button>(R.id.backButton)
        buttonBack.setOnClickListener {
            val intentAbout = Intent(this, MainActivity::class.java)
            startActivity(intentAbout)
        }
    }
}