package com.payback.imagepicker.domain.use_case

import com.payback.imagepicker.data.repository.ImagePickerRepository
import com.payback.imagepicker.domain.model.Image
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImageListUseCase @Inject constructor(
    private val imagePickerRepository: ImagePickerRepository,
) {

    private lateinit var result: ArrayList<Image>

    fun execute(
        keyWord: String,
        successConsumer: Consumer<ArrayList<Image>>,
        errorNoConnectionConsumer: Consumer<ArrayList<Image>>
    ): Disposable {
        return imagePickerRepository.getImageListBySearching(keyWord)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->

                result = response.hits as ArrayList<Image>
                imagePickerRepository.insertImagesOffline(result)
            }
            .subscribe({
                successConsumer.accept(result)
            }, {
                result= imagePickerRepository.getImageListOffline() as ArrayList<Image>
                errorNoConnectionConsumer.accept(result)
            })
    }
}