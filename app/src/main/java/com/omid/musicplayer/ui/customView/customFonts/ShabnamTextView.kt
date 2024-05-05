package com.omid.musicplayer.ui.customView.customFonts

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.omid.musicplayer.utils.configuration.AppConfiguration

class ShabnamTextView: AppCompatTextView {

    constructor(context: Context?) : super(context!!) {
        extracted()
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context!!, attrs) {
        extracted()
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        extracted()
    }

    private fun extracted() {
        Typeface.createFromAsset(AppConfiguration.getContext().assets, "font/Shabnam/Shabnam.ttf").apply {
            setTypeface(this@apply)
        }
    }
}