package com.pictogram.presentation.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.pictogram.databinding.FragmentLoginBinding
import com.pictogram.presentation.authentication.events.NavEvent
import com.pictogram.presentation.authentication.vm.LoginViewModel
import com.pictogram.presentation.home.HomeActivity
import com.pictogram.utils.Constants
import com.pictogram.utils.SharedPrefFunctions
import com.pictogram.utils.extensions.navigateTo
import com.pictogram.utils.extensions.observeLiveData
import com.pictogram.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val vm: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var sharedPrefFunctions: SharedPrefFunctions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()

        observeLiveData(vm.loginResponse, onError = {msg-> toast(msg)}){
            sharedPrefFunctions.setPref(Constants.IS_LOGIN, true)
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            toast("Success")
        }

    }

    private fun setupNavigation() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.navigationEvent.collect { event ->
                if (event is NavEvent.NavigateToRegister ){
                    navigateTo(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                }
            }
        }
    }
}