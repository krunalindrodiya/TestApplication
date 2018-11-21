package com.powerise.testapplication.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.powerise.testapplication.R
import com.powerise.testapplication.home.core.IHomePresenter
import com.powerise.testapplication.home.core.IHomeView
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), IHomeView {

    @Inject
    lateinit var homePresenter: IHomePresenter

    override fun onSpeciesResponse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)

        if (homePresenter != null) {
            Timber.d("Presenter is not null")
        } else {
            Timber.d("Presenter is null")
        }

    }
}
