package pvidela.MyBopIt

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.math.sqrt

class SensorDetectionActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private lateinit var sensorListTextView: TextView
    private var accelerometer: Sensor? = null
    private lateinit var accelerometerText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_detection)


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        //val aSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorListTextView = findViewById(R.id.sensorListTextView)
        accelerometerText = findViewById(R.id.accelerometer)

        // Get the list of available sensors
        val sensorList = sensorManager!!.getSensorList(Sensor.TYPE_ALL)

        // Display the list of sensors
        val sensorInfoList = StringBuilder()
        for (sensor in sensorList) {
            sensorInfoList.append("${sensor.name} (${sensor.typeToString()})\n")
        }
        sensorListTextView.text = sensorInfoList.toString()

    }

    private fun Sensor.typeToString(): String {
        return when (this.type) {
            Sensor.TYPE_ACCELEROMETER -> "Accelerometer"
            Sensor.TYPE_GYROSCOPE -> "Gyroscope"
            Sensor.TYPE_PROXIMITY -> "Proximity"
            Sensor.TYPE_MAGNETIC_FIELD -> "Magnetometer (Compass)"
            Sensor.TYPE_LIGHT -> "Light"
            // Add more sensor types as needed
            else -> "Unknown Sensor"
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

            accelerometerText.text = ("x: " + x.toString() + "y: " + y.toString() + "z: " + z.toString())

            // You can adjust the acceleration threshold based on your needs
            val threshold = 10.0f


        }
    }

}