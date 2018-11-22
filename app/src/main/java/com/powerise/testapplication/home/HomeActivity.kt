package com.powerise.testapplication.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.azova.azovatest.utils.isNetworkConnected
import com.powerise.testapplication.BR
import com.powerise.testapplication.R
import com.powerise.testapplication.databinding.ActivityMainBinding
import com.powerise.testapplication.home.adapter.SpeciesAdapter
import com.powerise.testapplication.home.core.IHomePresenter
import com.powerise.testapplication.home.core.IHomeView
import com.powerise.testapplication.home.core.IPageLoader
import com.powerise.testapplication.home.models.SpeciesResponse
import com.powerise.testapplication.home.viewmodel.HomeViewModel
import dagger.android.AndroidInjection
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), IHomeView, IPageLoader {


    @Inject
    lateinit var homePresenter: IHomePresenter

    private val homeViewModel = HomeViewModel()

    private var binding: ActivityMainBinding? = null

    private var adapter: SpeciesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding?.model = homeViewModel
        homeViewModel.isProgressBarVisible = true
        homeViewModel.notifyPropertyChanged(BR.isProgressBarVisible)
        getSpecies()


        binding?.swipeContainer?.setOnRefreshListener {
            adapter?.clearAll()
            adapter = null
            binding?.recyclerView?.adapter = adapter
            homePresenter.clearPageCount()
            getSpecies()
        }

        binding?.swipeContainer?.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        );


    }

    private fun getSpecies() {
        if (isNetworkConnected()) {
            binding?.swipeContainer?.isEnabled = false
            homePresenter.getSpecies()
        } else {
            if (homeViewModel.isProgressBarVisible) {
                homeViewModel.isProgressBarVisible = false
                homeViewModel.notifyPropertyChanged(BR.isProgressBarVisible)
            }
            showMessage(getString(R.string.msg_no_network))
        }
    }

    override fun onSpeciesResponse(response: SpeciesResponse?, throwable: Throwable?) {

        binding?.swipeContainer?.isEnabled = true



        if (homeViewModel.isProgressBarVisible) {
            homeViewModel.isProgressBarVisible = false
            homeViewModel.notifyChange()
        }


        when {
            response != null -> {
                if (adapter == null) {
                    adapter = SpeciesAdapter(this, response.results, this)
                    binding?.recyclerView?.layoutManager = LinearLayoutManager(this)
                    binding?.recyclerView?.adapter = adapter
                } else {
                    if (response.results != null && response.results.isNotEmpty()) {
                        adapter?.addSpecies(response.results)
                    } else {
                        adapter?.noElementFound()
                    }
                }
            }
            throwable != null -> {
                if (throwable is HttpException) {
                    if (throwable.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                        if (adapter != null) {
                            adapter?.noElementFound()
                        } else {
                            showMessage(getString(R.string.msg_server_error))
                        }
                    } else {
                        showMessage(throwable.message ?: getString(R.string.msg_server_error))
                    }
                } else {
                    if (adapter != null) {
                        adapter?.noElementFound()
                    }
                    showMessage(throwable.message ?: getString(R.string.msg_server_error))
                }
            }
            else -> {
                if (adapter != null) {
                    adapter?.noElementFound()
                }
                showMessage(getString(R.string.msg_server_error))
            }
        }
        if (binding?.swipeContainer?.isRefreshing == true) {
            binding?.swipeContainer?.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.unBindView()
    }

    override fun showMessage(message: String) {
        if (adapter != null) {
            binding?.progressBar?.let {
                Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
            }
        } else {
            homeViewModel.isErrorVisible = true
            homeViewModel.textError = message
            homeViewModel.notifyChange()
        }
    }

    override fun loadNextPage() {
        getSpecies()
    }

}
