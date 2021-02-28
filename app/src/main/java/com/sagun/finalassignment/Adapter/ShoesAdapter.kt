package com.sagun.finalassignment.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sagun.finalassignment.API.ServiceBuilder
import com.sagun.finalassignment.R
import com.sagun.finalassignment.Repository.ShoesRepository
import com.sagun.finalassignment.entity.Shoes
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoesAdapter (
    private val lstShoes:ArrayList<Shoes>,
    private val context: Context
): RecyclerView.Adapter<ShoesAdapter.ShoesViewHolder>() {
    class ShoesViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
        val imgProfile: CircleImageView = view.findViewById(R.id.imgProfile)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvSize: TextView = view.findViewById(R.id.tvSize)
        val tvCompany: TextView = view.findViewById(R.id.tvCompany)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_layout, parent, false)
        return ShoesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoesViewHolder, position: Int) {
        val shoes = lstShoes[position]
        holder.tvName.text = shoes.name
        holder.tvPrice.text = shoes.price
        holder.tvSize.text = shoes.size
        holder.tvAddress.text = shoes.address
        holder.tvCompany.text = shoes.company

        val imagePath = ServiceBuilder.loadImagePath() + shoes.photo
        if (!shoes.photo.equals("no-photo.jpg")) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.imgProfile)
        }
        //deleteUser
        holder.btnDelete.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete shoes")
            builder.setMessage("Are you sure you want to delete ${shoes.name} ??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val userRepository = ShoesRepository()
                        val response = userRepository.deleteShoes(shoes._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Student Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            withContext(Dispatchers.Main) {
                                lstShoes.remove(shoes)
                                notifyDataSetChanged()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                ex.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }


    }

    override fun getItemCount(): Int {
        return lstShoes.size
    }
}
