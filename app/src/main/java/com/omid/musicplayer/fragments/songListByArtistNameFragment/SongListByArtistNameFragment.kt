package com.omid.musicplayer.fragments.songListByArtistNameFragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
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
import com.omid.musicplayer.databinding.FragmentSongListByArtistNameBinding
import com.omid.musicplayer.model.ArtistsMp3
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class SongListByArtistNameFragment : Fragment() {

    private lateinit var binding: FragmentSongListByArtistNameBinding
    private lateinit var owner: LifecycleOwner
    private lateinit var sharedViewModel : SharedViewModel
    private lateinit var songListByArtistNameViewModel: SongListByArtistNameViewModel
    private lateinit var artistsMp3 : ArtistsMp3

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
        getData()
        progressBarStatus()
        slidingUpPanelStatus()
        clickEvents()
        networkAvailable()
        getSongListByArtistNameObservers()
        srlStatus()
    }

    private fun setupBinding(){
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentSongListByArtistNameBinding.inflate(layoutInflater)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        songListByArtistNameViewModel = ViewModelProvider(this)[SongListByArtistNameViewModel::class.java]
    }

    private fun getData(){
        binding.apply {
            artistsMp3 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                requireArguments().getParcelable("ArtistInfo", ArtistsMp3::class.java)!!
            }else {
                requireArguments().getParcelable("ArtistInfo")!!
            }
            titleToolbar.text = artistsMp3.artistName
        }
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pb)
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }

    private fun clickEvents(){
        binding.apply {

            FragmentsPracticalCodes.backPressed(this@SongListByArtistNameFragment)

            ivBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgetStatus.visible()
            }
        }
    }

    private fun networkAvailable(){
        binding.apply {
            if (songListByArtistNameViewModel.checkNetworkAvailable()) {
                pb.visibility = View.GONE
                srl.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                pb.visibility = View.GONE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun getSongListByArtistNameObservers(){
        binding.apply {
            if (isAdded){
                songListByArtistNameViewModel.checkNetworkConnection.observe(owner) { isConnected->
                    pb.visibility = View.VISIBLE
                    srl.visibility = View.GONE
                    liveNoConnection.visibility = View.GONE
                    if (isConnected) {
                        if (MainWidgets.isPlay) {
                            MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                        }else {
                            MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                        }

                        songListByArtistNameViewModel.getSongListByArtistName(artistsMp3.artistName).observe(owner) { songListByArtistName->
                            pb.visibility = View.GONE
                            srl.visibility = View.VISIBLE
                            liveNoConnection.visibility = View.GONE
                            songListByArtistName.let {
                                rvListByArtisName.adapter = SongListByArtistNameAdapter(it.songListByArtistName,object : IOnSongClickListener {
                                    override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                                        sharedViewModel.latestMp3.value = latestSongInfo
                                        sharedViewModel.latestMp3List.value = latestSongsList
                                    }

                                })
                            }
                            rvListByArtisName.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                        }
                    }else {
                        pb.visibility = View.GONE
                        srl.visibility = View.GONE
                        liveNoConnection.visibility = View.VISIBLE
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                        MainWidgets.playPause.setImageResource(R.drawable.play)
                        MainWidgets.upPlayPause.setImageResource(R.drawable.play)
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
                pb.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                songListByArtistNameViewModel.getSongListByArtistName(artistsMp3.artistName)
                srl.isRefreshing = false
            }
        }
    }
}