package com.omid.musicplayer.fragments.searchFragment

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.databinding.FragmentSearchBinding
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class SearchFragment : Fragment(),IOnSongClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var owner: LifecycleOwner

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
        clickEvent()
        progressStatus()
        checkNetLiveData()
        slidingUpPanelStatus()
    }

    private fun setupBinding() {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    private fun networkAvailable() {
        binding.apply {
            if (searchViewModel.checkNetworkAvailable()) {
                pbSearch.visibility = View.GONE
                rvSearchSong.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            } else {
                pbSearch.visibility = View.GONE
                rvSearchSong.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun progressStatus() {
        ProgressBarStatus.pbStatus(binding.pbSearch)
    }

    private fun clickEvent() {
        binding.apply {

            FragmentsPracticalCodes.backPressed(this@SearchFragment)

            ivBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgetStatus.visible()
            }

            clIvClearText.setOnClickListener {
                songSearch.setText("")
            }
        }
    }

    private fun checkNetLiveData(){
        binding.apply {
            searchViewModel.checkNetworkConnection.observe(owner) { isConnected->
                if (isConnected) {
                    if (MainWidgets.isPlay) {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }else {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    }
                    liveNoConnection.visibility = View.GONE
                    clIvSearch.setOnClickListener {
                        if (songSearch.text?.isEmpty() == true) {
                            resultSearch.visibility = View.VISIBLE
                            rvSearchSong.visibility = View.GONE

                        } else {
                            resultSearch.visibility = View.GONE
                            rvSearchSong.visibility = View.VISIBLE
                            songSearchObservers()
                        }
                    }
                }else {
                    liveNoConnection.visibility = View.VISIBLE
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

    private fun songSearchObservers() {
        binding.apply {
            if (isAdded){
                pbSearch.visibility = View.VISIBLE
                rvSearchSong.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                searchViewModel.getSearchSong(songSearch.text.toString()).observe(owner) { searchSong ->
                    pbSearch.visibility = View.GONE
                    rvSearchSong.visibility = View.VISIBLE
                    liveNoConnection.visibility = View.GONE
                    searchSong.let {
                        rvSearchSong.adapter = SongSearchAdapter(it.onlineMp3,this@SearchFragment)
                    }
                    rvSearchSong.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }

    override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
        sharedViewModel.latestMp3List.value = latestSongsList
        sharedViewModel.latestMp3.value = latestSongInfo
    }
}