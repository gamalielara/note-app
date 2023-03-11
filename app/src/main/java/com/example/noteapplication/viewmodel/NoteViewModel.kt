package com.example.noteapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.noteapplication.database.Note
import com.example.noteapplication.database.NoteDao

class NoteViewModel(
    private val database: NoteDao,
    application: Application
) : AndroidViewModel(application) {

    fun getNote(id: Int): Note? {
        return database.getNote(id)
    }

    fun getNotes(): LiveData<List<Note>> {
        return database.getNotes()
    }

    fun addNote(note: Note) {
        return database.addNote(note)
    }

    fun editNote(note: Note) {
        return database.updateNote(note)
    }

    fun deleteNote(noteId: Int){
        return database.deleteNote(noteId)
    }
}