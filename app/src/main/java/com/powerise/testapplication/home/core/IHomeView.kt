package com.powerise.testapplication.home.core

import com.powerise.testapplication.home.models.SpeciesResponse

/**
 * HomeView
 */
interface IHomeView {

    fun onSpeciesResponse(response: SpeciesResponse? = null, throwable: Throwable? = null)

    fun showMessage(message: String)


}