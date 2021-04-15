package com.sagun.finalassignment.API

import com.sagun.finalassignment.entity.Cart
import com.sagun.finalassignment.response.AddCartResponse
import com.sagun.finalassignment.response.DeleteCartResponse
import com.sagun.finalassignment.response.GetCartItemsResponse
import retrofit2.Response
import retrofit2.http.*

interface CartAPI {

    @POST("/add/cart")
    suspend fun addItemToCart(
            @Header("Authorization") token: String,
            @Body cart: Cart
    ): Response<AddCartResponse>

    @GET("cart/all")
    suspend fun getCartItems(
            @Header("Authorization") token: String
    ): Response<GetCartItemsResponse>

    @DELETE("delete/{id}")
    suspend fun deleteCartItem(
            @Header ("Authorization") token: String,
            @Path("id") id: String
    ):Response<DeleteCartResponse>
}