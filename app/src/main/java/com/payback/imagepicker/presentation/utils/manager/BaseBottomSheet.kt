package com.payback.imagepicker.presentation.utils.manager

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.payback.imagepicker.R

abstract class BaseBottomSheet: BottomSheetDialogFragment() {

    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null


    override fun onStart() {
        super.onStart()
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        val dialog = dialog
        var bottomSheet: View? = null
        if (dialog != null) {
            bottomSheet = dialog.findViewById(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        val view = view
        val finalBottomSheet = bottomSheet
        view!!.post {
            val parent = view.parent as View
            val params =
                parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view.measuredHeight
            (finalBottomSheet!!.parent as View).setBackgroundColor(Color.TRANSPARENT)
        }
    }

    fun closeSheet(){
        try {
            bottomSheetBehavior!!.isHideable = true
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        } catch (ignored: NullPointerException) {
        }
    }
}