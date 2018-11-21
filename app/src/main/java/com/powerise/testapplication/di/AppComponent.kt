package com.powerise.demoapp.di

import android.app.Application
import com.powerise.testapplication.TestApplication
import com.powerise.testapplication.di.ActivityBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule


/**
 * Created by Krunal on 04-06-2018.
 */
@Component(modules = arrayOf(AndroidInjectionModule::class, ActivityBuilder::class, AppModule::class))
interface AppComponent {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: TestApplication)

}