package com.omid.musicplayer.ui.dashboard.albums

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentAlbumsBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.AlbumsList
import com.omid.musicplayer.utils.practicalCodes.DashboardFragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import retrofit2.Call

class AlbumsFragment : Fragment() {

    private lateinit var binding: FragmentAlbumsBinding
    private val webServiceCaller = WebServiceCaller()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarStatus()
        albumsList()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentAlbumsBinding.inflate(layoutInflater)
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbAlbums)
    }

    private fun albumsList() {
        binding.apply {
            pbAlbums.visibility = View.VISIBLE
            webServiceCaller.getAlbums(object : IListener<AlbumsList>{
                override fun onSuccess(call: Call<AlbumsList>, response: AlbumsList) {
                    Log.e("", "")
                    pbAlbums.visibility = View.GONE
                    rvAlbumsList.visibility = View.VISIBLE
                    rvAlbumsList.adapter =
                        AlbumsListAdapter(
                            this@AlbumsFragment,
                            response.onlineMp3
                        )
                    rvAlbumsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<AlbumsList>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                    pbAlbums.visibility = View.VISIBLE
                    rvAlbumsList.visibility = View.GONE
                }

            })
        }
    }

    private fun slidingUpPanelStatus() {
        DashboardFragmentsPracticalCodes.slidingUpPanelStatus()
    }

    private fun clickEvents(){
        DashboardFragmentsPracticalCodes.backPressed(this@AlbumsFragment)
    }
}