package com.powerise.testapplication.home.di

import com.powerise.testapplication.home.HomeActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = arrayOf(HomeModule::class))
interface HomeComponent : AndroidInjector<HomeActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<HomeActivity>()
}