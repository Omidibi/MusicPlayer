package com.omid.musicplayer.ui.dashboard.artists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.ArtistsList
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable

class ArtistViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    val checkNetworkConnection = CheckNetworkConnection(application)
    val artistList: LiveData<ArtistsList> = webServiceCaller.artistList

    init {
        checkNetworkConnection.observeForever {  isConnected->
            if (isConnected) {
                getArtistList()
            }
        }
    }

    fun getArtistList(){
        if (checkNetworkConnection.value == true) {
            webServiceCaller.getArtistsList()
        }
    }

    fun networkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}