package com.example.notess

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//Entities - Table

@Entity(tableName = "note_table")
class Note(
    @ColumnInfo(name = "title")  val title: String,
    @ColumnInfo(name = "content") val content: String) {

    @PrimaryKey(autoGenerate = true) var id = 0

}