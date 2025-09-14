package com.app.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup

abstract class BaseDialog(context: Context) : Dialog(context) {

    private var onPositiveClick: (() -> Unit)? = null
    private var onNegativeClick: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun setPositiveButtonClickListenerApplication(listener: () -> Unit) {
        onPositiveClick = listener
    }

    fun setNegativeButtonClickListenerApplication(listener: () -> Unit) {
        onNegativeClick = listener
    }

    protected fun dismissDialogApplicationApplicationApplicationApplication() {
        dismiss()
    }

    protected fun handlePositiveButtonClick() {
        onPositiveClick?.invoke()
        dismissDialogApplicationApplicationApplicationApplication()
    }

    protected open fun handleNegativeButtonClick() {
        onNegativeClick?.invoke()
        dismissDialogApplicationApplicationApplicationApplication()
    }
}