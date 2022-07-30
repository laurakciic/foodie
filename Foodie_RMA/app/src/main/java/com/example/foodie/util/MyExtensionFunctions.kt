package com.example.foodie.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

// extension function for live data
// observes live data obj only once
// called only when readDatabase fun is called bc only there is used
// solves problem for when app is running for the first time and it's calling both requestApiData and readDatabase fun
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}