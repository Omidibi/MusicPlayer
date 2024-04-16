package com.omid.musicplayer.fragments.newSongsMoreFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable

class NewSongsMoreViewModel(application: Application) : AndroidViewModel(application) {

    val checkNetworkConnection = CheckNetworkConnection(application)

    fun checkNetworkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}