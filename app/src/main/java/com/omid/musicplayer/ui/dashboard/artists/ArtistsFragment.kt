package com.omid.musicplayer.ui.dashboard.artists

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.databinding.FragmentArtistsBinding
import com.omid.musicplayer.utils.practicalCodes.DashboardFragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class ArtistsFragment : Fragment() {

    private lateinit var binding: FragmentArtistsBinding
    private lateinit var owner: LifecycleOwner
    private lateinit var artistViewModel: ArtistViewModel

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
        progressBarStatus()
        networkAvailable()
        artistsListObservers()
        srlStatus()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentArtistsBinding.inflate(layoutInflater)
        artistViewModel = ViewModelProvider(this)[ArtistViewModel::class.java]
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbArtist)
    }

    private fun networkAvailable(){
        binding.apply {
            if (artistViewModel.networkAvailable()) {
                pbArtist.visibility = View.GONE
                srl.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                pbArtist.visibility = View.GONE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun artistsListObservers() {
        binding.apply {
            if (isAdded){
                artistViewModel.checkNetworkConnection.observe(owner) { isConnected->
                    pbArtist.visibility = View.VISIBLE
                    srl.visibility  = View.GONE
                    liveNoConnection.visibility = View.GONE
                    if (isConnected) {
                        MainWidgets.bnv.visibility = View.VISIBLE
                        if (MainWidgets.isPlay) {
                            MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                        }else {
                            MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                        }

                        artistViewModel.artistList.observe(owner) { artistList->
                            pbArtist.visibility = View.GONE
                            srl.visibility  = View.VISIBLE
                            liveNoConnection.visibility = View.GONE
                            artistList.let {
                                rvArtists.adapter = ArtistAdapter(this@ArtistsFragment,it.onlineMp3)
                            }
                            rvArtists.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        }
                    }else {
                        pbArtist.visibility = View.GONE
                        srl.visibility  = View.GONE
                        liveNoConnection.visibility = View.VISIBLE
                        MainWidgets.playPause.setImageResource(R.drawable.play)
                        MainWidgets.upPlayPause.setImageResource(R.drawable.play)
                        MainWidgets.bnv.visibility = View.VISIBLE
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
                pbArtist.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                artistViewModel.getArtistList()
                srl.isRefreshing = false
            }
        }
    }

    private fun slidingUpPanelStatus() {
        DashboardFragmentsPracticalCodes.slidingUpPanelStatus()
    }

    private fun clickEvents(){
        DashboardFragmentsPracticalCodes.backPressed(this@ArtistsFragment)
    }
}