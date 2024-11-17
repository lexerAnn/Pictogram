package com.pictogram.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pictogram.generics.rv_generics.BaseRecyclerItemListener
import com.pictogram.data.remote.model.response.ImageResult
import com.pictogram.databinding.ListItemImageBinding
import com.pictogram.generics.rv_generics.BasePagingRecyclerAdapter
import com.pictogram.presentation.home.viewHolder.ImageListVH

class ImageListPagingAdapter(
    private val itemListener: BaseRecyclerItemListener<ImageResult>? = null
) : BasePagingRecyclerAdapter<ImageResult, ImageListVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListVH {
        return ImageListVH(
            ListItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemListener
        )
    }
}