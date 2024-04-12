package com.omid.musicplayer.fragments.playlistByIdListFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.models.PlaylistByIdList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistByIdListViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    private val playListByIdList = MutableLiveData<PlaylistByIdList?>()

    fun getPlaylistById(playListId: String): MutableLiveData<PlaylistByIdList?> {
        CoroutineScope(Dispatchers.IO).launch {
            webServiceCaller.getPlaylistById(playListId).apply {
                playListByIdList.postValue(this)
            }
        }
        return playListByIdList
    }
}