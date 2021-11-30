package com.payback.imagepicker.di

import com.payback.imagepicker.data.db.ImagePickerDao
import com.payback.imagepicker.data.remote.Api
import com.payback.imagepicker.data.repository.ImagesRepository
import com.payback.imagepicker.data.repository.ImagesRepositoryGateway
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    fun providesImageRepository(api: Api, imagePickerDao: ImagePickerDao): ImagesRepositoryGateway =
        ImagesRepository(api, imagePickerDao)

}