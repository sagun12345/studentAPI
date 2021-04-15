package com.sagun.finalassignment

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat

class DashBoardActivity : AppCompatActivity() {
    private lateinit var home:ImageView
    private lateinit var logout:ImageView
    private lateinit var viewShoes:ImageView
    private lateinit var shoes: LinearLayout
    private lateinit var profile: LinearLayout
    private lateinit var cart: LinearLayout
    private val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        home=findViewById(R.id.home)
        logout=findViewById(R.id.imglogout)
        viewShoes=findViewById(R.id.viewShoes)
        shoes=findViewById(R.id.shoes)
        profile=findViewById(R.id.profile)
        cart=findViewById(R.id.cart)

        cart.setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, CartActivity::class.java))
        }

        logout.setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, MainActivity::class.java))
        }

        home.setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, AddShoes::class.java))
        }

        shoes.setOnClickListener{
            startActivity(Intent(this@DashBoardActivity,AllShoes::class.java))
        }

        profile.setOnClickListener{
            startActivity(Intent(this@DashBoardActivity,ProfileActivity::class.java))
        }

        viewShoes.setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, AllShoes::class.java))
        }
        if (!hasPermission()) {
            requestPermission()

//            home.setOnClickListener{
//                startActivity(Intent(this@DashBoardActivity,ViewShoesActivity::class.java))
//            }


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
