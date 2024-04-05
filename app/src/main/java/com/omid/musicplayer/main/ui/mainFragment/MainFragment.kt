package com.omid.musicplayer.main.ui.mainFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.MainWidgets
import com.omid.musicplayer.R
import com.omid.musicplayer.SharedViewModel
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentMainBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.model.models.LatestSong
import com.omid.musicplayer.model.models.RecentArtistList
import com.omid.musicplayer.util.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import retrofit2.Call

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val webServiceCaller = WebServiceCaller()
    private lateinit var sharedViewModel : SharedViewModel
    private lateinit var newSong: MutableList<LatestMp3>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutProgressBar()
        latestSongs()
        recentArtist()
        newSongs()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        binding = FragmentMainBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
       sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        newSong = mutableListOf()
    }

    private fun newSongs(){
        binding.apply {
            val teddySwims = LatestMp3("",R.drawable.teddy.toString(),R.drawable.teddy.toString(),"","","","Teddy Swims","","03:30",R.drawable.teddy.toString(),R.drawable.teddy.toString(),"Lose Control","",R.raw.teddy_swims_lose_control.toString(),"","","","")
            newSong.add(teddySwims)
            rvNewSongs.adapter = NewSongsAdapter(newSong,object : IOnSongClickListener{
                override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                    sharedViewModel.latestMp3.value = latestSongInfo
                    sharedViewModel.latestMp3List.value = latestSongsList
                }

            })
            rvNewSongs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun aboutProgressBar() {
        binding.apply {
            val wrapDrawable = DrawableCompat.wrap(pbLoading.indeterminateDrawable)
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(requireContext(), R.color.torchRed))
            pbLoading.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        }
    }

    private fun latestSongs() {
        binding.apply {
            pbLoading.visibility = View.VISIBLE
            mainScroll.visibility = View.GONE
            webServiceCaller.getLatestSongs(object : IListener<LatestSong> {
                override fun onSuccess(call: Call<LatestSong>, response: LatestSong) {
                    pbLoading.visibility = View.GONE
                    mainScroll.visibility = View.VISIBLE
                    val adapter = LatestSongsAdapter(requireActivity(), response.onlineMp3, object : IOnSongClickListener {
                        override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                            sharedViewModel.latestMp3.value = latestSongInfo
                            sharedViewModel.latestMp3List.value = latestSongsList
                        }
                    })
                    rvLatestSongs.adapter = adapter
                    rvLatestSongs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }

                override fun onFailure(call: Call<LatestSong>, t: Throwable, errorResponse: String) {
                    pbLoading.visibility = View.VISIBLE
                    mainScroll.visibility = View.GONE
                }
            })
        }
    }

    private fun recentArtist() {
        binding.apply {
            pbLoading.visibility = View.VISIBLE
            mainScroll.visibility = View.GONE
            webServiceCaller.getRecentArtist(object : IListener<RecentArtistList> {
                override fun onSuccess(call: Call<RecentArtistList>, response: RecentArtistList) {
                    pbLoading.visibility = View.GONE
                    mainScroll.visibility = View.VISIBLE
                    rvRecentArtist.adapter = RecentArtistAdapter(response.onlineMp3)
                    rvRecentArtist.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }

                override fun onFailure(call: Call<RecentArtistList>, t: Throwable, errorResponse: String) {
                    pbLoading.visibility = View.VISIBLE
                    mainScroll.visibility = View.GONE
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
                        MainWidgets.bnv.visibility = View.VISIBLE
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
                }
            }
        }
    }
}