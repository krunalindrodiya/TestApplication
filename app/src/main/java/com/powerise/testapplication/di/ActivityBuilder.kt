package com.powerise.testapplication.di

import android.app.Activity
import com.powerise.testapplication.home.HomeActivity
import com.powerise.testapplication.home.di.HomeComponent
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap


/**
 * Created by Krunal
 */
@Module
abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(HomeActivity::class)
    abstract fun bindHomeActivity(builder: HomeComponent.Builder): AndroidInjector.Factory<out Activity>

}