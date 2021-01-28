package com.sagun.finalassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var etfirstname: EditText
    private   lateinit var etlastname: EditText
    private lateinit var etusername: EditText
    private  lateinit var etpassword: EditText
    private lateinit var btnsignup: Button
    private lateinit var btnlogin: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_signup)
            etfirstname = findViewById(R.id.etFirstname)
            etlastname = findViewById(R.id.etLastname)
            etusername = findViewById(R.id.etUsername)
            etpassword = findViewById(R.id.etPassword)
            btnsignup = findViewById(R.id.btnSignUp)
            btnlogin = findViewById(R.id.btnLogin)

            btnsignup.setOnClickListener {
                val fname = etfirstname.text.toString()
                val lname = etlastname.text.toString()
                val username = etusername.text.toString()
                val password = etpassword.text.toString()

                if (password != confirmPassword) {
                    etPassword.error = "Password does not match"
                    etPassword.requestFocus()
                    return@setOnClickListener
                } else {
                    val user = User(fname, lname, username, password)
                    CoroutineScope(Dispatchers.IO).launch {
                        //StudentDB(this@RegisterUserActivity).getUserDAO().registerUser(user)
                        StudentDB.getInstance(this@RegisterUserActivity).getUserDAO().registerUser(user)
                    }
                    Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show()
                }

            }

        }


    }
