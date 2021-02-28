package com.sagun.finalassignment.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

    data class Shoes(
        @PrimaryKey var _id:String? = null,
        var fname: String? = null,
        var lname: String? = null,
        var username: String? = null,
        var password: String? = null,
        val age: String?=null,
        val gender: String?=null,
        val address: String?=null,
        val photo: String?=null,

    )

