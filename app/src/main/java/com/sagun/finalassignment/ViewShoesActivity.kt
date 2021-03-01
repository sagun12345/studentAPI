package com.sagun.finalassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sagun.finalassignment.Adapter.ShoesAdapter
import com.sagun.finalassignment.Repository.ShoesRepository
import com.sagun.finalassignment.entity.Shoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewShoesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnADD: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_shoes)

        btnADD = findViewById(R.id.)
        recyclerView = findViewById(R.id.recyclerView)

        loadShoes()
    }

    private fun loadShoes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val shoesRepository = ShoesRepository()
                val response = shoesRepository.getAllShoes()
                if (response.success == true) {
                    // Put all the student details in lstStudents
                    val lstShoes = response.data
                    withContext(Dispatchers.Main) {
                        val adapter = ShoesAdapter(lstShoes as ArrayList<Shoes>, this@ViewShoesActivity)
                        recyclerView.layoutManager = LinearLayoutManager(this@ViewShoesActivity)
                        recyclerView.adapter = adapter
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ViewShoesActivity,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}