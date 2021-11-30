package com.payback.imagepicker.presentation.utils.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.payback.imagepicker.data.Resource
import com.payback.imagepicker.presentation.utils.Event

class BaseActivityViewModel : ViewModel() {

    private var _observeLoading =MutableLiveData<Event<Boolean>>()
    private var _observeSuccess =MutableLiveData<Event<String>>()
    private var _observeFailed =MutableLiveData<Event<String>>()
    private var _observeNoConnection =MutableLiveData<Event<Boolean>>()
    private var _observeHideLoading =MutableLiveData<Event<Boolean>>()


    fun getResponseState(responseState: Resource<Any>){
        when(responseState){
            is Resource.Loading -> {_observeLoading.value= Event(true) }
            is Resource.Success -> {_observeSuccess.value= Event(responseState.message!!) }
            is Resource.Failed -> {_observeFailed.value= Event(responseState.message!!) }
            is Resource.NoConnection -> {_observeNoConnection.value=Event(true)}
            is Resource.HideLoading -> {_observeHideLoading.value=Event(true)}
        }
    }

    //getters:
    val observeLoading: LiveData<Event<Boolean>>
        get() = _observeLoading
    val observeSuccess: LiveData<Event<String>>
        get() = _observeSuccess
    val observeFailed: LiveData<Event<String>>
        get() = _observeFailed
    val observeNoConnection: LiveData<Event<Boolean>>
        get() = _observeNoConnection
    val observeHideLoading: LiveData<Event<Boolean>>
        get() = _observeHideLoading
}