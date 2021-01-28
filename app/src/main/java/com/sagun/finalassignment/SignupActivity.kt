package com.sagun.finalassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

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




            try {this
                val intent = Intent(this, SignupActivity::class.java)
               startActivity(intent)
            } catch (ex: Exception) {
                Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
           }
        }
        }


    }
