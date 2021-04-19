package com.example.dreamlog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// DAO should be either interface or abstract class
@Dao
interface DreamDAO {

    @Query("SELECT * FROM dream_table ORDER BY id ASC")
    fun getAllDreams() : Flow<List<Dream>>

    // concurrent functions
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dream:Dream)


    @Query("UPDATE dream_table SET title=:title, content=:content, reflection=:reflection, emotion=:emotion WHERE id=:id")
    suspend fun update(id:Int, title:String, content:String, reflection:String, emotion:String)

    @Query("DELETE FROM dream_table WHERE id=:id")
    suspend fun delete (id:Int)

    @Query ("SELECT * FROM dream_table WHERE id=:id")
    fun getDream (id: Int) : Flow<Dream>
}