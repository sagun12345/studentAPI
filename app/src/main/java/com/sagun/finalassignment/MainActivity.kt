package com.sagun.finalassignment

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast.makeText
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.sagun.finalassignment.API.ServiceBuilder
import com.sagun.finalassignment.Repository.UserRepository
//import com.sagun.finalassignment.db.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(),SensorEventListener {

    private lateinit var signup: TextView
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginbtn: Button
    private lateinit var layout: ConstraintLayout
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    signup = findViewById(R.id.signup)
    username = findViewById(R.id.username)
    password = findViewById(R.id.password)
    loginbtn = findViewById(R.id.loginbtn)
        layout = findViewById(R.id.layout)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager




    signup.setOnClickListener {
        startActivity(Intent(this, SignupActivity::class.java))
    }

        loginbtn.setOnClickListener {
            login()
        }


        if (!checkSensor())
            return
        else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null) {
            flag = false
        }
        return flag
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[0]



        if(values<=4)
            login()
        else
            Toast.makeText(this@MainActivity,"najik jau nah bro ",Toast.LENGTH_LONG).show()


    }

    private fun login() {

        val username = username.text.toString()
        val password = password.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.loginUser(username, password)
                if (response.success == true) {
                    ServiceBuilder.token = "Bearer " + response.token
                    startActivity(
                            Intent(
                                    this@MainActivity,
                                    DashBoardActivity::class.java
                            )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                                Snackbar.make(
                                      layout,
                                       "Invalid credentials",
                                     Snackbar.LENGTH_LONG
                             )
                       snack.setAction("OK", View.OnClickListener {
                         snack.dismiss()
                        })
                      snack.show()

                        makeText(this@MainActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                            this@MainActivity,
                            "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}


