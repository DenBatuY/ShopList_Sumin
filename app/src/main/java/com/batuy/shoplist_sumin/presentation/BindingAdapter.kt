package com.batuy.shoplist_sumin.presentation

import androidx.databinding.BindingAdapter
import com.batuy.shoplist_sumin.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInputName")
fun errorInputName(textInputLayout: TextInputLayout,isError:Boolean){
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_name)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun errorInputCount(textInputLayout: TextInputLayout,isError:Boolean){
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
    textInputLayout.error = message
}