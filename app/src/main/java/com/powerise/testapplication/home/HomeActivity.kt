package com.powerise.testapplication.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.azova.azovatest.utils.isNetworkConnected
import com.powerise.testapplication.R
import com.powerise.testapplication.home.core.IHomePresenter
import com.powerise.testapplication.home.core.IHomeView
import com.powerise.testapplication.home.models.SpeciesResponse
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), IHomeView {


    @Inject
    lateinit var homePresenter: IHomePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)


        getSpecies(1)

    }

    private fun getSpecies(page: Int) {
        if (isNetworkConnected()) {
            homePresenter.getSpecies(page)
        } else {
            showMessage(getString(R.string.msg_no_internet))
        }
    }

    override fun onSpeciesResponse(response: SpeciesResponse?, throwable: Throwable?) {
        when {
            response != null -> {
                Timber.d("Response received-${response.results}")
            }
            throwable != null -> {
                Timber.d("Throwable received-${throwable.message}")
            }
            else -> {
                Timber.d("else execute-")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.unBindView()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
