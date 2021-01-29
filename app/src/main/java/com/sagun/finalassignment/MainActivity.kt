package com.sagun.finalassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.sagun.finalassignment.db.UserDB
import com.sagun.finalassignment.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var signup: TextView
    private lateinit var username: EditText
    private lateinit var Password: EditText
    private lateinit var loginbtn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        signup = findViewById(R.id.signup)
        username = findViewById(R.id.username)
        Password = findViewById(R.id.password)
        loginbtn = findViewById(R.id.loginbtn)

        signup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }


        loginbtn.setOnClickListener {
            saveSharedPref()

            val username = username.text.toString()
            val password = Password.text.toString()
            var user: User?= null
            CoroutineScope(Dispatchers.IO).launch {
                user = UserDB
                        .getInstance(this@MainActivity)
                        .getUserDAO()
                        .checkUser(username, password)
                if (user == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Invalid credentials", Toast.LENGTH_SHORT)
                                .show()
                    }
                } else {
                    startActivity(
                            Intent(
                                    this@MainActivity,
                                    dashboard::class.java
                            )
                    )


                }






//
            }
        }
    }
    private fun saveSharedPref() {
        val username = username.text.toString()
        val password = Password.text.toString()
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
        Toast.makeText(
                this@MainActivity,
                "Username and password saved",
                Toast.LENGTH_SHORT
        ).show()
    }

    }


