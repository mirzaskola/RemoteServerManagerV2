package com.example.rsmtemp.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert")
data class Alert(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @NonNull
    var server_id: Int,
    @NonNull
    var server_name: String,
    @NonNull
    var type: String,
    @NonNull
    var description: String,
)
