package com.approse.app.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.approse.app.R
import com.approse.app.data.repository.AuthRepository
import com.approse.app.databinding.ActivityMainBinding
import com.approse.app.databinding.FragmentLoginBinding
import com.approse.app.ui.auth.AuthActivity
import com.approse.app.ui.auth.AuthViewModel
import com.approse.app.ui.auth.LoginFragmentDirections

class LoginFragment : Fragment() {
    val parent: AuthActivity by lazy { activity as AuthActivity }
    val viewModel: AuthViewModel by lazy { AuthViewModel(AuthRepository(parent)) }
    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
    }

    private fun init() {
        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun observe() {
        viewModel.authLogin.observe(viewLifecycleOwner){
            if (it.isConsumed){
                Log.i("login", "isConsumed")
            } else if (!it.isSuccess){
                Toast.makeText(parent, it.message, Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(parent, it.message, Toast.LENGTH_SHORT).show()
                it.data?.let { it1 -> parent.onSucces(it1) }
            }
            it.isConsumed = true
        }
    }
}