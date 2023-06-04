package com.example.rsmtemp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rsmtemp.data.Alert
import com.example.rsmtemp.data.AlertDao
import com.example.rsmtemp.data.Server
import com.example.rsmtemp.data.ServerDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map

class ServerViewModel(private val serverDao: ServerDao, private val alertDao: AlertDao) : ViewModel() {
    // Server funkcije
    fun getAllServers() : Flow<List<Server>> = serverDao.getAll()

    fun getAllByOwnerId(user_id: Int) : Flow<List<Server>> = serverDao.getAllByOwnerId(user_id)

    fun getOneById(id: Int) : Flow<Server> = serverDao.getOneById(id)

    suspend fun upsertServer(server: Server) = serverDao.upsertServer(server)

//    suspend fun upsertServer(server: Server): Server {
//        serverDao.upsertServer(server)
//        //        alertDao.upsertAlert(Alert(id = null, server_id = createdServer.owner_id, server_name = createdServer.name, type = "success", description = "Server successfully created"))
////        alertDao.upsertAlert(Alert(id = null, server_id = 1, server_name = "createdServer.name", type = "success", description = "Server successfully created"))
//        return serverDao.getOneById(server.id!!).first()
//    }

    suspend fun deleteServer(server: Server)  = serverDao.deleteServer(server)

    private fun getLastServer(): Flow<Server> {
        return serverDao.getAll()
            .map { servers -> servers.last() }
    }

    suspend fun generateAlert(){
        val lastServer = getLastServer().first()
        alertDao.upsertAlert(Alert(id = null, server_id = lastServer.id!!, server_name = lastServer.name, type = "success", description = "Server successfully created"))
    }



    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory{
            initializer{
                val application = (this[APPLICATION_KEY] as ServerApplication)
                ServerViewModel(application.database.ServerDao(), application.database.AlertDao())

            }}
    }
}