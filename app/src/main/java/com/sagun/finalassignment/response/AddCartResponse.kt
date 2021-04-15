package com.sagun.finalassignment.response

import com.sagun.finalassignment.entity.Cart

data class AddCartResponse (
    val success: Boolean? = null,
    val data: Cart? = null
        )