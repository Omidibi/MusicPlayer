package com.omid.musicplayer.fragments.playlistByIdListFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.PlaylistByIdList
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistByIdListViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    private val playListByIdList = MutableLiveData<PlaylistByIdList?>()
    val checkNetworkConnection = CheckNetworkConnection(application)

    fun getPlaylistById(playListId: String): MutableLiveData<PlaylistByIdList?> {
        CoroutineScope(Dispatchers.IO).launch {
            webServiceCaller.getPlaylistById(playListId).apply {
                playListByIdList.postValue(this)
            }
        }
        return playListByIdList
    }

    fun checkNetworkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}