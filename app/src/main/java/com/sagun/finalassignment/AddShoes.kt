package com.sagun.finalassignment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sagun.finalassignment.Repository.ShoesRepository
import com.sagun.finalassignment.Repository.UserRepository
import com.sagun.finalassignment.entity.Shoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddShoes : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etSize: EditText
    private lateinit var etCompany: EditText
    private lateinit var etAddress: EditText
    private lateinit var btnSave: Button
    private lateinit var imgProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_shoes)

        etName = findViewById(R.id.etName)
        etPrice = findViewById(R.id.etPrice)
        etSize = findViewById(R.id.etSize)
        etCompany = findViewById(R.id.etCompany)
        etAddress = findViewById(R.id.etAddress)
        btnSave = findViewById(R.id.btnSave)
        imgProfile = findViewById(R.id.imgProfile)

        btnSave.setOnClickListener {
            saveShoes()

        }

        imgProfile.setOnClickListener {
            loadPopUpMenu()

        }

    }


    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(this, imgProfile)
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

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null



    private fun saveShoes() {
        val Name = etName.text.toString()
        val Price = etPrice.text.toString()
        val Size = etSize.text.toString()
        val Company = etCompany.text.toString()
        val address = etAddress.text.toString()


        val shoes = Shoes(name =Name, price=Price, size=Size ,company=Company, address=address)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val shoesRepository = ShoesRepository()
                val response = shoesRepository.addShoes(shoes)
                if(response.success == true){
                    if (imageUrl !=null) {
                        uploadImage(response.data!!._id!!)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                this@AddShoes,
                                "Added Shoes!!", Toast.LENGTH_SHORT
                        ).show()
                        showHighPriorityNotification()
                    }
                }
            } catch (ex: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                            this@AddShoes,ex.toString(), Toast.LENGTH_SHORT
//                    ).show()
//                }
            }
        }
    }

    private fun uploadImage(userId: String) {
        if (imageUrl != null) {
            val imageFile = File(imageUrl!!)
            val requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
            val body =
                    MultipartBody.Part.createFormData("file", imageFile.name, requestFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepo = UserRepository()
                    val response = userRepo.userImageUpload(userId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddShoes, "Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Log.d("My Error ", ex.localizedMessage)
//                        Toast.makeText(
//                                this@AddShoes,
//                                ex.localizedMessage,
//                                Toast.LENGTH_SHORT
//                        ).show()
//                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }

    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
    private fun showHighPriorityNotification() {

        val notificationManager = NotificationManagerCompat.from(this)

        val notificationChannels = NotificationChannels(this)
        notificationChannels.createNotificationChannels()

        val notification = NotificationCompat.Builder(this, notificationChannels.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_lock)
                .setContentTitle("High priority notification")
                .setContentText("This is my notification body")
                .setColor(Color.BLUE)
                .build()

        notificationManager.notify(1, notification)

    }
}