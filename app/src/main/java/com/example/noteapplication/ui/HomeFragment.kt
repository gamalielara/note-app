package com.example.noteapplication.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplication.R
import com.example.noteapplication.data.NoteAdapter
import com.example.noteapplication.database.Note
import com.example.noteapplication.database.NotesDatabase
import com.example.noteapplication.utils.SwipeGesture
import com.example.noteapplication.viewmodel.NoteViewModel
import com.example.noteapplication.viewmodel.NoteViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), LifecycleObserver {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notesData: LiveData<List<Note>>
    private lateinit var userDataPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = requireActivity()
        userDataPreferences = activity.getSharedPreferences("userData", Context.MODE_PRIVATE)

        val username = userDataPreferences.getString("username", null)
        val userPassword = userDataPreferences.getString("password", null)

        val isLoggedIn = username != null && userPassword != null


        Log.e(
            "USER PREF",
            "${username.toString()}, pass: ${userPassword.toString()}, login? ${isLoggedIn}"
        )

        if (!isLoggedIn) {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        } else {
            Toast.makeText(
                activity,
                "Hello, ${username}! Welcome to NoteApp created by Ara Gamaliel!!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val data = NotesDatabase.getInstance(application).noteDatabaseDao
        val noteViewModelFactory = NoteViewModelFactory(data, application)
        noteViewModel = ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesData = noteViewModel.getNotes()

        addNoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_noteEditorFragment, null)
        }

        logoutButton.setOnClickListener {
            val userDataPreferencesEditor = userDataPreferences.edit()

            userDataPreferencesEditor.apply {
                remove("username")
                remove("password")
                apply()
            }

            Toast.makeText(activity, "Success Logged Out!", Toast.LENGTH_LONG).show()
//            userModel.logOutHandler()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        notesData.observe(viewLifecycleOwner, Observer { note ->
            val noteAdapter = NoteAdapter(note)
            notes.adapter = noteAdapter
            notes.layoutManager = LinearLayoutManager(activity)

            val notesSwipeGesture = object : SwipeGesture() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    note[position].id?.let { note -> onDeleteNote(note) }
                    Toast.makeText(activity, "Note ${note[position].title} is successfully deleted!", Toast.LENGTH_LONG).show()
                    super.onSwiped(viewHolder, direction)
                }
            }

            val itemTouchHelper = ItemTouchHelper(notesSwipeGesture)
            itemTouchHelper.attachToRecyclerView(notes)

            if (note.isEmpty()) {
                noNotesIllustration.setImageResource(R.drawable.no_note)
                noNotesSection.visibility = View.VISIBLE
            } else {
                noNotesSection.visibility = View.GONE
            }
        })
    }

    override fun onPause() {
        super.onPause()
        lifecycle.removeObserver(this)
    }

    override fun onResume() {
        super.onResume()
        lifecycle.addObserver(this)
    }

    private fun onDeleteNote(noteId: Int) {
        noteViewModel.deleteNote(noteId)
    }
}