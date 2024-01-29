package com.omid.musicplayer.activities.menuActivity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.omid.musicplayer.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        clickEvent()
    }

    private fun setupBinding() {
        binding = ActivityMenuBinding.inflate(layoutInflater)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding.apply {
            setContentView(root)
        }
    }

    private fun clickEvent() {
        binding.apply {
            ivBack.setOnClickListener { finish() }

            clVideoArchive.setOnClickListener {
                Toast.makeText(applicationContext, "clVideoArchive is OK", Toast.LENGTH_LONG).show()
            }

            clDownloads.setOnClickListener {
                Toast.makeText(applicationContext, "clDownloads is OK", Toast.LENGTH_LONG).show()
            }

            clFavorites.setOnClickListener {
                Toast.makeText(applicationContext, "clFavorites is OK", Toast.LENGTH_LONG).show()
            }

            clRate.setOnClickListener {
                Toast.makeText(applicationContext, "clRate is OK", Toast.LENGTH_LONG).show()
            }

            clAbout.setOnClickListener {
                Toast.makeText(applicationContext, "clAbout is OK", Toast.LENGTH_LONG).show()
            }

            clPrivacyPolicy.setOnClickListener {
                Toast.makeText(applicationContext, "clPrivacyPolicy is OK", Toast.LENGTH_LONG)
                    .show()
            }

            clShare.setOnClickListener {
                Toast.makeText(applicationContext, "clShare is OK", Toast.LENGTH_LONG).show()
            }
        }
    }
}