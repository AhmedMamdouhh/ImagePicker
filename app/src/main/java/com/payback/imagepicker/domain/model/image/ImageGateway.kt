package com.payback.imagepicker.domain.model.image

import io.reactivex.Single

interface ImageGateway {
    fun requestImages(searchKeyWord: String): Single<ImageResponse>
    fun saveImages(images:List<Image>)
    fun loadImages(): List<Image>
}