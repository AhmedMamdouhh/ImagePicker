package com.payback.imagepicker.presentation.response.error

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.payback.imagepicker.R
import com.payback.imagepicker.databinding.LayoutErrorBinding
import com.payback.imagepicker.presentation.utils.Constants


class ErrorSheet : BottomSheetDialogFragment()  {

    private lateinit var errorBinding: LayoutErrorBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        errorBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_error, container, false)
        getBundleMessage()
        errorBinding.lifecycleOwner = this

        Handler(Looper.getMainLooper()).postDelayed({
            dismiss()
        }, Constants.DELAY_BIG.toLong())

        return errorBinding.root
    }

    private fun getBundleMessage() {
        if (arguments?.containsKey(Constants.MESSAGE) == true) {
            errorBinding.tvMessage.text = requireArguments().getString(Constants.MESSAGE)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }



}

