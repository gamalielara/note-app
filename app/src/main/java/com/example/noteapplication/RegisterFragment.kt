package com.example.noteapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    private var USERNAME = ""
    private var EMAIL = ""
    private var PASSWORD = ""

    private val userModel: LoginViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        USERNAME = ""
        EMAIL = ""
        PASSWORD = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (userModel.isLogedIn()) {
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerButton.setOnClickListener {
            if (USERNAME != "" && EMAIL != "" && PASSWORD != "") {
                userModel.setUsername(USERNAME)
                userModel.setPassword(PASSWORD)
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