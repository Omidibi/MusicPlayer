package com.omid.musicplayer.fragments.playlistByIdListFragment

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
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.databinding.FragmentPlaylistByIdListBinding
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.model.models.PlayListsMp3
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class PlaylistByIdListFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistByIdListBinding
    private lateinit var owner: LifecycleOwner
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var checkNetworkConnection: CheckNetworkConnection
    private lateinit var playlistByIdListViewModel: PlaylistByIdListViewModel
    private lateinit var playListsMp3 : PlayListsMp3

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBindingAndInitialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarStatus()
        networkAvailable()
        playlistByIdObservers()
        srlStatus()
        clickEvent()
        slidingUpPanelStatus()
    }

    private fun setupBindingAndInitialize() {
        binding = FragmentPlaylistByIdListBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        playlistByIdListViewModel = ViewModelProvider(this)[PlaylistByIdListViewModel::class.java]
        checkNetworkConnection = CheckNetworkConnection(requireActivity().application)
        binding.apply {
            playListsMp3 = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
               requireArguments().getParcelable("playListsInfo", PlayListsMp3::class.java)!!
            } else {
                requireArguments().getParcelable("playListsInfo")!!
            }
            namePlaylist.text = playListsMp3.playlistName
        }
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbPlaylistByIdList)
    }

    private fun clickEvent() {
        binding.apply {

            FragmentsPracticalCodes.backPressed(this@PlaylistByIdListFragment)

            imgBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgetStatus.visible()
            }
        }
    }

    private fun srlStatus(){
        binding.apply {
            srl.setOnRefreshListener {
                pbPlaylistByIdList.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                playlistByIdListViewModel.getPlaylistById(playListsMp3.pid)
                srl.isRefreshing = false
            }
        }
    }

    private fun playlistByIdObservers() {
        binding.apply {
            checkNetworkConnection.observe(owner) { isConnected->
                pbPlaylistByIdList.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                if (isConnected) {
                    playlistByIdListViewModel.getPlaylistById(playListsMp3.pid).observe(owner) { playListByIdList->
                        pbPlaylistByIdList.visibility = View.GONE
                        srl.visibility = View.VISIBLE
                        liveNoConnection.visibility = View.GONE
                        for (i in 0..<playListByIdList?.onlineMp3!!.size) {
                            val songs = playListByIdList.onlineMp3[i].songsList
                            rvPlaylistList.adapter = PlaylistByIdAdapter(songs,object : IOnSongClickListener {
                                override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                                    sharedViewModel.latestMp3.value = latestSongInfo
                                    sharedViewModel.latestMp3List.value = latestSongsList
                                }

                            })
                        }
                        rvPlaylistList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                }else {
                    pbPlaylistByIdList.visibility = View.GONE
                    srl.visibility = View.GONE
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

    private fun networkAvailable(){
        binding.apply {
            if (NetworkAvailable.isNetworkAvailable(requireContext())) {
                pbPlaylistByIdList.visibility = View.GONE
                srl.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                pbPlaylistByIdList.visibility = View.GONE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}