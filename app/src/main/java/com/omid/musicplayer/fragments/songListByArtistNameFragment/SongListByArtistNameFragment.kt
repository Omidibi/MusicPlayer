package com.omid.musicplayer.fragments.songListByArtistNameFragment

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
import com.omid.musicplayer.databinding.FragmentSongListByArtistNameBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.ArtistsMp3
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.model.models.SongListByArtistName
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import retrofit2.Call

class SongListByArtistNameFragment : Fragment() {

    private lateinit var binding: FragmentSongListByArtistNameBinding
    private lateinit var recentArtist : ArtistsMp3
    private val webServiceCaller = WebServiceCaller()
    private lateinit var sharedViewModel : SharedViewModel

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
        getSongListByArtistName()
    }

    private fun setupBinding(){
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentSongListByArtistNameBinding.inflate(layoutInflater)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    private fun getData(){
        binding.apply {
            recentArtist = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                requireArguments().getParcelable("ArtistInfo",ArtistsMp3::class.java)!!
            }else {
                requireArguments().getParcelable("ArtistInfo")!!
            }
            titleToolbar.text = recentArtist.artistName
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

    private fun getSongListByArtistName(){
        binding.apply {
            pb.visibility = View.VISIBLE
            rvListByArtisName.visibility = View.GONE
            webServiceCaller.getSongListByArtistName(recentArtist.artistName,object : IListener<SongListByArtistName>{
                override fun onSuccess(call: Call<SongListByArtistName>, response: SongListByArtistName) {
                    pb.visibility = View.GONE
                    rvListByArtisName.visibility = View.VISIBLE
                    rvListByArtisName.adapter = SongListByArtistNameAdapter(response.songListByArtistName,object : IOnSongClickListener{
                        override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                            sharedViewModel.latestMp3.value = latestSongInfo
                            sharedViewModel.latestMp3List.value = latestSongsList
                        }

                    })
                    rvListByArtisName.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                }

                override fun onFailure(call: Call<SongListByArtistName>, t: Throwable, errorResponse: String) {

                }

            })
        }
    }
}