package com.powerise.testapplication.home.models

import com.google.gson.annotations.SerializedName

/**
 * SpeciesResponse
 */
data class SpeciesResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: ArrayList<SpeciesModel>
)