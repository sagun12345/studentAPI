package com.sagun.finalassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sagun.finalassignment.Adapter.ShoesAdapter
import com.sagun.finalassignment.Repository.ShoesRepository
import com.sagun.finalassignment.entity.Shoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllShoes : AppCompatActivity() {
    private lateinit var recyclerview_shoes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_shoes)

        recyclerview_shoes = findViewById(R.id.recyclerview_shoes)

        loadShoes()
    }

    private fun loadShoes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val shoesRepository = ShoesRepository()
                val response = shoesRepository.getAllShoes()
                if(response.success==true){
                    // Put all the shoes details in lstShoes
                    val lstShoes = response.data
                    withContext(Dispatchers.Main){
                        val adapter = ShoesAdapter(lstShoes  as ArrayList<Shoes>, this@AllShoes)
                        recyclerview_shoes.layoutManager = LinearLayoutManager(this@AllShoes)
                        recyclerview_shoes.adapter = adapter
                    }
                }
            }catch(ex : Exception){
//                withContext(Dispatchers.Main){
//                    Toast.makeText(this@ViewShoesActivity,
//                            "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }
}