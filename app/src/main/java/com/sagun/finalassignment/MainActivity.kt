package com.sagun.finalassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    private lateinit var signup: TextView
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginbtn: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    signup = findViewById(R.id.signup)
    username = findViewById(R.id.username)
    password = findViewById(R.id.password)
    loginbtn = findViewById(R.id.loginbtn)


    signup.setOnClickListener {
        startActivity(Intent(this, SignupActivity::class.java))
    }

        loginbtn.setOnClickListener {
            login()
        }


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
                                    dashboard::class.java
                            )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
//                        val snack =
//                                Snackbar.make(
//                                        line,
//                                        "Invalid credentials",
//                                        Snackbar.LENGTH_LONG
//                                )
//                        snack.setAction("OK", View.OnClickListener {
//                            snack.dismiss()
//                        })
//                        snack.show()

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


