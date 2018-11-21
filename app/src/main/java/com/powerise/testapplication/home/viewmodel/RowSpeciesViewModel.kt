package com.powerise.testapplication.home.viewmodel

import android.databinding.BaseObservable

class RowSpeciesViewModel(
    val currentState: String,
    val name: String,
    val classification: String,
    val designation: String
) : BaseObservable()