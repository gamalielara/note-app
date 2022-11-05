package com.example.noteapplication

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapplication.database.NoteDao

class NoteViewModelFactory(
    private val data: NoteDao,
    private val application: Application
):ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun<T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)) return NoteViewModel(data, application) as T

        throw IllegalArgumentException("Unknown ViewModelClass")
    }

}