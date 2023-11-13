package pvidela.MyBopIt

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.sqrt

class SensorDetectionActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private lateinit var sensorListTextView: TextView
    private var accelerometer: Sensor? = null
    private lateinit var accelerometerText: TextView
    private lateinit var addThreshold: Button
    private lateinit var reduceThreshold: Button
    private  var threshold = 10.0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_detection)


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        //val aSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorListTextView = findViewById(R.id.sensorListTextView)
        accelerometerText = findViewById(R.id.accelerometer)
        addThreshold = findViewById(R.id.addThreshold)
        reduceThreshold = findViewById(R.id.reduceThreshold)

        addThreshold.setOnClickListener{

            threshold++

        }

        reduceThreshold.setOnClickListener{
            threshold--
        }




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

            accelerometerText.text = ("x:" + x + " y: " + y + " z: " + z)

            // You can adjust the acceleration threshold based on your needs


            if(acceleration > threshold){
                Log.i("ValidacionOnSensorChange", acceleration.toString() + " > " + threshold.toString())
                showToast("Shaked")
            }



        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}