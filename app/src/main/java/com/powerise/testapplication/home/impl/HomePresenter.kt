package com.powerise.testapplication.home.impl

import com.powerise.testapplication.home.core.IHomePresenter
import com.powerise.testapplication.home.core.IHomeView
import com.powerise.testapplication.home.models.SpeciesResponse
import com.powerise.testapplication.home.webapi.ISpeciesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class HomePresenter(private val speciesApi: ISpeciesApi, private var homeView: IHomeView?) : IHomePresenter {
    override fun unBindView() {
        homeView = null
    }

    override fun getSpecies(page: Int) {

        homeView?.let {
            speciesApi.getSpecies(page).enqueue(object : Callback<SpeciesResponse> {
                override fun onFailure(call: Call<SpeciesResponse>, t: Throwable) {
                    homeView?.onSpeciesResponse(response = null, throwable = t)
                }

                override fun onResponse(call: Call<SpeciesResponse>, response: Response<SpeciesResponse>) {
                    if (response.isSuccessful) {
                        homeView?.onSpeciesResponse(response = response.body())
                    } else {
                        response.errorBody()
                        homeView?.onSpeciesResponse(response = null, throwable = HttpException(response))
                    }
                }

            })
        }

    }
}