package com.example.dreamlog

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Dream::class], version = 1, exportSchema = false)
public abstract class DreamRoomDatabase : RoomDatabase() { // has to be abstract
    // connect with DAO
    abstract fun dreamDAO() : DreamDAO // getter

    companion object{

        @Volatile // singleton
        private var INSTANCE : DreamRoomDatabase? = null
        // write a method to get the database

        fun getDatabase(context : Context) : DreamRoomDatabase {
            // if our instance is not null
            // can return it

            // if it is null, create the database
            return INSTANCE ?: synchronized(this){
                // creating the database and return
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DreamRoomDatabase::class.java,
                    "dream_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}