package com.pictogram.generics.rv_generics

import android.view.View

interface BaseRecyclerItemListener<Model> {
    fun onItemClicked(view: View?, position: Int, modelItem: Model)
}