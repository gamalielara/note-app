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
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private var USERNAME: String = ""
    private var PASSWORD: String = ""

    private lateinit var userDataPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = requireActivity()
        userDataPreferences = activity.getSharedPreferences("userData", Context.MODE_PRIVATE)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val username = userDataPreferences.getString("username", null)
        val userPassword = userDataPreferences.getString("password", null)
        val isLoggedIn = username != null && userPassword != null

        if (isLoggedIn) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        userDataPreferences = activity.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val userDataEditor = userDataPreferences.edit()

        loginButton.setOnClickListener {
            if (USERNAME != "" && PASSWORD != "") {
                userDataEditor.apply {
                    putString("username", USERNAME)
                    putString("password", PASSWORD)
                    apply()
                }

                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(activity, "Fill the username and password first!", Toast.LENGTH_LONG)
                    .show()
            }
        }

        redirectRegisterButton.setOnClickListener {
            findNavController().navigate((R.id.action_loginFragment_to_registerFragment))
        }

        enterUsernameLogin.doOnTextChanged { text, _, _, _ ->
            USERNAME = text.toString()
        }

        enterPasswordLogin.doOnTextChanged { text, _, _, _ ->
            PASSWORD = text.toString()
        }
    }
}