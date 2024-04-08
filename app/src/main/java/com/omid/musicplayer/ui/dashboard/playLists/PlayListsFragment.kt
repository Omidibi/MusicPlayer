package com.omid.musicplayer.ui.dashboard.playLists

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentPlaylistsBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.PlayLists
import com.omid.musicplayer.utils.practicalCodes.DashboardFragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import retrofit2.Call

class PlayListsFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistsBinding
    private val webServiceCaller = WebServiceCaller()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarStatus()
        playLists()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentPlaylistsBinding.inflate(layoutInflater)
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbPlaylist)
    }

    private fun playLists() {
        binding.apply {
            pbPlaylist.visibility = View.VISIBLE
            webServiceCaller.getPlayLists(object : IListener<PlayLists> {
                override fun onSuccess(call: Call<PlayLists>, response: PlayLists) {
                    Log.e("", "")
                    pbPlaylist.visibility = View.GONE
                    rvPlaylists.visibility = View.VISIBLE
                    rvPlaylists.adapter = PlayListsAdapter(this@PlayListsFragment,response.onlineMp3)
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

    private fun slidingUpPanelStatus() {
        DashboardFragmentsPracticalCodes.slidingUpPanelStatus()
    }

    private fun clickEvents(){
        DashboardFragmentsPracticalCodes.backPressed(this@PlayListsFragment)
    }
}