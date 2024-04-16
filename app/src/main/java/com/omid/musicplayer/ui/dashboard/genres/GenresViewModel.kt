package com.omid.musicplayer.ui.dashboard.genres

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.CategoriesList
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable

class GenresViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    val checkNetworkConnection = CheckNetworkConnection(application)
    val categoriesList: LiveData<CategoriesList> = webServiceCaller.categoriesList

    init {
        checkNetworkConnection.observeForever { isConnected ->
            if (isConnected) {
                getCategoriesList()
            }
        }
    }

    fun getCategoriesList() {
        if (checkNetworkConnection.value == true) {
            webServiceCaller.getCategories()
        }
    }

    fun networkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}