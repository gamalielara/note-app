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
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private val userModel: LoginViewModel by activityViewModels()

    private var USERNAME: String = ""
    private var PASSWORD: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        USERNAME = ""
        PASSWORD = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (userModel.isLogedIn()) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener {
            if (USERNAME != "" && PASSWORD != "") {
                userModel.setUsername(USERNAME)
                userModel.setPassword(PASSWORD)
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