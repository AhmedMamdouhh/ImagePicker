package com.payback.imagepicker.presentation.ui.image_picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payback.imagepicker.databinding.ItemImagePickerBinding
import com.payback.imagepicker.domain.model.Image

class ImagePickerAdapter(
    private val imagePickerList: ArrayList<Image>,
    private val imagePickerViewModel: ImagePickerViewModel
) : RecyclerView.Adapter<ImagePickerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePickerViewHolder =
        ImagePickerViewHolder(
            ItemImagePickerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), imagePickerViewModel
        )

    override fun onBindViewHolder(holder: ImagePickerViewHolder, position: Int) {
        holder.bind(imagePickerList[position])
    }

    override fun getItemCount(): Int {
        return imagePickerList.size
    }


}
