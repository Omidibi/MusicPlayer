package com.omid.musicplayer.utils.share

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.omid.musicplayer.model.LatestMp3

class Share {

    companion object {

        fun shareMusic(latestMp3: LatestMp3, fragment: Fragment) {
            val shareMusic = "${latestMp3.mp3Title}\n${latestMp3.mp3Artist}"
            Intent(Intent.ACTION_SEND).apply {
                this.type = "text/plain"
                this.putExtra(Intent.EXTRA_TEXT, shareMusic)
                fragment.requireActivity().startActivity(this)
            }
        }

        fun mainActivityShareMusic(latestMp3: LatestMp3, activity: Activity) {
            val shareMusic = "${latestMp3.mp3Title}\n${latestMp3.mp3Artist}"
            Intent(Intent.ACTION_SEND).apply {
                this.type = "text/plain"
                this.putExtra(Intent.EXTRA_TEXT, shareMusic)
                activity.startActivity(this)
            }
        }

        fun shareApp(fragment: Fragment) {
            val shareApp = "install this app and enjoy that"
            Intent(Intent.ACTION_SEND).apply {
                this.type = "text/plain"
                this.putExtra(Intent.EXTRA_TEXT, shareApp)
                fragment.requireActivity().startActivity(this)
            }
        }
    }
}