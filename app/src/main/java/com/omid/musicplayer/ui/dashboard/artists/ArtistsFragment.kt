package com.omid.musicplayer.ui.dashboard.artists

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.databinding.FragmentArtistsBinding
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import com.omid.musicplayer.utils.practicalCodes.DashboardFragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class ArtistsFragment : Fragment() {

    private lateinit var binding: FragmentArtistsBinding
    private lateinit var artistViewModel: ArtistViewModel

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
        artistViewModel = ViewModelProvider(requireActivity())[ArtistViewModel::class.java]
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbArtist)
    }

    private fun networkAvailable(){
        binding.apply {
            if (NetworkAvailable.isNetworkAvailable(requireContext())) {
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
            artistViewModel.checkNetworkConnection.observe(viewLifecycleOwner) { isConnected->
                pbArtist.visibility = View.VISIBLE
                srl.visibility  = View.GONE
                liveNoConnection.visibility = View.GONE
                if (isConnected) {
                    artistViewModel.artistList.observe(viewLifecycleOwner) { artistList->
                        pbArtist.visibility = View.GONE
                        srl.visibility  = View.VISIBLE
                        liveNoConnection.visibility = View.GONE
                        rvArtists.adapter = ArtistAdapter(this@ArtistsFragment,artistList.onlineMp3)
                        rvArtists.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                }else {
                    pbArtist.visibility = View.GONE
                    srl.visibility  = View.GONE
                    liveNoConnection.visibility = View.VISIBLE
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    try {
                        MainWidgets.player.stop()
                    }catch (e: UninitializedPropertyAccessException) {
                        e.message?.let { Log.e("Catch", it) }
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