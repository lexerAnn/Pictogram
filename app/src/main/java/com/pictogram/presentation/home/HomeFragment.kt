package com.pictogram.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.pictogram.generics.rv_generics.BaseRecyclerItemListener
import com.pictogram.data.remote.model.response.ImageResult
import com.pictogram.databinding.FragmentHomeBinding
import com.pictogram.presentation.home.adapters.ImageListPagingAdapter
import com.pictogram.presentation.home.vm.ImageViewModel
import com.pictogram.utils.extensions.navigateTo
import com.pictogram.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(), BaseRecyclerItemListener<ImageResult> {
    private lateinit var binding: FragmentHomeBinding

    private val imageVM: ImageViewModel by viewModels()
    private lateinit var imageViewBindingAdapter: ImageListPagingAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.vm =imageVM
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewBindingAdapter = ImageListPagingAdapter(this)

        imageVM.imageAdapter.set(imageViewBindingAdapter)
        binding.imageAdapter.adapter = imageViewBindingAdapter
        loadingImages()


        lifecycleScope.launch {
            imageVM.fetchImages()
                .distinctUntilChanged()
                .collectLatest {
                    imageViewBindingAdapter.submitData(it)
                }
        }
    }

    private fun loadingImages() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                imageViewBindingAdapter.loadStateFlow.collect {
                    binding.apply {
                        prependProgress.isVisible = it.source.refresh is LoadState.Loading
                        appendProgress.isVisible = it.source.append is LoadState.Loading
                    }
                }
            }

        }
    }

    override fun onItemClicked(view: View?, position: Int, modelItem: ImageResult) {
        navigateTo(HomeFragmentDirections.actionHomeFragmentToImageDetailsFragment(modelItem))
    }
}