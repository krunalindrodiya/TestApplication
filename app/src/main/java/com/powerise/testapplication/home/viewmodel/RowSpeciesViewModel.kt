package com.powerise.testapplication.home.viewmodel

import android.databinding.BaseObservable
import android.view.View

class RowSpeciesViewModel(
    val currentState: String,
    val name: String,
    val classification: String,
    val designation: String,
    val onClickListener: View.OnClickListener
) : BaseObservable()