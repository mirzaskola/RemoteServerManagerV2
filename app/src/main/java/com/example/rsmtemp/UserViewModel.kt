package com.example.rsmtemp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rsmtemp.data.User
import com.example.rsmtemp.data.UserDao
import com.example.rsmtemp.data.UserState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class UserViewModel(private val userDao: UserDao) : ViewModel(){

    // User funkcije
    fun getUserById(id: Int) : Flow<User> = userDao.getById(id)
    fun getUserByEmail(email: String) : Flow<User> = userDao.getByEmail(email)
    suspend fun upsertUser(user: User) = userDao.upsertUser(user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory{
            initializer{
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ServerApplication)
                UserViewModel(application.database.UserDao())

            }}
    }
}