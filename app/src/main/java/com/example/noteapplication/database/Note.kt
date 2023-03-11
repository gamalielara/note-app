package com.example.noteapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapplication.constants.NoteColors

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "last_edited")
    var lastEdited: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "created_time")
    var createdTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "note_color")
    var noteColor: String? = null,

    @ColumnInfo(name = "body")
    var body: String = "",

)