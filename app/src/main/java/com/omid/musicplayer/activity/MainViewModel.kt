package com.omid.musicplayer.activity

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.db.RoomDBInstance
import com.omid.musicplayer.model.DownloadedMp3
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.model.SearchSong
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

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

    fun checkForInsertFavorite(id: String, latestMp3: LatestMp3) {
        if (RoomDBInstance.roomDbInstance.dao().searchByIdFavorite(id).isEmpty()) {
            RoomDBInstance.roomDbInstance.dao().insertFavorite(latestMp3)
            Toast.makeText(AppConfiguration.getContext(), "${latestMp3.mp3Title} Added To Favorites List", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(AppConfiguration.getContext(), "${latestMp3.mp3Title} There is in Favorites List", Toast.LENGTH_LONG).show()
        }
    }

    fun checkForInsertDownload(id: String, latestMp3: LatestMp3) {
        if (RoomDBInstance.roomDbInstance.dao().searchByIdDownload(id).isEmpty()){
            RoomDBInstance.roomDbInstance.dao().insertDownload(latestMp3.toDownloadMp3())
        }
    }

    private fun LatestMp3.toDownloadMp3(): DownloadedMp3 {
        return DownloadedMp3(catId, categoryImage, categoryImageThumb, categoryName, cid, id, mp3Artist, mp3Description, mp3Duration, mp3ThumbnailB, mp3ThumbnailS, mp3Title, mp3Type, mp3Url, rateAvg, totalDownload, totalRate, totalViews)
    }
}