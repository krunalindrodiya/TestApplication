package com.powerise.testapplication.home.impl

import com.powerise.testapplication.home.core.IHomePresenter
import com.powerise.testapplication.home.core.IHomeView
import com.powerise.testapplication.home.webapi.SpeciesApi

class HomePresenter(private val speciesApi: SpeciesApi, private var homeView: IHomeView?) : IHomePresenter {
    override fun unBindView() {
        this.homeView = null
    }

    override fun getSpecies() {
    }
}