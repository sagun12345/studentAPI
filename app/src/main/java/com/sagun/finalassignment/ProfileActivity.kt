package com.sagun.finalassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.sagun.finalassignment.API.ServiceBuilder
import com.sagun.finalassignment.API.ServiceBuilder.id
import com.sagun.finalassignment.Repository.UserRepository
import com.sagun.finalassignment.entity.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileActivity : AppCompatActivity() {

    private lateinit var etFirstname: EditText
    private   lateinit var etLastname: EditText
    private lateinit var etUsername: EditText
    private lateinit var btnupdate: Button
    private lateinit var profilepic: CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        etFirstname = findViewById(R.id.etFirstname)
        etLastname = findViewById(R.id.etLastname)
        etUsername = findViewById(R.id.etUsername)
        btnupdate = findViewById(R.id.btnupdate)
        profilepic = findViewById(R.id.profilepic)

        loadProfile()


        profilepic.setOnClickListener {
            loadPopup()
        }

        btnupdate.setOnClickListener {
            val fname = etFirstname.text.toString()
            val lname = etLastname.text.toString()
            val userName = etUsername.text.toString()

            val user =
                    User(fname = fname, lname = lname, username= userName)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepo = UserRepository()
                    val response = userRepo.updateUser(id.toString(),user)
                    if (response.success == true){
                        if (imageUrl != null){
                            uploadImage(response.data!!._id!!)
                        }
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@ProfileActivity,
                                    "Updated!!", Toast.LENGTH_SHORT).show()


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

    private fun uploadImage(userId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body =
                    MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepo = UserRepository()
                    val response = userRepo.userImageUpload(userId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ProfileActivity, "Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
//                    withContext(Dispatchers.Main) {
////                        Log.d("My Error ", ex.localizedMessage)
//                        Toast.makeText(
//                                this@ProfileActivity,
//                                ex.localizedMessage,
//                                Toast.LENGTH_SHORT
//                        ).show()
//                    }
                }
            }
        }
    }

    private fun loadPopup() {
        val popupMenu = PopupMenu(this, profilepic)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    private fun loadProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepo = UserRepository()
                val response = userRepo.getMe()
                if (response.success == true){
                    val imagePath = ServiceBuilder.loadImagePath() + response.data?.photo
                    withContext(Dispatchers.Main){
                        Glide.with(this@ProfileActivity)
                                .load(imagePath)
                                .fitCenter()
                                .into(profilepic)

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