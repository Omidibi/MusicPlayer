package com.omid.musicplayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.model.models.LatestMp3

class SharedViewModel(application: Application) : AndroidViewModel(application) {

     val latestMp3 = MutableLiveData<LatestMp3>()
     val latestMp3List = MutableLiveData<List<LatestMp3>>()
}