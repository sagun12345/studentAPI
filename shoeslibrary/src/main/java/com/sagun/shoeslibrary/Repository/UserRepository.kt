package com.sagun.finalassignment.Repository

import com.sagun.finalassignment.API.MyApiRequest
import com.sagun.finalassignment.API.ServiceBuilder
import com.sagun.finalassignment.entity.User
import com.sagun.finalassignment.response.LoginResponse
import com.sagun.finalassignment.API.UserAPI
import com.sagun.finalassignment.response.ImageResponse
import com.sagun.finalassignment.response.UpdateUserResponse
import com.sagun.finalassignment.response.UserProfileResponse
import okhttp3.MultipartBody

class UserRepository :
    MyApiRequest(){
    private val UserAPI = ServiceBuilder.buildService(UserAPI::class.java)

    //Register User

    suspend fun registerUser(user: User) : LoginResponse {
        return apiRequest {
            UserAPI.registerUser(user)
        }
    }
    //Login User
    suspend fun loginUser(username : String,password : String) : LoginResponse{
        return apiRequest {
            UserAPI.checkUser(username,password)
        }
    }

    suspend fun getMe(): UserProfileResponse {
        return apiRequest {
            UserAPI.getMe(ServiceBuilder.token!!)
        }
    }

    suspend fun updateUser(id:String, user: User ): UpdateUserResponse {
        return apiRequest {
            UserAPI.updateUser(id, user)
        }
    }

    suspend fun userImageUpload(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            UserAPI.userImageUpload(id, body)
        }
    }
}