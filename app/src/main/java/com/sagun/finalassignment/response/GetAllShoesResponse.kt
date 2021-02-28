package com.sagun.finalassignment.response

import com.sagun.finalassignment.entity.Shoes

data class GetAllShoesResponse (
        val success :Boolean?=null,
        val Count :Int?=null,
        val data :MutableList<Shoes>?=null
)
