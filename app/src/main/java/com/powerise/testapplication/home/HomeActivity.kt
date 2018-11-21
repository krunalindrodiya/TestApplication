package com.powerise.testapplication.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.azova.azovatest.utils.isNetworkConnected
import com.powerise.testapplication.BR
import com.powerise.testapplication.R
import com.powerise.testapplication.databinding.ActivityMainBinding
import com.powerise.testapplication.home.core.IHomePresenter
import com.powerise.testapplication.home.core.IHomeView
import com.powerise.testapplication.home.models.SpeciesResponse
import com.powerise.testapplication.home.viewmodel.HomeViewModel
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), IHomeView {


    @Inject
    lateinit var homePresenter: IHomePresenter

    private val homeViewModel = HomeViewModel()

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding?.model = homeViewModel
        homeViewModel.isProgressBarVisible = true
        homeViewModel.notifyPropertyChanged(BR.isProgressBarVisible)
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
        binding?.progressBar?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }
    }

}
