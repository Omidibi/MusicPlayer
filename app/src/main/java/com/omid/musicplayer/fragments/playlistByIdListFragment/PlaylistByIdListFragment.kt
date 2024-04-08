package com.omid.musicplayer.fragments.playlistByIdListFragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentPlaylistByIdListBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.model.models.PlayListsMp3
import com.omid.musicplayer.model.models.PlaylistByIdList
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import retrofit2.Call

class PlaylistByIdListFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistByIdListBinding
    private val webServiceCaller = WebServiceCaller()
    private var playlistMp3: PlayListsMp3? = null
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBindingAndInitialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistById()
        clickEvent()
        slidingUpPanelStatus()
    }

    private fun setupBindingAndInitialize() {
        binding = FragmentPlaylistByIdListBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        binding.apply {
            playlistMp3 = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
               requireArguments().getParcelable("playListsInfo", PlayListsMp3::class.java)
            } else {
                requireArguments().getParcelable("playListsInfo")
            }
            namePlaylist.text = playlistMp3?.playlistName
        }
    }

    private fun clickEvent() {
        binding.apply {

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                if (MainWidgets.slidingUpPanel.panelState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }else{
                    findNavController().popBackStack()
                    MainWidgets.bnv.visibility = View.VISIBLE
                    MainWidgets.toolbar.visibility = View.VISIBLE
                }
            }

            imgBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgets.bnv.visibility = View.VISIBLE
                MainWidgets.toolbar.visibility = View.VISIBLE
            }
        }
    }

    private fun playlistById() {
        binding.apply {
            webServiceCaller.getPlaylistById(playlistMp3?.pid!!,object :IListener<PlaylistByIdList>{
                override fun onSuccess(call: Call<PlaylistByIdList>, response: PlaylistByIdList) {
                    Log.e("", "")
                    for (i in 0..<response.onlineMp3.size) {
                        val songs = response.onlineMp3[i].songsList
                        rvPlaylistList.adapter = PlaylistByIdAdapter(songs,object : IOnSongClickListener{
                            override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                                sharedViewModel.latestMp3.value = latestSongInfo
                                sharedViewModel.latestMp3List.value = latestSongsList
                            }

                        })
                    }
                    rvPlaylistList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<PlaylistByIdList>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                }

            })
        }
    }

    private fun slidingUpPanelStatus() {
        MainWidgets.slidingUpPanel.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {

            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                when (newState) {
                    SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                        MainWidgets.bnv.visibility = View.GONE
                    }

                    SlidingUpPanelLayout.PanelState.EXPANDED -> {
                        MainWidgets.bnv.visibility = View.GONE
                    }

                    else -> {

                    }
                }
            }
        })
    }
}