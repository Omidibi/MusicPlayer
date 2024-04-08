package com.omid.musicplayer.ui.dashboard.artists

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentArtistsBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.ArtistsList
import com.omid.musicplayer.utils.practicalCodes.DashboardFragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import retrofit2.Call

class ArtistsFragment : Fragment() {

    private lateinit var binding: FragmentArtistsBinding
    private val webServiceCaller = WebServiceCaller()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarStatus()
        artistsList()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentArtistsBinding.inflate(layoutInflater)
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbArtist)
    }

    private fun artistsList() {
        binding.apply {
            pbArtist.visibility = View.VISIBLE
            webServiceCaller.getArtistsList(object : IListener<ArtistsList>{
                override fun onSuccess(call: Call<ArtistsList>, response: ArtistsList) {
                    Log.e("", "")
                    pbArtist.visibility = View.GONE
                    rvArtists.visibility = View.VISIBLE
                    rvArtists.adapter = ArtistAdapter(this@ArtistsFragment,response.onlineMp3)
                    rvArtists.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<ArtistsList>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                    pbArtist.visibility = View.VISIBLE
                    rvArtists.visibility = View.GONE
                }

            })
        }
    }

    private fun slidingUpPanelStatus() {
        DashboardFragmentsPracticalCodes.slidingUpPanelStatus()
    }

    private fun clickEvents(){
        DashboardFragmentsPracticalCodes.backPressed(this@ArtistsFragment)
    }
}