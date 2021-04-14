package com.sagun.finalassignment.API
import com.sagun.finalassignment.response.UserProfileResponse
import com.sagun.finalassignment.entity.User
import com.sagun.finalassignment.response.ImageResponse
import com.sagun.finalassignment.response.LoginResponse
import com.sagun.finalassignment.response.UpdateUserResponse
import okhttp3.MultipartBody
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

    @PUT("/update/user/{id}")
    suspend fun updateUser(
            @Path("id") id: String,
            @Body user:User
    ): Response<UpdateUserResponse>

    @Multipart
    @PUT("/user/{id}/photo")
    suspend fun userImageUpload(
            @Path("id") id: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>
}
