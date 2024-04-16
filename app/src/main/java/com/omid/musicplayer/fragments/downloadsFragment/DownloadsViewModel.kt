package com.omid.musicplayer.fragments.downloadsFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.omid.musicplayer.db.RoomDBInstance
import com.omid.musicplayer.model.DownloadedMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable

class DownloadsViewModel(application: Application) : AndroidViewModel(application) {

    val checkNetworkConnection = CheckNetworkConnection(application)

    fun showAllDownload(): MutableList<DownloadedMp3> {
        return RoomDBInstance.roomDbInstance.dao().showAllDownload()
    }

    fun networkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}