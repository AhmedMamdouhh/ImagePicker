package com.payback.imagepicker.domain.use_case

import com.payback.imagepicker.data.repository.ImagesRepositoryGateway
import com.payback.imagepicker.domain.model.image.Image
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepositoryGateway
) {

    private lateinit var result: ArrayList<Image>

    fun execute(
        keyWord: String,
        successConsumer: Consumer<ArrayList<Image>>,
        errorNoConnectionConsumer: Consumer<ArrayList<Image>>
    ): Disposable {
        return imagesRepository.requestImages(keyWord)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                result = response.hits as ArrayList<Image>
                imagesRepository.saveImages(result)
            }
            .subscribe({
                successConsumer.accept(result)
            }, {
                result= imagesRepository.loadImages() as ArrayList<Image>
                errorNoConnectionConsumer.accept(result)
            })
    }
}