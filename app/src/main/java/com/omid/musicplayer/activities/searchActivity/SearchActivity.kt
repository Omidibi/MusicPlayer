package com.omid.musicplayer.activities.searchActivity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.ActivitySearchBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.SearchSong
import retrofit2.Call

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val webServiceCaller = WebServiceCaller()
    private var content = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        clickEvent()

    }

    private fun setupBinding() {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding.apply {
            setContentView(root)

        }
    }

    private fun clickEvent() {
        binding.apply {

            ivBack.setOnClickListener { finish() }

            clIvClearText.setOnClickListener {
                songSearch.setText("")
            }

            clIvSearch.setOnClickListener {
                if (songSearch.text?.isEmpty() == true) {
                    resultSearch.visibility = View.VISIBLE
                    rvSearchSong.visibility = View.GONE
                } else {
                    content = songSearch.text.toString()
                    resultSearch.visibility = View.GONE
                    rvSearchSong.visibility = View.VISIBLE
                    songSearch()
                }

            }
        }
    }

    private fun songSearch() {
        binding.apply {
            webServiceCaller.getSearchSong(content, object : IListener<SearchSong> {
                override fun onSuccess(call: Call<SearchSong>, response: SearchSong) {
                    Log.e("", "")
                    rvSearchSong.adapter = SongSearchAdapter(response.onlineMp3)
                    rvSearchSong.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<SearchSong>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                }

            })
        }
    }
}