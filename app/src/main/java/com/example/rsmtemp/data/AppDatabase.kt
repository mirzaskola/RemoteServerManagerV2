package com.example.rsmtemp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Server::class, Alert::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ServerDao() : ServerDao
    abstract fun AlertDao() : AlertDao
    abstract fun UserDao() : UserDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context : Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder( context, AppDatabase::class.java, "app_database")
//                    .createFromAsset("database/rsmdb.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ INSTANCE = it}
            }
        }
    }
}
