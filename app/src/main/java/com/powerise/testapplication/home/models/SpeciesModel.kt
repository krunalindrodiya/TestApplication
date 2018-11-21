package com.powerise.testapplication.home.models

import com.google.gson.annotations.SerializedName

/**
 * SpeciesModel
 */
data class SpeciesModel(
    @SerializedName("name") val name: String,
    @SerializedName("classification") val classification: String,
    @SerializedName("designation") val designation: String,
    var isExtinct: Boolean = false
)