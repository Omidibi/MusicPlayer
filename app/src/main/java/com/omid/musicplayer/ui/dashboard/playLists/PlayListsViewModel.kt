package com.omid.musicplayer.ui.dashboard.playLists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.models.PlayLists
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection

class PlayListsViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    val checkNetworkConnection = CheckNetworkConnection(application)
    val playLists: LiveData<PlayLists> = webServiceCaller.playLists

    init {
        checkNetworkConnection.observeForever { isConnected ->
            if (isConnected) {
                getPlayLists()
            }
        }
    }

    fun getPlayLists() {
        if (checkNetworkConnection.value == true) {
            webServiceCaller.getPlayLists()
        }
    }
}