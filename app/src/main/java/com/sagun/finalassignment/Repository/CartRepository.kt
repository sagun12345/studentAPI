package com.sagun.finalassignment.Repository

import com.sagun.finalassignment.API.CartAPI
import com.sagun.finalassignment.API.MyApiRequest
import com.sagun.finalassignment.API.ServiceBuilder
import com.sagun.finalassignment.entity.Cart
import com.sagun.finalassignment.response.AddCartResponse
import com.sagun.finalassignment.response.DeleteCartResponse
import com.sagun.finalassignment.response.GetCartItemsResponse

class CartRepository: MyApiRequest() {

    private val cartAPI= ServiceBuilder.buildService(CartAPI::class.java)

    suspend fun addItemToCart(cart: Cart): AddCartResponse {
        return apiRequest {
            cartAPI.addItemToCart(
                    ServiceBuilder.token!!, cart
            )
        }
    }

    suspend fun getCartItems(): GetCartItemsResponse {
        return apiRequest {
            cartAPI.getCartItems(ServiceBuilder.token!!)
        }
    }

    suspend fun  deleteCartItem(id: String): DeleteCartResponse {
        return apiRequest {
            cartAPI.deleteCartItem(ServiceBuilder.token!!,id)
        }
    }
}