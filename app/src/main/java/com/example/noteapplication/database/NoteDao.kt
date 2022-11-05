package com.example.noteapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao{
    @Query("SELECT * FROM notes_table")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id=:id")
    fun getNote(id:Int): Note?

    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Query("DELETE FROM notes_table WHERE id=:id")
    fun deleteNote(id:Int)

    @Query("DELETE FROM notes_table")
    fun clearAllNotes()
}