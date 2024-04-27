package com.omid.musicplayer.fragments.albumsByIdListFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.AlbumByIdList
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumsByIdListViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    private val albumByIdList = MutableLiveData<AlbumByIdList>()
    val checkNetworkConnection = CheckNetworkConnection(application)

    fun getAlbumsById(albumId: String): MutableLiveData<AlbumByIdList> {
        CoroutineScope(Dispatchers.IO).launch {
            webServiceCaller.getAlbumsById(albumId).apply {
                albumByIdList.postValue(this)
            }
        }
        return albumByIdList
    }

    fun checkNetworkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}