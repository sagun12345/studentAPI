package com.sagun.finalassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sagun.finalassignment.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewUserActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user)
        recyclerView = findViewById(R.id.recyclerView)

        loadUsers()

    }

    private fun loadUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.
                if(response.success==true){
                    // Put all the student details in lstStudents
                    val lstStudents = response.data
                    withContext(Dispatchers.Main){
                        val adapter = StudentAdapter(lstStudents as ArrayList<Student>, this@ViewStudentActivity)
                        recyclerView.layoutManager = LinearLayoutManager(this@ViewStudentActivity)
                        recyclerView.adapter = adapter
                    }
                }
            }catch(ex : Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@ViewStudentActivity,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

