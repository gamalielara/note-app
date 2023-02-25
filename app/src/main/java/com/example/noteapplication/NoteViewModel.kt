package com.example.noteapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapplication.database.Note
import com.example.noteapplication.database.NoteDao
import kotlinx.coroutines.launch

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
}