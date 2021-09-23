package com.payback.imagepicker.presentation.ui.image_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.payback.imagepicker.R
import com.payback.imagepicker.databinding.DialogImageDetailsBinding
import com.payback.imagepicker.manager.base.BaseBottomSheet
import com.payback.imagepicker.manager.utilities.convertFromStringToList

class ImageDetailsDialog : BaseBottomSheet() {

    private lateinit var imageDetailsBinding: DialogImageDetailsBinding
    private val args:ImageDetailsDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        imageDetailsBinding =
            DialogImageDetailsBinding.inflate(inflater, container, false)
        imageDetailsBinding.imageObject = args.imageObject
        imageDetailsBinding.imageDetailsListener = this


        createChips()

        return imageDetailsBinding.root
    }

    fun onCloseClicked() {
        closeSheet()
    }


    private fun createChips() {
        convertFromStringToList(args.imageObject.imageTagsList).map {
            val chip = Chip(requireContext())
            chip.text = it
            chip.setChipBackgroundColorResource(R.color.colorLightGrey)
            chip.setTextAppearanceResource(R.style.ChipTextStyle)
            imageDetailsBinding.cgImageDetailsChips.addView(chip)
        }
    }
}