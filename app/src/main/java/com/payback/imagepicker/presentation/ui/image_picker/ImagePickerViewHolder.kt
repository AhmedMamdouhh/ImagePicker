package com.payback.imagepicker.presentation.ui.image_picker

import androidx.recyclerview.widget.RecyclerView
import com.payback.imagepicker.databinding.ItemImagePickerBinding
import com.payback.imagepicker.domain.model.Image

class ImagePickerViewHolder(
    private val imagePickerBinding: ItemImagePickerBinding,
    private val imagePickerViewModel: ImagePickerViewModel
) : RecyclerView.ViewHolder(imagePickerBinding.root) {


    fun bind(imagePickerObject: Image) {
        imagePickerBinding.imagePickerObject = imagePickerObject
        imagePickerBinding.imagePickerListener = imagePickerViewModel
    }
}