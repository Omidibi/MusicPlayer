package com.omid.musicplayer.fragments.songsListByCatIdFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.SongsByCatId
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongsListByCatIdViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    private val songsByCatId = MutableLiveData<SongsByCatId?>()
    val checkNetworkConnection = CheckNetworkConnection(application)

    fun getSongsListByCatId(catId: String): MutableLiveData<SongsByCatId?> {
        CoroutineScope(Dispatchers.IO).launch {
            webServiceCaller.getSongsListByCatId(catId).apply {
                songsByCatId.postValue(this)
            }
        }
        return songsByCatId
    }

    fun checkNetworkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}