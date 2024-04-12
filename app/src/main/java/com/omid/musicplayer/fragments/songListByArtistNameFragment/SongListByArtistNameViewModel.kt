package com.omid.musicplayer.fragments.songListByArtistNameFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.model.models.SongListByArtistName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongListByArtistNameViewModel(application: Application) : AndroidViewModel(application) {

    private val webServiceCaller = WebServiceCaller()
    private val songListByArtistName = MutableLiveData<SongListByArtistName?>()

    fun getSongListByArtistName(artistName: String): MutableLiveData<SongListByArtistName?> {
        CoroutineScope(Dispatchers.IO).launch {
            webServiceCaller.getSongListByArtistName(artistName).apply {
                songListByArtistName.postValue(this)
            }
        }
        return songListByArtistName
    }
}