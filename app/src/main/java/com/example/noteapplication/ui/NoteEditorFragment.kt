package com.example.noteapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.noteapplication.R
import com.example.noteapplication.data.NoteDummyData
import com.example.noteapplication.database.NotesDatabase
import com.example.noteapplication.viewmodel.NoteViewModel
import com.example.noteapplication.viewmodel.NoteViewModelFactory
import kotlinx.android.synthetic.main.fragment_note_editor.*

class NoteEditorFragment : Fragment() {
    private val ARG_NOTE_EDITOR = "noteId"
    private var noteId: Int? = null
    private lateinit var noteViewModel: NoteViewModel

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
//            val thisNote = noteViewModel.getNote(noteId!!) ?: return
        val thisNote = NoteDummyData.find { it.id == noteId }

        if (noteId != null) {
            if (thisNote == null) return;

            setOnNoteEditingMode(false)

            noteTitleText.setText(thisNote.title)
            noteBodyText.setText(thisNote.body)
        } else {
            setOnNoteEditingMode(true)
            noteTitleText.setText("Note Title")
            noteBodyText.setText("")
        }

        editNoteButton.setOnClickListener {
            setOnNoteEditingMode(true)
        }

        saveNoteButton.setOnClickListener {
            setOnNoteEditingMode(false)
        }

        backButton.setOnClickListener {
            onBackButtonPressed()
        }

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
}