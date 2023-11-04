package pvidela.MyBopIt

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))



        val buttonAbout = findViewById<Button>(R.id.backButton)
        val buttonPreferences = findViewById<Button>(R.id.PreferencesButton)
        //val buttonHistory = findViewById<Button>(R.id.HistoryButton)
        //val buttonInstructions = findViewById<Button>(R.id.InstructionsButton)
        val buttonVictory = findViewById<Button>(R.id.VictoryButton)
        val buttonSensors = findViewById<Button>(R.id.Sensors)
        val buttonGame = findViewById<Button>(R.id.gameButton)

        // Button click listeners
        buttonAbout.setOnClickListener {
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
        }
        buttonPreferences.setOnClickListener {
            val intentAbout = Intent(this, SettingsActivity::class.java)
            startActivity(intentAbout)
        }
        buttonVictory.setOnClickListener {
            val intentAbout = Intent(this, VictoryActivity::class.java)
            startActivity(intentAbout)
        }

        buttonSensors.setOnClickListener {
            val intentAbout = Intent(this, SensorDetectionActivity::class.java)
            startActivity(intentAbout)
        }

        buttonGame.setOnClickListener {
            val intentAbout = Intent(this, GameActivity::class.java)
            startActivity(intentAbout)
        }




    }
    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val intentPreferences = Intent(this, SettingsActivity::class.java)
                startActivity(intentPreferences)
                return true
            }
            R.id.action_about -> {
                val intentPreferences = Intent(this, AboutActivity::class.java)
                startActivity(intentPreferences)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }*/

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }


}