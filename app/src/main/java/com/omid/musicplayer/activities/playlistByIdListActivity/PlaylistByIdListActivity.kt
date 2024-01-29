package com.omid.musicplayer.activities.playlistByIdListActivity

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.ActivityPlaylistByIdListBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.PlayListsMp3
import com.omid.musicplayer.model.models.PlaylistByIdList
import retrofit2.Call

class PlaylistByIdListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaylistByIdListBinding
    private val webServiceCaller = WebServiceCaller()
    private lateinit var bundle: Bundle
    private var playlistMp3: PlayListsMp3? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindingAndInitialize()
        playlistById()
        clickEvent()

    }

    private fun setupBindingAndInitialize() {
        binding = ActivityPlaylistByIdListBinding.inflate(layoutInflater)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding.apply {
            setContentView(root)
            bundle = intent?.extras!!
            playlistMp3 = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("playListsInfo", PlayListsMp3::class.java)
            } else {
                intent.getParcelableExtra("playListsInfo")
            }
            namePlaylist.text = playlistMp3?.playlistName

        }

    }

    private fun clickEvent() {
        binding.apply {
            imgBack.setOnClickListener { finish() }
        }
    }

    private fun playlistById() {
        binding.apply {
            webServiceCaller.getPlaylistById(playlistMp3?.pid!!,object :IListener<PlaylistByIdList>{
                override fun onSuccess(call: Call<PlaylistByIdList>, response: PlaylistByIdList) {
                    Log.e("", "")
                    for (i in 0..<response.onlineMp3.size) {
                        val songs = response.onlineMp3[i].songsList
                        rvPlaylistList.adapter = PlaylistByIdAdapter(songs)
                    }
                    rvPlaylistList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<PlaylistByIdList>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                }

            })
        }
    }
}