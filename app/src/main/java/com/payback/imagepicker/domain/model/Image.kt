package com.payback.imagepicker.domain.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.payback.imagepicker.BR
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "image_picker_table")
class Image : BaseObservable(),Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @get:Bindable
    var imageId: Int = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageId)
        }
    @IgnoredOnParcel
    @SerializedName("user")
    @get:Bindable
    var imageUserName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUserName)
        }

    @IgnoredOnParcel
    @SerializedName("webformatURL")
    @get:Bindable
    var imageSmall: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageSmall)
        }

    @IgnoredOnParcel
    @SerializedName("largeImageURL")
    @get:Bindable
    var imageLarge: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageLarge)
        }

    @IgnoredOnParcel
    @SerializedName("tags")
    @get:Bindable
    var imageTagsList: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageTagsList)
        }

    @IgnoredOnParcel
    @SerializedName("likes")
    @get:Bindable
    var imageLikes: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageLikes)
        }

    @IgnoredOnParcel
    @SerializedName("comments")
    @get:Bindable
    var imageComments: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageComments)
        }

    @IgnoredOnParcel
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