package com.example.rsmtemp.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "server")
data class Server(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @NonNull
    var name: String,
    @NonNull
    var type: String,
    @NonNull
    var owner_id: Int,
    @NonNull
    var cpu: String,
    @NonNull
    var ram: String,
    @NonNull
    var ram_amount: String,
    @NonNull
    var storage: String,
    @NonNull
    var storage_amount: String,
    @NonNull
    var gpu: String,
    @NonNull
    var gpu_vram_amount: String,
    @NonNull
    var os: String,
    @NonNull
    var status: Boolean,
    @NonNull
    var firewall: Boolean,
    @NonNull
    var ip: String,
    @NonNull
    var dns: String

)


