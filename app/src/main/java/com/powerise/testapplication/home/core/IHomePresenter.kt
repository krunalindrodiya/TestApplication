package com.powerise.testapplication.home.core

/**
 * HomePresenter
 */
interface IHomePresenter {

    fun unBindView()

    fun getSpecies(page: Int)
}