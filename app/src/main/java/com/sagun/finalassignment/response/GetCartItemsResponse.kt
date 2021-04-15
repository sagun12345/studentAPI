package com.sagun.finalassignment.response

import com.sagun.finalassignment.entity.Cart


data class GetCartItemsResponse (
        val success: Boolean? = null,
        val count: Int? =null,
        val data: MutableList<Cart>? = null
)