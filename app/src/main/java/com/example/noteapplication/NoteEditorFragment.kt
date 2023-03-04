package com.example.noteapplication

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.noteapplication.data.NoteDummyData
import com.example.noteapplication.database.NotesDatabase
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

    private fun setupNoteUI() {
        if (noteId == null) return;
//            val thisNote = noteViewModel.getNote(noteId!!) ?: return
        val thisNote = NoteDummyData.find { it.id == noteId } ?: return
        Log.e("HELLO", thisNote.title)

        noteTitleText.text = thisNote.title
        noteBodyText.text = thisNote.body


        editNoteButton.setOnClickListener {
            noteTitleText.inputType = InputType.TYPE_CLASS_TEXT
            noteBodyText.inputType = InputType.TYPE_CLASS_TEXT
        }

    }
}