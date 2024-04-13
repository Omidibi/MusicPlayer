package com.omid.musicplayer.ui.dashboard.albums

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
import com.omid.musicplayer.databinding.FragmentAlbumsBinding
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import com.omid.musicplayer.utils.practicalCodes.DashboardFragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class AlbumsFragment : Fragment() {

    private lateinit var binding: FragmentAlbumsBinding
    private lateinit var owner: LifecycleOwner
    private lateinit var albumsListViewModel: AlbumsListViewModel

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
        srlStatus()
        albumsListObservers()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentAlbumsBinding.inflate(layoutInflater)
        albumsListViewModel = ViewModelProvider(this)[AlbumsListViewModel::class.java]
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbAlbums)
    }

    private fun networkAvailable(){
        binding.apply {
            if (NetworkAvailable.isNetworkAvailable(requireContext())) {
                pbAlbums.visibility = View.GONE
                srl.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                pbAlbums.visibility = View.GONE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun albumsListObservers() {
        binding.apply {
            albumsListViewModel.checkNetworkConnection.observe(owner) { isConnect->
                pbAlbums.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                if (isConnect) {
                    MainWidgets.bnv.visibility = View.VISIBLE
                    if (MainWidgets.isPlay) {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }else {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    }

                    albumsListViewModel.albumList.observe(owner) { albumList->
                        pbAlbums.visibility = View.GONE
                        srl.visibility = View.VISIBLE
                        liveNoConnection.visibility = View.GONE
                        rvAlbumsList.adapter = AlbumsListAdapter(this@AlbumsFragment, albumList.onlineMp3)
                        rvAlbumsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                } else {
                    pbAlbums.visibility = View.GONE
                    srl.visibility = View.GONE
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

    private fun srlStatus(){
        binding.apply {
            srl.setOnRefreshListener {
                pbAlbums.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                albumsListViewModel.getAlbumList()
                srl.isRefreshing = false
            }
        }
    }

    private fun slidingUpPanelStatus() {
        DashboardFragmentsPracticalCodes.slidingUpPanelStatus()
    }

    private fun clickEvents(){
        DashboardFragmentsPracticalCodes.backPressed(this@AlbumsFragment)
    }
}