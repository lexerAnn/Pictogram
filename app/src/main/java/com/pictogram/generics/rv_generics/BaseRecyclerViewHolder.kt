package com.pictogram.generics.rv_generics

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewHolder<Model>(
    itemView: View,
    private val interaction: BaseRecyclerItemListener<Model>? = null
) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(modelItem: Model)
}