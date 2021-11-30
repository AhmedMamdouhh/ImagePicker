package com.payback.imagepicker.data.remote

import com.payback.imagepicker.BuildConfig
import com.payback.imagepicker.presentation.utils.Constants
import com.payback.imagepicker.domain.model.image.ImageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(BuildConfig.BASE_URL)
    fun getImageListBySearching(
        @Query(Constants.KEY)
        apiKey: String = BuildConfig.API_KEY,
        @Query(Constants.SEARCH_KEYWORD)
        searchKeyWord: String
    ): Single<ImageResponse>
}