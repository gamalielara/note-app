package com.example.noteapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapplication.R
import com.example.noteapplication.data.NoteAdapter
import com.example.noteapplication.database.Note
import com.example.noteapplication.database.NotesDatabase
import com.example.noteapplication.viewmodel.LoginViewModel
import com.example.noteapplication.viewmodel.NoteViewModel
import com.example.noteapplication.viewmodel.NoteViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), LifecycleObserver {
    private val userModel: LoginViewModel by activityViewModels()
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notesData: LiveData<List<Note>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!userModel.isLogedIn()) {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        val application = requireNotNull(this.activity).application
        val data = NotesDatabase.getInstance(application).noteDatabaseDao
        val noteViewModelFactory = NoteViewModelFactory(data, application)
        noteViewModel = ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(
            activity,
            "Hello,${userModel.getUsername()}! Welcome to NoteApp created by Ara Gamaliel!!",
            Toast.LENGTH_LONG
        ).show()

        notesData = noteViewModel.getNotes()

        addNoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_noteEditorFragment, null)
        }

        logoutButton.setOnClickListener {
            Toast.makeText(activity, "Success Logged Out!", Toast.LENGTH_LONG).show()
            userModel.logOutHandler()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        notesData.observe(requireActivity(), Observer { note ->
            val noteAdapter = NoteAdapter(note)
            notes.adapter = noteAdapter
            notes.layoutManager = LinearLayoutManager(activity)

            if(note.isEmpty()){
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


}