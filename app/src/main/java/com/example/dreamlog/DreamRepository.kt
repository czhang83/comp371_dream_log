package com.example.dreamlog

import kotlinx.coroutines.flow.Flow

class DreamRepository (private val dreamDAO: DreamDAO){
    // Room does not run queries on main thread
    // required to run queries on a separate thread

    // store all the results into a list and make it into a public property
    // for each of the method in the DAO, write something to execute them in separate threads

    val allDreams:Flow<List<Dream>> = dreamDAO.getAllDreams()

    // suspend -> room runs all suspend functions/queries off the main thread
    // can call it and embed in a method that can be used later

    suspend fun insert (dream:Dream){
        dreamDAO.insert(dream)
    }


    suspend fun update(id:Int, title:String, content:String, reflection:String, emotion:String){
        dreamDAO.update(id, title, content, reflection, emotion)
    }

    suspend fun delete (id:Int){
        dreamDAO.delete(id)
    }

    fun getDream (id: Int) : Flow<Dream>{
        return dreamDAO.getDream(id)
    }

}