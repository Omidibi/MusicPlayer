package com.omid.musicplayer.fragments.playlistByIdListFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.fragments.ValuesToPass
import com.omid.musicplayer.model.models.PlaylistByIdList
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistByIdListViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    val checkNetworkConnection = CheckNetworkConnection(application)
    val playListByIdList = MutableLiveData<PlaylistByIdList>()

    init {
        checkNetworkConnection.observeForever { isConnected ->
            if (isConnected) {
                getPlaylistById(ValuesToPass.playListsMp3.pid)
            }
        }
    }

    fun getPlaylistById(playListId: String) {
        if (checkNetworkConnection.value == true) {
            CoroutineScope(Dispatchers.IO).launch {
                webServiceCaller.getPlaylistById(playListId).apply {
                    playListByIdList.postValue(this)
                }
            }
        }
    }
}