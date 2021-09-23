package com.payback.imagepicker.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.payback.imagepicker.domain.model.Image

@Dao
interface ImagePickerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImages(images:List<Image>)

    @Query("SELECT * FROM image_picker_table")
    fun getImages():List<Image>
}