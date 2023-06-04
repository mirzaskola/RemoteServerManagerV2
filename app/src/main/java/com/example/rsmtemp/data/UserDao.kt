package com.example.rsmtemp.data

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // Get ALL
    @Query("SELECT * FROM user")
    fun getAll() : Flow<List<User>>

    // Get by id
    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: Int) : Flow<User>

    // Get by email
    @Query("SELECT * FROM user WHERE email = :email")
    fun getByEmail(email: String) : Flow<User>

    @Upsert
    suspend fun upsertUser(user: User)

    // Delete
    @Delete
    suspend fun deleteUser(user: User)


}