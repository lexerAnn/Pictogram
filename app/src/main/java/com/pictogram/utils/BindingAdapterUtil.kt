package com.pictogram.utils

import android.graphics.Typeface
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.pictogram.R
import com.pictogram.generics.rv_generics.BasePagingRecyclerAdapter

object BindingAdapterUtil {

    @JvmStatic
    @BindingAdapter("boldText")
    fun setText(view: MaterialTextView, text: Any?) {
        if (text is String && text.contains(" ")) {
            val parts = text.split(" ")
            val spannableString = SpannableString(text)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                parts[0].length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            view.text = spannableString
        } else {
            view.text = "$text"
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.outline_image_24)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("recyclerAdapter")
    fun setRecyclerAdapter(recyclerView: RecyclerView, adapter: BasePagingRecyclerAdapter<*, *>?) {
        recyclerView.layoutManager =
            LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }


    @JvmStatic
    @BindingAdapter("onFocusChanged")
    fun setOnFocusChangedListener(view: View, listener: OnFocusChanged?) {
        view.onFocusChangeListener = if (listener != null) {
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                listener.onFocusChanged(v, hasFocus)
            }
        } else null
    }

    @JvmStatic
    @BindingAdapter("onTouch")
    fun setOnTouchListener(view: View, listener: OnTouch) {
        view.setOnTouchListener { view: View?, motionEvent: MotionEvent? ->
            listener.onTouch(view, motionEvent)
        }
    }


    @JvmStatic
    @BindingAdapter("android:afterTextChanged")
    fun setListener(view: TextView, after: AfterTextChanged?) {
        setListener(view, null, null, after)
    }

    @JvmStatic
    @BindingAdapter("android:afterTextChanged")
    fun setListener(view: MaterialTextView, after: AfterTextChanged?) {
        setListener(view, null, null, after)
    }
    @JvmStatic
    @BindingAdapter("android:afterTextChanged")
    fun setListener(view: TextInputEditText, after: AfterTextChanged?) {
        setListener(view, null, null, after)
    }

    @JvmStatic
    @BindingAdapter("android:beforeTextChanged", "android:onTextChanged", "android:afterTextChanged")
    fun setListener(view: TextView, before: BeforeTextChanged?, on: OnTextChanged?, after: AfterTextChanged?) {
        val newValue: TextWatcher? = if (before == null && after == null && on == null) {
            null
        } else {
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    before?.beforeTextChanged(s, start, count, after)
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    on?.onTextChanged(s, start, before, count)
                }

                override fun afterTextChanged(editable: Editable) {
                    after?.afterTextChanged(view, editable)
                }
            }
        }
        val oldValue = ListenerUtil.trackListener(view, newValue, R.id.textWatcher)
        if (oldValue != null) {
            view.removeTextChangedListener(oldValue)
        }
        if (newValue != null) {
            view.addTextChangedListener(newValue)
        }
    }

    interface AfterTextChanged {
        fun afterTextChanged(view: View?, s: Editable?)
    }

    interface BeforeTextChanged {
        fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
    }

    interface OnTextChanged {
        fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
    }

    interface OnFocusChanged {
        fun onFocusChanged(view: View?, hasFocus: Boolean)
    }

    interface OnTouch {
        fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean
    }
}