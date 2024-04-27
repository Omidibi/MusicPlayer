package com.omid.musicplayer.fragments.searchFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.SearchSong
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    private val searchSong = MutableLiveData<SearchSong>()
    val checkNetworkConnection = CheckNetworkConnection(application)

    fun getSearchSong(searchText: String): MutableLiveData<SearchSong> {
        CoroutineScope(Dispatchers.IO).launch {
            webServiceCaller.getSearchSong(searchText).apply {
                searchSong.postValue(this)
            }
        }
        return searchSong
    }

    fun checkNetworkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}