package com.omid.musicplayer.fragments.albumsByIdListFragment

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
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.databinding.FragmentAlbumsByIdListBinding
import com.omid.musicplayer.fragments.ValuesToPass
import com.omid.musicplayer.model.models.AlbumsListMp3
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class AlbumsByIdListFragment : Fragment() {

    private lateinit var binding: FragmentAlbumsByIdListBinding
    private lateinit var albumsByIdListViewModel: AlbumsByIdListViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBindingAndInitialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarStatus()
        networkAvailable()
        albumsByIdListObservers()
        srlStatus()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBindingAndInitialize() {
        binding = FragmentAlbumsByIdListBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        albumsByIdListViewModel = ViewModelProvider(requireActivity())[AlbumsByIdListViewModel::class.java]
        binding.apply {
            ValuesToPass.albumsListMp3 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireArguments().getParcelable("albumsListInfo", AlbumsListMp3::class.java) !!
            } else {
                requireArguments().getParcelable("albumsListInfo")!!
            }
            nameAlbum.text = ValuesToPass.albumsListMp3.albumName
        }
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbAlbumByIdList)
    }

    private fun networkAvailable(){
        binding.apply {
            if (NetworkAvailable.isNetworkAvailable(requireContext())) {
                pbAlbumByIdList.visibility = View.GONE
                srl.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                pbAlbumByIdList.visibility = View.GONE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun albumsByIdListObservers() {
        binding.apply {
            albumsByIdListViewModel.checkNetworkConnection.observe(viewLifecycleOwner) { isConnected->
                pbAlbumByIdList.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                if (isConnected) {
                    albumsByIdListViewModel.albumByIdList.observe(viewLifecycleOwner) { albumByIdList->
                        pbAlbumByIdList.visibility = View.GONE
                        srl.visibility = View.VISIBLE
                        liveNoConnection.visibility = View.GONE
                        rvAlbumsList.adapter = AlbumsByIdAdapter(albumByIdList.onlineMp3,object : IOnSongClickListener {
                            override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                                sharedViewModel.latestMp3List.value = latestSongsList
                                sharedViewModel.latestMp3.value = latestSongInfo
                            }

                        })
                        rvAlbumsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                } else {
                    pbAlbumByIdList.visibility = View.GONE
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

    private fun srlStatus(){
        binding.apply {
            srl.setOnRefreshListener {
                pbAlbumByIdList.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                albumsByIdListViewModel.getAlbumsById(ValuesToPass.albumsListMp3.aid)
                srl.isRefreshing = false
            }
        }
    }

    private fun clickEvents(){
        binding.apply {

            FragmentsPracticalCodes.backPressed(this@AlbumsByIdListFragment)

            imgBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgetStatus.visible()
            }
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}