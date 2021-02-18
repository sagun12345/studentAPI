package com.kiran.student.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kiran.student.R

class DashboardActivity : AppCompatActivity() {


    private lateinit var btnAddStudent: Button
    private lateinit var btnViewStudent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        btnAddStudent = findViewById(R.id.btnAddStudent)
        btnViewStudent = findViewById(R.id.btnViewStudent)


        btnAddStudent.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }
        btnViewStudent.setOnClickListener {
            startActivity(Intent(this, ViewStudentActivity::class.java))
        }
    }
}