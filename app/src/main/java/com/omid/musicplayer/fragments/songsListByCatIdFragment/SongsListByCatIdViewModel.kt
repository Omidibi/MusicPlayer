package com.omid.musicplayer.fragments.songsListByCatIdFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.models.SongsByCatId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongsListByCatIdViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    private val songsByCatId = MutableLiveData<SongsByCatId?>()

    fun getSongsListByCatId(catId: String): MutableLiveData<SongsByCatId?> {
        CoroutineScope(Dispatchers.IO).launch {
            webServiceCaller.getSongsListByCatId(catId).apply {
                songsByCatId.postValue(this)
            }
        }
        return songsByCatId
    }
}