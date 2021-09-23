package com.payback.imagepicker.presentation.ui.image_picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.payback.imagepicker.domain.model.Image
import com.payback.imagepicker.domain.use_case.ImageListUseCase
import com.payback.imagepicker.manager.base.BaseViewModel
import com.payback.imagepicker.manager.base.ResponseManager
import com.payback.imagepicker.manager.utilities.Constants
import com.payback.imagepicker.manager.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor(
    private val imageListUseCase: ImageListUseCase,
    private val responseManager: ResponseManager
) : BaseViewModel() {

    private val _observeImageListData = MutableLiveData<Event<ArrayList<Image>>>()
    private val _observeImageClicked = MutableLiveData<Event<Image>>()
    private val _observeNotFound = MutableLiveData<Event<Boolean>>()
    private val _observeOfflineMode = MutableLiveData<Event<Boolean>>()
    private val _observeOnlineMode = MutableLiveData<Event<Boolean>>()



    init {
        getImageList(Constants.KEY_WORD)
    }


    private fun getImageList(keyWord: String) {
        responseManager.loading()
        val disposable = imageListUseCase.execute(keyWord, { success ->
            responseManager.hideLoading()
            if (success.size == 0)
                _observeNotFound.value = Event(true)

            _observeImageListData.value = Event(success)
            _observeOnlineMode.value = Event(true)
        }, { error ->
            responseManager.hideLoading()
            _observeOfflineMode.value = Event(true)
            _observeImageListData.value = Event(error)
        })

        compositeDisposable.add(disposable)
    }

    fun filterSearchKeyWord(filteredKeyWord: String) {
        if (filteredKeyWord.isNotEmpty())
            getImageList(filteredKeyWord)
    }

    //Clicked
    fun onImageClicked(imageObject: Image) {
        _observeImageClicked.value = Event(imageObject)
    }
    fun onRefreshClicked(){
        getImageList(Constants.KEY_WORD)
    }


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