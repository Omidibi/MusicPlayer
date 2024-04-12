package com.omid.musicplayer.fragments.albumsByIdListFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.models.AlbumByIdList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumsByIdListViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    private val albumByIdList = MutableLiveData<AlbumByIdList?>()

    fun getAlbumsById(albumId: String): MutableLiveData<AlbumByIdList?> {
        CoroutineScope(Dispatchers.IO).launch {
            webServiceCaller.getAlbumsById(albumId).apply {
                albumByIdList.postValue(this)
            }
        }
        return albumByIdList
    }
}