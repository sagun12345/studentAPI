package com.sagun.finalassignment.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sagun.finalassignment.API.ServiceBuilder
import com.sagun.finalassignment.R
import com.sagun.finalassignment.Repository.CartRepository
import com.sagun.finalassignment.entity.Cart
import com.sagun.finalassignment.entity.Shoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoesAdapter (
    private val lstShoes:ArrayList<Shoes>,
    private val context: Context
): RecyclerView.Adapter<ShoesAdapter.ShoesViewHolder>() {
    class ShoesViewHolder (view: View): RecyclerView.ViewHolder(view) {
//        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
        val imgProfile: ImageView = view.findViewById(R.id.imgProfile)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvSize: TextView = view.findViewById(R.id.tvSize)
        val tvCompany: TextView = view.findViewById(R.id.tvCompany)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val addtocart: ImageButton = view.findViewById(R.id.addtocart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_layout, parent, false)
        return ShoesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoesViewHolder, position: Int) {
        val item = lstShoes[position]
        holder.tvName.text = item.name
        holder.tvPrice.text = item.price
        holder.tvSize.text = item.size
        holder.tvAddress.text = item.address
        holder.tvCompany.text = item.company

        val imagePath = ServiceBuilder.loadImagePath() + item.photo
        if (!item.photo.equals("no-photo.jpg")) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.imgProfile)
        }

        holder.addtocart.setOnClickListener {
            val name = item.name
            val price = item.price
            val pic = item.photo

            val carts = Cart(itemName = name,itemPrice = price,photo = pic)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val cartRepo = CartRepository()
                    val response = cartRepo.addItemToCart(carts)
                    if(response.success == true){

                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "$name Added to Cart", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (ex: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            context,
//                            ex.toString(), Toast.LENGTH_LONG
//                        ).show()
//                    }
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return lstShoes.size
    }
}
