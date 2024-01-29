package com.omid.musicplayer.util.configuration

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class AppConfiguration : Application() {
    private lateinit var context: Context

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        context = getContext()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: AppConfiguration? = null
        fun getContext(): Context {
            return instance!!.applicationContext
        }
    }
}