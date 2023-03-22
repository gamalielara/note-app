package com.example.noteapplication.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapplication.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    private var USERNAME = ""
    private var EMAIL = ""
    private var PASSWORD = ""

    private lateinit var userDataPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = requireActivity()
        userDataPreferences = activity.getSharedPreferences("userData", Context.MODE_PRIVATE)

        USERNAME = ""
        EMAIL = ""
        PASSWORD = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val username = userDataPreferences.getString("username", null)
        val userPassword = userDataPreferences.getString("password", null)
        val isLoggedIn = username != null && userPassword != null

        if (isLoggedIn) {
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerButton.setOnClickListener {
            if (USERNAME != "" && EMAIL != "" && PASSWORD != "") {

                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
            } else {
                Toast.makeText(activity, "Fill the username and password first!", Toast.LENGTH_LONG)
                    .show()
            }
        }

        redirectLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        enterEmailRegister.doOnTextChanged { text, _, _, _ ->
            EMAIL = text.toString()
        }

        enterPasswordRegister.doOnTextChanged { text, _, _, _ ->
            PASSWORD = text.toString()
        }

        enterUsernameRegister.doOnTextChanged { text, _, _, _ ->
            USERNAME = text.toString()
        }
    }
}