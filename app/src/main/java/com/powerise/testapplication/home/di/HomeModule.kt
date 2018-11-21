package com.powerise.testapplication.home.di

import com.powerise.testapplication.home.HomeActivity
import com.powerise.testapplication.home.core.IHomePresenter
import com.powerise.testapplication.home.core.IHomeView
import com.powerise.testapplication.home.impl.HomePresenter
import com.powerise.testapplication.home.webapi.SpeciesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class HomeModule {
    @Provides
    fun provideHomeView(homeActivity: HomeActivity): IHomeView {
        return homeActivity
    }

    @Provides
    fun provideSpeciesApi(retrofit: Retrofit): SpeciesApi {
        return retrofit.create(SpeciesApi::class.java)
    }

    @Provides
    fun provideHomePresenter(api: SpeciesApi, view: IHomeView): IHomePresenter {
        return HomePresenter(api, view)
    }
}