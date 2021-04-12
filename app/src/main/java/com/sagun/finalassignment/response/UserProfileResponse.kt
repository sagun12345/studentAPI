package com.sagun.finalassignment.response

import com.sagun.finalassignment.entity.User

data class UserProfileResponse (
        val success : Boolean?=null,
        val data : User?=null
)