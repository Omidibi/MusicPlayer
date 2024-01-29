package com.omid.musicplayer.util.sendData

import androidx.media3.exoplayer.ExoPlayer
import com.omid.musicplayer.model.models.LatestMp3

object MusicPlayer {
    var player: ExoPlayer? = null
    var handler: android.os.Handler? = null
    var runnable: Runnable? = null
    var latestMp3: LatestMp3? = null
    var mainLatestList: List<LatestMp3>? = null
    var isRepeat = false
    var isRandom = false
    var currentPosition: Long = 0
    var seekBarPosition: Int = 0
}