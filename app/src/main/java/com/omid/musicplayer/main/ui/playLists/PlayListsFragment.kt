package com.omid.musicplayer.main.ui.playLists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.omid.musicplayer.R
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentPlaylistsBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.PlayLists
import retrofit2.Call

class PlayListsFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistsBinding
    private val webServiceCaller = WebServiceCaller()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        aboutProgressBar()
        playLists()

        return binding.root
    }

    private fun setupBinding() {
        binding = FragmentPlaylistsBinding.inflate(layoutInflater)
        binding.apply {

        }
    }

    private fun aboutProgressBar() {
        binding.apply {
            val wrapDrawable = DrawableCompat.wrap(pbPlaylist.indeterminateDrawable)
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(requireContext(), R.color.torchRed))
            pbPlaylist.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        }
    }

    private fun playLists() {
        binding.apply {
            pbPlaylist.visibility = View.VISIBLE
            webServiceCaller.getPlayLists(object : IListener<PlayLists> {
                override fun onSuccess(call: Call<PlayLists>, response: PlayLists) {
                    Log.e("", "")
                    pbPlaylist.visibility = View.GONE
                    rvPlaylists.visibility = View.VISIBLE
                    rvPlaylists.adapter =
                        PlayListsAdapter(response.onlineMp3)
                    rvPlaylists.layoutManager = GridLayoutManager(requireContext(), 2)
                }

                override fun onFailure(call: Call<PlayLists>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                    pbPlaylist.visibility = View.VISIBLE
                    rvPlaylists.visibility = View.GONE
                }

            })
        }
    }
}