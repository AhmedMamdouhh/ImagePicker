package com.payback.imagepicker.data.remote

import com.payback.imagepicker.manager.utilities.Constants
import com.payback.imagepicker.domain.model.ImageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(Constants.BASE_URL)
    fun getImageListBySearching(
        @Query(Constants.KEY)
        apiKey: String = Constants.API_KEY,
        @Query(Constants.SEARCH_KEYWORD)
        searchKeyWord: String
    ): Single<ImageResponse>
}