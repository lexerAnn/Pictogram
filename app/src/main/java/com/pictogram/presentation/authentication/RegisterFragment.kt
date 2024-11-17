package com.pictogram.presentation.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pictogram.databinding.FragmentRegisterBinding
import com.pictogram.presentation.authentication.vm.RegisterViewModel
import com.pictogram.presentation.home.HomeActivity
import com.pictogram.utils.Constants
import com.pictogram.utils.SharedPrefFunctions
import com.pictogram.utils.extensions.observeLiveData
import com.pictogram.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val vm: RegisterViewModel by viewModels()

    @Inject
    lateinit var sharedPrefFunctions: SharedPrefFunctions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater)
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData(vm.registerResponse, onError = {msg-> toast(msg)}){
            sharedPrefFunctions.setPref(Constants.IS_LOGIN, true)

            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            toast("Account Created Successfully")
        }
    }
}