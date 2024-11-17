package com.pictogram.utils.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.pictogram.domain.DataState
import com.pictogram.presentation.components.LoadingDialog


fun Fragment.navigateTo(directions: NavDirections) = findNavController().navigate(directions)
fun Fragment.popBackStackOrFinish(): Boolean {
    return if (findNavController().popBackStack()) {
        true
    } else {
        requireActivity().finish()
        true
    }
}


fun <T> Fragment.observeLiveData(
    liveData: LiveData<DataState<T>>,
    enableProgressBar: Boolean = true,
    onError: ((String) -> Unit)? = null,
    onSuccess: (T) -> Unit
) {

    val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireActivity()) }

    liveData.observe(viewLifecycleOwner) { dataState ->
        when (dataState) {
            is DataState.Loading -> {
                loadingDialog.setLoading(enableProgressBar)
            }
            is DataState.Success -> {
                loadingDialog.setLoading(false)
                dataState.data?.let { onSuccess(it) }
            }
            is DataState.Error -> {
                loadingDialog.setLoading(false)
                dataState.exception.message?.let { errorMsg ->
                    onError?.invoke(errorMsg)
                }
            }
        }

    }
}

fun Fragment.toast(msg: String?) {
    Toast.makeText(requireActivity(), "$msg", Toast.LENGTH_SHORT).show()
}