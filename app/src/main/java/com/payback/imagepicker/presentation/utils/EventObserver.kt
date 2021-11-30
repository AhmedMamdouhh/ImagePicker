package com.payback.imagepicker.presentation.utils

import androidx.lifecycle.Observer


class EventObserver<T>(private val observe: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            observe(value)
        }
    }
}