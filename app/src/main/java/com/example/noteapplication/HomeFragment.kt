package com.example.noteapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.*

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutButton.setOnClickListener{
            Toast.makeText(activity, "Success Logged Out!", Toast.LENGTH_LONG).show()
            userModel.logOutHandler()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }
}