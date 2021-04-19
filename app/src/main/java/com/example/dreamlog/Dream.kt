package com.example.dreamlog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dream_table")
class Dream(@ColumnInfo(name="title") var title:String,
            @ColumnInfo(name="content") var content:String,
            @ColumnInfo(name="reflection") var reflection:String,
            @ColumnInfo(name="emotion") var emotion:String){

    @PrimaryKey (autoGenerate = true) @ColumnInfo(name="id") var id:Int = 0

}
