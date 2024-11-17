package com.pictogram.generics.rv_generics

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil


abstract class BasePagingRecyclerAdapter<Model : Any, VH : BaseRecyclerViewHolder<Model>>(
    private val itemListener: BaseRecyclerItemListener<Model>? = null,
) : PagingDataAdapter<Model, VH>(ItemCallback<Model>().diffCallback) {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let { item ->
            holder.onBind(item)
            itemListener?.let { listener ->
                holder.itemView.setOnClickListener {
                    listener.onItemClicked(holder.itemView, position, item)
                }
            }
        }
    }

    private class ItemCallback<T : Any> {
        val diffCallback = object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }
        }
    }
}
