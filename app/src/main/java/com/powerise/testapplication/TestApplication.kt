package com.powerise.testapplication

import android.app.Activity
import android.app.Application
import com.powerise.demoapp.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class TestApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = activityDispatchingAndroidInjector


    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree());
        } else {
            //Need to implement custom log tree for handling crash-reporting
        }

    }

}