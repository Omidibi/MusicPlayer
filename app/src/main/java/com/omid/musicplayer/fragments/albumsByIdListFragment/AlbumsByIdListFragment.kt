package com.omid.musicplayer.fragments.albumsByIdListFragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentAlbumsByIdListBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.AlbumByIdList
import com.omid.musicplayer.model.models.AlbumsListMp3
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import retrofit2.Call

class AlbumsByIdListFragment : Fragment() {

    private lateinit var binding: FragmentAlbumsByIdListBinding
    private val webServiceCaller = WebServiceCaller()
    private var albumsListInfo: AlbumsListMp3? = null
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBindingAndInitialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarStatus()
        albumsByIdList()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBindingAndInitialize() {
        binding = FragmentAlbumsByIdListBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        binding.apply {
            albumsListInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireArguments().getParcelable("albumsListInfo", AlbumsListMp3::class.java)
            } else {
                requireArguments().getParcelable("albumsListInfo")
            }
            nameAlbum.text = albumsListInfo?.albumName
        }
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbAlbumByIdList)
    }

    private fun albumsByIdList() {
        binding.apply {
            pbAlbumByIdList.visibility = View.VISIBLE
            rvAlbumsList.visibility = View.GONE
            webServiceCaller.getAlbumsById(albumsListInfo?.aid!!, object : IListener<AlbumByIdList> {
                override fun onSuccess(call: Call<AlbumByIdList>, response: AlbumByIdList) {
                    pbAlbumByIdList.visibility = View.GONE
                    rvAlbumsList.visibility = View.VISIBLE
                   rvAlbumsList.adapter = AlbumsByIdAdapter(response.onlineMp3,object : IOnSongClickListener{
                       override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                           sharedViewModel.latestMp3List.value = latestSongsList
                           sharedViewModel.latestMp3.value = latestSongInfo
                       }

                   })
                   rvAlbumsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<AlbumByIdList>, t: Throwable, errorResponse: String) {

                }

            })
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