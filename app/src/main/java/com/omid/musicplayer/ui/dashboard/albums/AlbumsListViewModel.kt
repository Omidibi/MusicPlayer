package com.omid.musicplayer.ui.dashboard.albums

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.AlbumsList
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable

class AlbumsListViewModel(application: Application) : AndroidViewModel(application) {

    val checkNetworkConnection = CheckNetworkConnection(application)
    private val webServiceCaller = WebServiceCaller()
    val albumList: LiveData<AlbumsList> = webServiceCaller.albumList

    init {
        checkNetworkConnection.observeForever { isConnect ->
            if (isConnect) {
                getAlbumList()
            }
        }
    }

    fun getAlbumList() {
        if (checkNetworkConnection.value == true) {
            webServiceCaller.getAlbums()
        }
    }

    fun networkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}