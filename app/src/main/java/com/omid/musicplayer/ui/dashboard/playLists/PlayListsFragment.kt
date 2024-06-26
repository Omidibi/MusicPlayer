package com.omid.musicplayer.ui.dashboard.playLists

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.databinding.FragmentPlaylistsBinding
import com.omid.musicplayer.utils.practicalCodes.DashboardFragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class PlayListsFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistsBinding
    private lateinit var owner: LifecycleOwner
    private lateinit var playListsViewModel: PlayListsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkAvailable()
        progressBarStatus()
        playListsObservers()
        srlStatus()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentPlaylistsBinding.inflate(layoutInflater)
        playListsViewModel = ViewModelProvider(this)[PlayListsViewModel::class.java]
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbPlaylist)
    }

    private fun networkAvailable(){
        binding.apply {
            if (playListsViewModel.networkAvailable()) {
                pbPlaylist.visibility = View.GONE
                srl.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                pbPlaylist.visibility = View.GONE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun playListsObservers() {
        binding.apply {
            if (isAdded){
                playListsViewModel.checkNetworkConnection.observe(owner) { isConnected->
                    pbPlaylist.visibility = View.VISIBLE
                    srl.visibility = View.GONE
                    liveNoConnection.visibility = View.GONE
                    if (isConnected) {
                        MainWidgets.bnv.visibility = View.VISIBLE
                        if (MainWidgets.isPlay) {
                            MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                        }else {
                            MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                        }

                        playListsViewModel.playLists.observe(owner) { playLists->
                            pbPlaylist.visibility = View.GONE
                            srl.visibility = View.VISIBLE
                            liveNoConnection.visibility = View.GONE
                            playLists.let {
                                rvPlaylists.adapter = PlayListsAdapter(this@PlayListsFragment,it.onlineMp3)
                            }
                            rvPlaylists.layoutManager = GridLayoutManager(requireContext(), 2)
                        }
                    }else {
                        pbPlaylist.visibility = View.GONE
                        srl.visibility = View.GONE
                        liveNoConnection.visibility = View.VISIBLE
                        MainWidgets.bnv.visibility = View.VISIBLE
                        MainWidgets.playPause.setImageResource(R.drawable.play)
                        MainWidgets.upPlayPause.setImageResource(R.drawable.play)
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                        try {
                            MainWidgets.player.pause()
                        }catch (e: UninitializedPropertyAccessException) {
                            e.message?.let { Log.e("Catch", it) }
                        }
                    }
                }
            }
        }
    }

    private fun srlStatus(){
        binding.apply {
            srl.setOnRefreshListener {
                pbPlaylist.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                playListsViewModel.getPlayLists()
                srl.isRefreshing = false
            }
        }
    }

    private fun slidingUpPanelStatus() {
        DashboardFragmentsPracticalCodes.slidingUpPanelStatus()
    }

    private fun clickEvents(){
        DashboardFragmentsPracticalCodes.backPressed(this@PlayListsFragment)
    }
}