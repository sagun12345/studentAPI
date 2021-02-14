package com.sagun.finalassignment.response

import com.sagun.finalassignment.entity.User

data class GetAllUserResponse (
        val success :Boolean?=null,
        val Count :Int?=null,
        val data :MutableList<User>?=null
)
