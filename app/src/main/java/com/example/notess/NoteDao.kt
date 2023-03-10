package com.example.notess

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(note: Note)

    @Delete
     fun delete(note: Note)

     @Update
     fun update(note: Note)

    @Query("Select * from note_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

}