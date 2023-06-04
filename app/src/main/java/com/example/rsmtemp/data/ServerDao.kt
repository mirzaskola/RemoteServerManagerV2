package com.example.rsmtemp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ServerDao {
    // Get ALL
    @Query("SELECT * FROM server")
    fun getAll() : Flow<List<Server>>

    // Get all by owner id
    @Query("SELECT * FROM server WHERE owner_id = :owner_id")
    fun getAllByOwnerId(owner_id: Int) : Flow<List<Server>>

    // Get one by server id
    @Query("SELECT * FROM server WHERE id = :id")
    fun getOneById(id: Int) : Flow<Server>

    @Upsert
    suspend fun upsertServer(server: Server)

    // Delete
    @Delete
    suspend fun deleteServer(server: Server)
}