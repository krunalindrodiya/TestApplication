package com.powerise.testapplication.home.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable

class HomeViewModel : BaseObservable() {

    @Bindable
    var isProgressBarVisible: Boolean = false
}