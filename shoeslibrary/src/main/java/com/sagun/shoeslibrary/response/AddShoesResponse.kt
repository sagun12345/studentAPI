package com.sagun.finalassignment.response

import com.sagun.finalassignment.entity.Shoes

data class AddShoesResponse(
        val  success :Boolean? = null,
        val  data : Shoes? = null
)