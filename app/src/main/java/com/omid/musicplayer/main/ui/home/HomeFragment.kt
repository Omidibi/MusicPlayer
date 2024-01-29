package com.omid.musicplayer.main.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.R
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentHomeBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.model.models.LatestSong
import com.omid.musicplayer.model.models.RecentArtistList
import com.omid.musicplayer.util.sendData.IOnSongClickListener
import com.omid.musicplayer.util.sendData.ISendToActivity
import retrofit2.Call

class HomeFragment(private val iSendToActivity: ISendToActivity) : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val webServiceCaller = WebServiceCaller()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        aboutProgressBar()
        latestSongs()
        recentArtist()

        return binding.root
    }

    private fun setupBinding() {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.apply {

        }
    }

    private fun aboutProgressBar() {
        binding.apply {
            val wrapDrawable = DrawableCompat.wrap(pbLoading.indeterminateDrawable)
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(requireContext(), R.color.torchRed))
            pbLoading.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        }
    }

    private fun latestSongs() {
        binding.apply {
            pbLoading.visibility = View.VISIBLE
            mainScroll.visibility = View.GONE
            webServiceCaller.getLatestSongs(object : IListener<LatestSong> {
                override fun onSuccess(call: Call<LatestSong>, response: LatestSong) {
                    Log.e("", "")
                    pbLoading.visibility = View.GONE
                    mainScroll.visibility = View.VISIBLE
                    val adapter = LatestSongsAdapter(requireActivity(), response.onlineMp3, object : IOnSongClickListener {
                        override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                            iSendToActivity.sendSongInfo(latestSongInfo,latestSongsList)
                            Log.e("Click", latestSongInfo.mp3Url)
                        }

                    })
                    rvLatestSongs.adapter = adapter

                    Log.e("", "")
                    rvLatestSongs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }

                override fun onFailure(call: Call<LatestSong>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                    pbLoading.visibility = View.VISIBLE
                    mainScroll.visibility = View.GONE
                }

            })
        }
    }

    private fun recentArtist() {
        binding.apply {
            pbLoading.visibility = View.VISIBLE
            mainScroll.visibility = View.GONE
            webServiceCaller.getRecentArtist(object : IListener<RecentArtistList> {
                override fun onSuccess(call: Call<RecentArtistList>, response: RecentArtistList) {
                    Log.e("", "")
                    pbLoading.visibility = View.GONE
                    mainScroll.visibility = View.VISIBLE
                    rvRecentArtist.adapter = RecentArtistAdapter(response.onlineMp3)
                    rvRecentArtist.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }

                override fun onFailure(call: Call<RecentArtistList>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                    pbLoading.visibility = View.VISIBLE
                    mainScroll.visibility = View.GONE
                }

            })
        }
    }
}