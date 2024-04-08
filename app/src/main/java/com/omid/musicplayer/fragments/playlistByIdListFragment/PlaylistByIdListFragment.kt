package com.omid.musicplayer.fragments.playlistByIdListFragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentPlaylistByIdListBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.model.models.PlayListsMp3
import com.omid.musicplayer.model.models.PlaylistByIdList
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
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
        progressBarStatus()
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

    private fun playlistById() {
        binding.apply {
            pbPlaylistByIdList.visibility = View.VISIBLE
            rvPlaylistList.visibility = View.GONE
            webServiceCaller.getPlaylistById(playlistMp3?.pid!!,object :IListener<PlaylistByIdList>{
                override fun onSuccess(call: Call<PlaylistByIdList>, response: PlaylistByIdList) {
                    pbPlaylistByIdList.visibility = View.GONE
                    rvPlaylistList.visibility = View.VISIBLE
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
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}