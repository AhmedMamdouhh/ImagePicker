package com.payback.imagepicker.data.repository

import com.payback.imagepicker.data.db.ImagePickerDao
import com.payback.imagepicker.data.remote.Api
import com.payback.imagepicker.domain.model.image.Image
import javax.inject.Inject

class ImagesRepository @Inject constructor(
    private val api: Api,
    private val imagePickerDao: ImagePickerDao
) : ImagesRepositoryGateway {


    //Remote:
    override fun requestImages(searchKeyWord: String) =
        api.getImageListBySearching(searchKeyWord = searchKeyWord)

    //Database:
    override fun saveImages(images: List<Image>) = imagePickerDao.insertImages(images)
    override fun loadImages() = imagePickerDao.getImages()

}