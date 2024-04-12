package com.omid.musicplayer.fragments.searchFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.models.SearchSong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    private val searchSong = MutableLiveData<SearchSong?>()

    fun getSearchSong(searchText: String): MutableLiveData<SearchSong?> {
        CoroutineScope(Dispatchers.IO).launch {
            webServiceCaller.getSearchSong(searchText).apply {
                searchSong.postValue(this)
            }
        }
        return searchSong
    }
}