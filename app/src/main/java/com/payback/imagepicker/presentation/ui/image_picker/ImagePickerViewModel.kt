package com.payback.imagepicker.presentation.ui.image_picker

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.payback.imagepicker.data.Resource
import com.payback.imagepicker.domain.model.Image
import com.payback.imagepicker.domain.use_case.ImageListUseCase
import com.payback.imagepicker.manager.base.BaseViewModel
import com.payback.imagepicker.manager.base.ResponseManager
import com.payback.imagepicker.manager.utilities.Constants
import com.payback.imagepicker.manager.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor(
    private val imageListUseCase: ImageListUseCase,
    private val responseManager: ResponseManager
) : BaseViewModel() {

    private val _observeImageListData = MutableLiveData<Event<ArrayList<Image>>>()
    private val _observeImageClicked = MutableLiveData<Event<Image>>()
    private val _observeNotFound = MutableLiveData<Event<Boolean>>()


    init {
        getImageList(Constants.KEY_WORD)
    }


    private fun getImageList(keyWord: String) {
        responseManager.loading()
        val disposable = imageListUseCase.execute(keyWord, { success ->
            responseManager.hideLoading()

            if (success.size ==0)
                _observeNotFound.value = Event(true)

            _observeImageListData.value = Event(success)
        }, { error ->
            responseManager.hideLoading()
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

    //getters:
    val observeImageListData: LiveData<Event<ArrayList<Image>>>
        get() = _observeImageListData
    val observeImageClicked: LiveData<Event<Image>>
        get() = _observeImageClicked
    val observeNotFound: LiveData<Event<Boolean>>
        get() = _observeNotFound
}