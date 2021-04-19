package com.example.dreamlog

import android.app.Application

class DreamApplication : Application() {
    // create 1 instance of database
    // 1 instance of repository

    // lazy -> the value gets computes or executes only upon first access
    val database by lazy {
        DreamRoomDatabase.getDatabase(this)
    }
    val repository by lazy {
        DreamRepository(database.dreamDAO())
    }

}