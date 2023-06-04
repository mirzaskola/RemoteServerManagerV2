package com.example.rsmtemp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rsmtemp.data.Alert
import com.example.rsmtemp.data.AlertDao
import com.example.rsmtemp.data.ServerDao
import kotlinx.coroutines.flow.Flow

class AlertViewModel(private val alertDao: AlertDao, private val serverDao: ServerDao) : ViewModel() {
    // Alert funkcije
    fun getAllAlerts() : Flow<List<Alert>> = alertDao.getAll()
    fun getAllAlertsByServerId(server_id: Int): Flow<List<Alert>> = alertDao.getAllByServerId(server_id)
    suspend fun upsertAlert(alert: Alert) = alertDao.upsertAlert(alert)
    suspend fun deleteAlert(alert: Alert) = alertDao.deleteAlert(alert)


    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory{
            initializer{
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ServerApplication)
                AlertViewModel(application.database.AlertDao(), application.database.ServerDao())

            }}
    }
}