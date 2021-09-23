package com.payback.imagepicker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.payback.imagepicker.domain.model.Image

@Database(
    entities = [Image::class],
    version = 1
)
abstract class ImagePickerDataBase :RoomDatabase(){
    abstract fun getImagePickerDao():ImagePickerDao
}