package com.payback.imagepicker.data.repository

import com.payback.imagepicker.data.db.ImagePickerDao
import com.payback.imagepicker.data.remote.Api
import com.payback.imagepicker.domain.model.Image
import javax.inject.Inject

class ImagePickerRepository @Inject constructor(
    private val api: Api,
    private val imagePickerDao: ImagePickerDao
) {

    //Remote:
    fun getImageListBySearching(searchKeyWord: String) =
        api.getImageListBySearching(searchKeyWord = searchKeyWord)


    //Offline:
    fun getImageListOffline() = imagePickerDao.getImages()
    fun insertImagesOffline(images:List<Image>) = imagePickerDao.insertImages(images)
}