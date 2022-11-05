package com.example.noteapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.noteapplication.database.NoteDao

class NoteViewModel(
    val database: NoteDao,
    application: Application
) : AndroidViewModel(application) {}