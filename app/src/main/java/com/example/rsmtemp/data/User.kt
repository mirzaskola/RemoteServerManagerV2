package com.example.rsmtemp.data


import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @NonNull
    var username: String,
    @NonNull
    var password: String,
    @NonNull
    var phone: String,
    @NonNull
    var email: String,
)

