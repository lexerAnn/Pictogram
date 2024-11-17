package com.pictogram.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.pictogram.databinding.FragmentImageDetailsBinding
import com.pictogram.presentation.authentication.events.NavEvent
import com.pictogram.presentation.home.vm.ImageDetailsVM
import com.pictogram.utils.extensions.popBackStackOrFinish
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageDetailsFragment : Fragment() {
    private lateinit var binding: FragmentImageDetailsBinding

    private val args: ImageDetailsFragmentArgs by navArgs()
    private val imageDetailsVM: ImageDetailsVM by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentImageDetailsBinding.inflate(inflater)
        binding.model = args.imageModel
        binding.vm = imageDetailsVM
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    fun setupNavigation(){
       viewLifecycleOwner.lifecycleScope.launch {
            imageDetailsVM.navigationEvent.collect { event ->
                if (event is NavEvent.NavigateBack){
                    popBackStackOrFinish()
                }
                }
            }
        }
    }