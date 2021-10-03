package com.danhtt.assignment.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class StateLiveData<T> : MutableLiveData<T>() {

    private val isRead = AtomicBoolean(false)

    fun observeState(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, Observer {
            it ?: return@Observer
            if (isRead.compareAndSet(false, true)) {
                observer.onChanged(it)
            }
        })
    }

    override fun postValue(value: T) {
        isRead.set(false)
        super.postValue(value)
    }

    override fun setValue(value: T) {
        isRead.set(false)
        super.setValue(value)
    }
}
