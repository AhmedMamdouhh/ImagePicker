package com.payback.imagepicker.presentation.ui.image_picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.payback.imagepicker.domain.model.image.Image
import com.payback.imagepicker.domain.use_case.ImagesUseCase
import com.payback.imagepicker.presentation.utils.manager.BaseViewModel
import com.payback.imagepicker.presentation.utils.manager.ResponseManager
import com.payback.imagepicker.presentation.utils.Constants
import com.payback.imagepicker.presentation.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor(
    private val imagesUseCase: ImagesUseCase,
    private val responseManager: ResponseManager
) : BaseViewModel() {

    private val _observeImageListData = MutableLiveData<Event<ArrayList<Image>>>()
    private val _observeImageClicked = MutableLiveData<Event<Image>>()
    private val _observeNotFound = MutableLiveData<Event<Boolean>>()
    private val _observeOfflineMode = MutableLiveData<Event<Boolean>>()
    private val _observeOnlineMode = MutableLiveData<Event<Boolean>>()

    private lateinit var photosList: ArrayList<Image>


    init {
        requestImages(Constants.KEY_WORD)
    }


    private fun requestImages(keyWord: String) {
        responseManager.loading()
        val disposable = imagesUseCase.execute(keyWord, { successData ->
            responseManager.hideLoading()
            if (successData.size == 0)
                _observeNotFound.value = Event(true)

            _observeImageListData.value = Event(successData)
            _observeOnlineMode.value = Event(true)
            photosList = successData
        }, { error ->
            responseManager.hideLoading()
            _observeOfflineMode.value = Event(true)
            _observeImageListData.value = Event(error)
        })

        compositeDisposable.add(disposable)
    }

    fun filterSearchKeyWord(filteredKeyWord: String) {
        if (filteredKeyWord.isNotEmpty())
            requestImages(filteredKeyWord)
    }

    //Clicked
    fun onImageClicked(imageObject: Image) {
        _observeImageClicked.value = Event(imageObject)
    }
    fun onRefreshClicked(){
        requestImages(Constants.KEY_WORD)
    }
    fun onSearchChange(string:CharSequence, before: Int, count: Int) {
        Observable.create<CharSequence>{emitter->
            emitter.onNext(string)
            emitter.onComplete()
        }
            .map { it.toString() }
            .subscribeBy(
                onNext = {}
            )

    }

//    private fun photosListSearch(keyWord: String) {
//        val newList = ArrayList<Image>()
//        for (photo in photosList) {
//            if (photo..contains(keyWord))
//                newList.add(photo)
//        }
//        _observePhotos.value = Event(newList)
//    }


    //getters:
    val observeImageListData: LiveData<Event<ArrayList<Image>>>
        get() = _observeImageListData
    val observeImageClicked: LiveData<Event<Image>>
        get() = _observeImageClicked
    val observeNotFound: LiveData<Event<Boolean>>
        get() = _observeNotFound
    val observeOfflineMode: LiveData<Event<Boolean>>
        get() = _observeOfflineMode
    val observeOnlineMode: LiveData<Event<Boolean>>
        get() = _observeOnlineMode
}