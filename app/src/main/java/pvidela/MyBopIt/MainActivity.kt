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
    private var CHANNEL_ID = "MAINACTIVTY"
    private var NOTIFICATION_ID = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Background Audio",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Background Audio Playback")
            .setContentText("Your app is playing audio in the background.")
            .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your notification icon
            .setPriority(NotificationCompat.PRIORITY_LOW) // Adjust priority as needed
            .build()


        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)


        val buttonAbout = findViewById<Button>(R.id.backButton)
        val buttonPreferences = findViewById<Button>(R.id.PreferencesButton)
        //val buttonHistory = findViewById<Button>(R.id.HistoryButton)
        //val buttonInstructions = findViewById<Button>(R.id.InstructionsButton)
        val buttonVictory = findViewById<Button>(R.id.VictoryButton)
        val buttonSensors = findViewById<Button>(R.id.Sensors)

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
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancel(NOTIFICATION_ID)
    }


}