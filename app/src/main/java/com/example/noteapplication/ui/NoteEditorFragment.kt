package com.example.noteapplication.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.noteapplication.R
import com.example.noteapplication.database.Note
import com.example.noteapplication.database.NotesDatabase
import com.example.noteapplication.viewmodel.NoteViewModel
import com.example.noteapplication.viewmodel.NoteViewModelFactory
import kotlinx.android.synthetic.main.fragment_note_editor.*

class NoteEditorFragment : Fragment() {
    private val ARG_NOTE_EDITOR = "noteId"
    private var noteId: Int? = null
    private lateinit var noteViewModel: NoteViewModel

    private var note = Note(
        title = "",
        body = "",
        createdTime = 0,
        lastEdited = 0,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getInt(ARG_NOTE_EDITOR)
        }

        val application = requireNotNull(this.activity).application
        val data = NotesDatabase.getInstance(application).noteDatabaseDao
        val noteViewModelFactory = NoteViewModelFactory(data, application)

        noteViewModel = ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_note_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNoteUI()
    }

    private fun onBackButtonPressed() {
        val activity = requireActivity() as MainActivity
        activity.onBackPressed()
    }

    private fun setupNoteUI() {
        var isNewNote = true
//        val thisNote = NoteDummyData.find { it.id == noteId }

        if (noteId != null) {
            val thisNote = noteViewModel.getNote(noteId!!)
            if (thisNote == null) return;

            isNewNote = false
            setOnNoteEditingMode(isNewNote)

            noteTitleText.setText(thisNote.title)
            noteBodyText.setText(thisNote.body)

            note = thisNote
        } else {
            isNewNote = true
            setOnNoteEditingMode(isNewNote)
            noteTitleText.setText("Note Title")
            noteBodyText.setText("")
        }

        editNoteButton.setOnClickListener {
            setOnNoteEditingMode(true)
        }

        saveNoteButton.setOnClickListener {
            saveNote(isNewNote)
            setOnNoteEditingMode(false)
        }

        backButton.setOnClickListener {
            onBackButtonPressed()
        }

        noteTitleText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        noteBodyText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note.body = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setOnNoteEditingMode(editingMode: Boolean) {
        if (editingMode) {
            editNoteButton.visibility = View.GONE
            saveNoteButton.visibility = View.VISIBLE
            noteTitleText.isEnabled = true
            noteBodyText.isEnabled = true
        } else {
            editNoteButton.visibility = View.VISIBLE
            saveNoteButton.visibility = View.GONE
            noteTitleText.isEnabled = false
            noteBodyText.isEnabled = false
        }
    }

    private fun saveNote(isNewNote: Boolean) {
        note.lastEdited = System.currentTimeMillis()
        if (isNewNote) {
            note.createdTime = System.currentTimeMillis()
            noteViewModel.addNote(note)
            Log.e("HELLO ARA GAMALIEL", note.toString())
        } else {
            noteViewModel.editNote(note)
        }
    }
}