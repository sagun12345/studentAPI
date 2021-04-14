package com.sagun.finalassignment.Repository

import com.sagun.finalassignment.API.MyApiRequest
import com.sagun.finalassignment.API.ServiceBuilder
import com.sagun.finalassignment.API.ShoesAPI
import com.sagun.finalassignment.entity.Shoes

import com.sagun.finalassignment.response.*
import okhttp3.MultipartBody

class ShoesRepository :
    MyApiRequest(){
    private val shoesAPI = ServiceBuilder.buildService(ShoesAPI::class.java)

    //Add shoes
    suspend fun addShoes(shoes: Shoes):AddShoesResponse {
        return apiRequest {
            shoesAPI.addShoes  (
                    ServiceBuilder.token!!, shoes

            )
        }
    }


    suspend fun getAllShoes(): GetAllShoesResponse {
        return apiRequest {
            shoesAPI.getallShoes(ServiceBuilder.token!!)
        }
    }
    suspend fun deleteShoes(id:String): DeleteShoesResponse {
        return apiRequest {
            shoesAPI.deleteShoes(ServiceBuilder.token!!,id)
        }

    }
    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            shoesAPI.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }
}