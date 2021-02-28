package com.sagun.finalassignment.API
import com.sagun.finalassignment.entity.Shoes

import com.sagun.finalassignment.response.*
import retrofit2.Response
import retrofit2.http.*

interface ShoesAPI {

    @POST("auth/shoes")
    suspend fun AddShoes(
            @Body shoes: Shoes
    ): Response<LoginResponse>


    @GET("Shoes/")
    suspend fun getallShoes(
            @Header("Authorization") token: String
    ): Response<GetAllShoesResponse>

    @DELETE("Shoes/{id}")
    suspend fun deleteShoes(
            @Header("Authorization") token: String,
            @Path("id") id: String
    ): Response<DeleteShoesResponse>

}