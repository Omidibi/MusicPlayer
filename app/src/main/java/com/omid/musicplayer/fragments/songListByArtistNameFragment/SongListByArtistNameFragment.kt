package com.omid.musicplayer.fragments.songListByArtistNameFragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
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
import com.omid.musicplayer.databinding.FragmentSongListByArtistNameBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.ArtistsMp3
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.model.models.SongListByArtistName
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
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

    private fun clickEvents(){
        binding.apply {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                if (MainWidgets.slidingUpPanel.panelState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                } else {
                    findNavController().popBackStack()
                    MainWidgets.bnv.visibility = View.VISIBLE
                    MainWidgets.toolbar.visibility = View.VISIBLE
                }
            }

            ivBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgets.bnv.visibility = View.VISIBLE
                MainWidgets.toolbar.visibility = View.VISIBLE
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