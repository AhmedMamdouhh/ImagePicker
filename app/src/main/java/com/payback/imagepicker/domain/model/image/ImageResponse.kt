package com.payback.imagepicker.domain.model.image

data class ImageResponse(
    val hits: List<Image>,
    val total: Int,
    val totalHits: Int
)