package com.sagun.shoesos

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.sagun.finalassignment.API.ServiceBuilder
import com.sagun.finalassignment.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : WearableActivity() {

    private lateinit var username:EditText
    private  lateinit var password:EditText
    private lateinit var login: Button
    private lateinit var linearLayout:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        linearLayout = findViewById(R.id.rootlayout)
        // Enables Always-on
        setAmbientEnabled()
        login.setOnClickListener {
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
                            this@LoginActivity,
                            MainActivity::class.java
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

                        Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}

