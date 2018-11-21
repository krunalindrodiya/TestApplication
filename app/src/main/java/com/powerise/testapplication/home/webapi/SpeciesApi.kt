package com.powerise.testapplication.home.webapi

import com.powerise.testapplication.home.models.SpeciesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Species web-services
 */
interface SpeciesApi {

    @GET("species/")
    fun getSpecies(@Query("page") page: Int): Call<ArrayList<SpeciesResponse>>

}