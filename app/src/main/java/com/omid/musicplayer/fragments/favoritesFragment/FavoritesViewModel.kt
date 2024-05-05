package com.omid.musicplayer.fragments.favoritesFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.omid.musicplayer.db.RoomDBInstance
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    val checkNetworkConnection = CheckNetworkConnection(application)

    fun showAllFavorite(): MutableList<LatestMp3> {
        return RoomDBInstance.roomDbInstance.dao().showAllFavorite()
    }

    fun networkAvailable(): Boolean {
        return NetworkAvailable.isNetworkAvailable(AppConfiguration.getContext())
    }
}