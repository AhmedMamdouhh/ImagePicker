package com.payback.imagepicker.domain.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import com.payback.imagepicker.BR

class Image : BaseObservable() {

    @SerializedName("id")
    @get:Bindable
    var imageId: Int = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageId)
        }

    @SerializedName("user")
    @get:Bindable
    var imageUserName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUserName)
        }

    @SerializedName("webformatURL")
    @get:Bindable
    var imageSmall: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageSmall)
        }

    @SerializedName("largeImageURL")
    @get:Bindable
    var imageLarge: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageLarge)
        }

    @SerializedName("tags")
    @get:Bindable
    var imageTagsList: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageTagsList)
        }

    @SerializedName("likes")
    @get:Bindable
    var imageLikes: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageLikes)
        }

    @SerializedName("comments")
    @get:Bindable
    var imageComments: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageComments)
        }

    @SerializedName("downloads")
    @get:Bindable
    var imageDownloads: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageDownloads)
        }
}

data class ImageResponse (
    val hits: List<Image>,
    val total: Int,
    val totalHits: Int
)