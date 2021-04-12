package com.sagun.finalassignment.API
import com.sagun.finalassignment.response.UserProfileResponse
import com.sagun.finalassignment.entity.User
import com.sagun.finalassignment.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @POST("/register")
    suspend fun registerUser(
            @Body user: User
    ): Response<LoginResponse>

    //Login user
    @FormUrlEncoded
    @POST("/login")
    suspend fun checkUser(
            @Field("username") username: String,
            @Field("password") password: String

    ): Response<LoginResponse>

    @GET("/me")
    suspend fun getMe(
            @Header("Authorization") token: String
    ): Response<UserProfileResponse>
}
