package com.omid.musicplayer.ui.dashboard.mainFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.models.LatestSong
import com.omid.musicplayer.model.models.RecentArtistList
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    val checkNetworkConnection = CheckNetworkConnection(application)
    val latestSong = MutableLiveData<LatestSong>()
    val recentArtistList = MutableLiveData<RecentArtistList>()

    init {
        checkNetworkConnection.observeForever{ isConnected->
            if (isConnected) {
                getLatestSongs()
                getRecentArtist()
            }
        }
    }

    fun getLatestSongs(){
        if (checkNetworkConnection.value == true) {
            CoroutineScope(Dispatchers.IO).launch {
                webServiceCaller.getLatestSongs().apply {
                    latestSong.postValue(this)
                }
            }
        }
    }

    fun getRecentArtist() {
        if (checkNetworkConnection.value == true) {
            CoroutineScope(Dispatchers.IO).launch {
                webServiceCaller.getRecentArtist().apply {
                    recentArtistList.postValue(this)
                }
            }
        }
    }
}