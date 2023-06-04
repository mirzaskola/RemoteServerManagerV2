package com.example.rsmtemp

import android.app.Application
import com.example.rsmtemp.data.AppDatabase

class ServerApplication : Application() {
    val database : AppDatabase by lazy{ AppDatabase.getDatabase(this)}
}