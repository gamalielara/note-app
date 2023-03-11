package com.example.noteapplication.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.noteapplication.R
import com.example.noteapplication.constants.NoteColors
import com.example.noteapplication.database.Note
import com.example.noteapplication.database.NotesDatabase
import com.example.noteapplication.viewmodel.NoteViewModel
import com.example.noteapplication.viewmodel.NoteViewModelFactory
import kotlinx.android.synthetic.main.fragment_note_editor.*

class NoteEditorFragment : Fragment() {
    private val ARG_NOTE_EDITOR = "noteId"
    private var noteId: Int? = null
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var note: Note
    private var isNewNote: Boolean = true

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

    private fun decideIsNewNote(): Boolean {
        return noteId == null || noteViewModel.getNote(noteId!!) == null
    }

    private fun setupNoteUI() {
        isNewNote = decideIsNewNote()

        if (isNewNote) {
            note = Note(
                title = "",
                body = "",
                createdTime = 0,
                lastEdited = 0,
                noteColor = NoteColors.BLUE
            )
            setOnNoteEditingMode(true)
            noteTitleText.setText("Note Title")
            noteBodyText.setText("")

        } else {
            note = noteViewModel.getNote(noteId!!) ?: note
            setOnNoteEditingMode(false)
            noteTitleText.setText(note.title)
            noteBodyText.setText(note.body)
        }

        setButtonsListeners()

        setColorNote()

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
            colorPickerGroup.visibility = View.VISIBLE
        } else {
            editNoteButton.visibility = View.VISIBLE
            saveNoteButton.visibility = View.GONE
            noteTitleText.isEnabled = false
            noteBodyText.isEnabled = false
            colorPickerGroup.visibility = View.GONE
        }
    }

    private fun saveNote(isNewNote: Boolean) {
        note.lastEdited = System.currentTimeMillis()

        if (note.noteColor == null) note.noteColor = NoteColors.BLUE

        if (isNewNote) {
            note.createdTime = System.currentTimeMillis()
            noteViewModel.addNote(note)
            Log.e("HELLO ARA GAMALIEL", note.toString())
            Toast.makeText(requireActivity(), "Note successfully created", Toast.LENGTH_SHORT)
                .show()
        } else {
            noteViewModel.editNote(note)
            Toast.makeText(requireActivity(), "Note successfully edited", Toast.LENGTH_SHORT).show()
        }
        Log.e("NOTE SAVED", note.toString())
    }

    private fun setColorNote() {
        when (note.noteColor) {
            NoteColors.BLUE -> blueColorButton.isChecked = true
            NoteColors.GREEN -> greenColorButton.isChecked = true
            NoteColors.MAGENTA -> magentaColorButton.isChecked = true
            NoteColors.PINK -> pinkColorButton.isChecked = true
            NoteColors.PURPLE -> purpleColorButton.isChecked = true
            NoteColors.YELLOW -> yellowColorButton.isChecked = true
        }
    }

    private fun setButtonsListeners() {
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

        blueColorButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                note.noteColor = NoteColors.BLUE
            }
        }

        greenColorButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                note.noteColor = NoteColors.GREEN
            }
            Log.e("NOTE GREEN ", note.noteColor.toString())
        }

        magentaColorButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                note.noteColor = NoteColors.MAGENTA
            }
        }

        pinkColorButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                note.noteColor = NoteColors.PINK
            }
        }

        purpleColorButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                note.noteColor = NoteColors.PURPLE
            }
        }

        yellowColorButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                note.noteColor = NoteColors.YELLOW
            }
        }
    }
}