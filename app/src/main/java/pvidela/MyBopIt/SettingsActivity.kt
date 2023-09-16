package pvidela.MyBopIt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<Button>(R.id.backButtonSettings)
        buttonBack.setOnClickListener {
            val intentAbout = Intent(this, MainActivity::class.java)
            startActivity(intentAbout)
        }
    }
}