package com.sagun.finalassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.sagun.finalassignment.Repository.UserRepository
import com.sagun.finalassignment.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignupActivity : AppCompatActivity() {

    private lateinit var etfirstname: EditText
    private   lateinit var etlastname: EditText
    private lateinit var etusername: EditText
    private  lateinit var etpassword: EditText
    private lateinit var btnsignup: Button
    private lateinit var btnlogin: Button
    private lateinit var etConfirmPassword: EditText

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_signup)
            etfirstname = findViewById(R.id.etFirstname)
            etlastname = findViewById(R.id.etLastname)
            etusername = findViewById(R.id.etUsername)
            etpassword = findViewById(R.id.etPassword)
            btnsignup = findViewById(R.id.btnSignUp)
            btnlogin = findViewById(R.id.btnLogin)
            etConfirmPassword = findViewById(R.id.etConfirmPassword)


            btnlogin.setOnClickListener {
                startActivity(
                        Intent(
                                this@SignupActivity,
                                MainActivity::class.java
                        )
                )
            }

            btnsignup.setOnClickListener {

                val fname = etfirstname.text.toString()
                val lname = etlastname.text.toString()
                val username = etusername.text.toString()
                val password = etpassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()

                if (password != confirmPassword) {
                    etpassword.error = "Password does not match"
                    etpassword.requestFocus()
                    return@setOnClickListener
                } else {


                    var user =
                        User(fname = fname, lname = lname, username = username, password = password)
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val userRepository = UserRepository()
                            val response = userRepository.registerUser(user)
                            if (response.success==true){
                                withContext(Dispatchers.Main){
                                    Toast.makeText(this@SignupActivity,"User Registered",Toast.LENGTH_SHORT).show()
                                }

                            }
                        }catch (ex:Exception){
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                        this@SignupActivity,
                                        "Username cannot be duplicate", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                }
            }

        }


    }
