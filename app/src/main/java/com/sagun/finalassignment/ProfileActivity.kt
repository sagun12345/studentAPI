package com.sagun.finalassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.sagun.finalassignment.API.ServiceBuilder
import com.sagun.finalassignment.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var etFirstname: EditText
    private   lateinit var etLastname: EditText
    private lateinit var etUsername: EditText
    private lateinit var btnupdate: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        etFirstname = findViewById(R.id.etFirstname)
        etLastname = findViewById(R.id.etLastname)
        etUsername = findViewById(R.id.etUsername)
        btnupdate = findViewById(R.id.btnupdate)

        loadProfile()
    }

    private fun loadProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepo = UserRepository()
                val response = userRepo.getMe()
                if (response.success == true){
//                    val imagePath = ServiceBuilder.loadImagePath() + response.data?.photo
                    withContext(Dispatchers.Main){


                        etFirstname.setText(response.data?.fname)
                        etLastname.setText(response.data?.lname)
                        etUsername.setText(response.data?.username)

                    }
                }
            } catch (ex: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(this@ProfileActivity,
//                            "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }
}