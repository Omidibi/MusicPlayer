package com.omid.musicplayer.fragments.menuFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    fun checkNetworkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}