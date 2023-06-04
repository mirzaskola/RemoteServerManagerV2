package com.example.rsmtemp.data

import androidx.annotation.NonNull
import androidx.room.PrimaryKey

data class UserState(
    val id: Int = 0,
    val username: String = "",
    val password: String = "",
    val phone: String = "",
    val email: String = ""
)
