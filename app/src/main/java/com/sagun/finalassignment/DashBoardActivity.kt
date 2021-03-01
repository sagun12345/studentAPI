package com.sagun.finalassignment

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.app.ActivityCompat

class DashBoardActivity : AppCompatActivity() {
    private lateinit var home:ImageView
    private val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        home=findViewById(R.id.home)
        if (!hasPermission()) {
            requestPermission()

            home.setOnClickListener{
                startActivity(Intent(this,ViewShoesActivity::class.java))
            }


        }
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission

    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                permissions, 1
        )
    }
}
