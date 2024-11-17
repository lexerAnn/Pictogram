package com.pictogram.presentation.home.viewHolder


import com.pictogram.generics.rv_generics.BaseRecyclerItemListener
import com.pictogram.data.remote.model.response.ImageResult
import com.pictogram.databinding.ListItemImageBinding
import com.pictogram.generics.rv_generics.BaseRecyclerViewHolder

class ImageListVH(
    private val binding: ListItemImageBinding,
    private val itemListener: BaseRecyclerItemListener<ImageResult>? = null
) : BaseRecyclerViewHolder<ImageResult>(binding.root) {

    override fun onBind(modelItem: ImageResult) = with(binding.root) {

        setOnClickListener {
            itemListener?.onItemClicked(it, adapterPosition, modelItem)
        }
        binding.model = modelItem
        binding.lifecycleOwner = binding.lifecycleOwner
        binding.executePendingBindings()
    }

}