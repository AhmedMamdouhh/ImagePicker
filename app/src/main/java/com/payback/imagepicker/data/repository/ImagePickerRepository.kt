package com.payback.imagepicker.data.repository

import com.payback.imagepicker.data.db.ImagePickerDao
import com.payback.imagepicker.data.remote.Api
import com.payback.imagepicker.domain.model.Image
import com.payback.imagepicker.domain.model.ImageGateway
import javax.inject.Inject

class ImagePickerRepository @Inject constructor(
    private val api: Api,
    private val imagePickerDao: ImagePickerDao
) : ImageGateway {


    //Remote:
    override fun requestImages(searchKeyWord: String) =
        api.getImageListBySearching(searchKeyWord = searchKeyWord)

    //Database:
    override fun saveImages(images: List<Image>) = imagePickerDao.insertImages(images)
    override fun loadImages() = imagePickerDao.getImages()

}