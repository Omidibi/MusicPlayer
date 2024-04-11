package com.omid.musicplayer.fragments.albumsByIdListFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.fragments.ValuesToPass
import com.omid.musicplayer.model.models.AlbumByIdList
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumsByIdListViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    val checkNetworkConnection = CheckNetworkConnection(application)
    val albumByIdList = MutableLiveData<AlbumByIdList>()

    init {
        checkNetworkConnection.observeForever { isConnected->
            if (isConnected) {
                getAlbumsById(ValuesToPass.albumsListMp3.aid)
            }
        }
    }

    fun getAlbumsById(albumId: String){
        if (checkNetworkConnection.value == true) {
            CoroutineScope(Dispatchers.IO).launch {
                webServiceCaller.getAlbumsById(albumId).apply {
                    albumByIdList.postValue(this)
                }
            }
        }
    }
}