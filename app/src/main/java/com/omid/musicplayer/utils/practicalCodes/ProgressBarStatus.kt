package com.omid.musicplayer.utils.practicalCodes

import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.omid.musicplayer.R
import com.omid.musicplayer.utils.configuration.AppConfiguration

class ProgressBarStatus {

    companion object {

        fun pbStatus(progressBar: ProgressBar){
            val wrapDrawable = DrawableCompat.wrap(progressBar.indeterminateDrawable)
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(AppConfiguration.getContext(), R.color.torchRed))
            progressBar.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        }
    }
}