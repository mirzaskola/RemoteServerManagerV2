package com.example.rsmtemp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    // Get ALL
    @Query("SELECT * FROM alert")
    fun getAll() : Flow<List<Alert>>

    // Get by id
//    @Query("SELECT * FROM alert WHERE server_id = :server_id")
//    fun getAllWithServerId(server_id: Int) : Flow<List<Alert>>
    //

    // Get all alerts by server id
    @Query("SELECT * FROM alert WHERE server_id = :server_id")
    fun getAllByServerId(server_id: Int) : Flow<List<Alert>>

    @Upsert
    suspend fun upsertAlert(alert: Alert)

    // Delete
    @Delete
    suspend fun deleteAlert(alert: Alert)


}