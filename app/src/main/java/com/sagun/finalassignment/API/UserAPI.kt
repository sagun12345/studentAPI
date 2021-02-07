package com.sagun.finalassignment.API
import com.sagun.finalassignment.entity.User
import com.sagun.finalassignment.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {

    @POST("auth/register")
    suspend fun  registerUser(
        @Body user: User
    ): Response<LoginResponse>

    //Login user
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun checkUser(
        @Field("email") email:String,
        @Field("password") password:String


    ):Response<LoginResponse>}