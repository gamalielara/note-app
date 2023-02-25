package com.example.noteapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapplication.data.NoteAdapter
import com.example.noteapplication.data.NoteDummyData
import com.example.noteapplication.database.NoteDao
import com.example.noteapplication.database.NotesDatabase
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val userModel: LoginViewModel by activityViewModels()

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
        val noteViewModel = ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(activity, "Hello,${userModel.getUsername()}! Welcome to NoteApp created by Ara Gamaliel!!", Toast.LENGTH_LONG).show()


        logoutButton.setOnClickListener{
            Toast.makeText(activity, "Success Logged Out!", Toast.LENGTH_LONG).show()
            userModel.logOutHandler()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        val noteAdapter = NoteAdapter(NoteDummyData)
        notes.adapter = noteAdapter
        notes.layoutManager = LinearLayoutManager(activity )
    }
}