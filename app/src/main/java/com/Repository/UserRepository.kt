package com.Repository

import com.API.MyAPiRequest
import com.API.ServiceBuilder
import com.API.UserAPI
import com.kiran.student.entity.User
import com.kiran.student.response.LoginResponse

class UserRepository :
MyAPiRequest() {

    private val userAPI = ServiceBuilder.buildService(UserAPI::class.java)

    //REgister USer
    suspend fun registerUser(user: User): LoginResponse {
        return apiRequest {
            userAPI.registerUser(user)
        }
    }

        suspend fun loginUser(username: String, password: String): LoginResponse {
            return apiRequest {
                userAPI.checkUser(username, password)

            }
        }
    }
