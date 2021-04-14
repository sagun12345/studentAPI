package com.sagun.finalassignment.API
import com.sagun.finalassignment.entity.Shoes

import com.sagun.finalassignment.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ShoesAPI {

    @POST("shoes")
    suspend fun addShoes(
            @Header("Authorization") token: String,
            @Body shoes: Shoes
    ): Response<AddShoesResponse>


    @GET("shoes/all")
    suspend fun getallShoes(
            @Header("Authorization") token: String
    ): Response<GetAllShoesResponse>

    @DELETE("shoes/{id}")
    suspend fun deleteShoes(
            @Header("Authorization") token: String,
            @Path("id") id:String
    ): Response<DeleteShoesResponse>


    @Multipart
    @PUT("shoes/{id}/photo")
    suspend fun uploadImage(
            @Header("Authorization") token: String,
            @Path("id") id: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>

}