package com.sagun.finalassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.sagun.finalassignment.API.ServiceBuilder
import com.sagun.finalassignment.Repository.UserRepository
//import com.sagun.finalassignment.db.UserDB
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
    private lateinit var linearLayout: LinearLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        signup = findViewById(R.id.signup)
        username = findViewById(R.id.username)
        Password = findViewById(R.id.password)
        loginbtn = findViewById(R.id.loginbtn)
        linearLayout = findViewById(R.id.linearLayout)


        signup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }


        loginbtn.setOnClickListener {
            login()
        }
    }
    private fun login() {
        val username = username.text.toString()
        val password = Password.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.loginUser(username, password)
                if (response.success == true) {
                    ServiceBuilder.token = "Bearer " + response.token
                    startActivity(
                            Intent(
                                    this@MainActivity,
                                    dashboard::class.java
                            )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                                Snackbar.make(
                                        linearLayout,
                                        "Invalid credentials",
                                        Snackbar.LENGTH_LONG
                                )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
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


