package com.omid.musicplayer.fragments.favoritesFragment

import android.content.Context
import android.content.pm.ActivityInfo
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
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.databinding.FragmentFavoritesBinding
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class FavoritesFragment : Fragment(), IOnSongClickListener {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var owner: LifecycleOwner
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkAvailable()
        checkListFavorite()
        clickEvent()
        slidingUpPanelStatus()
        observer()
    }

    override fun onResume() {
        super.onResume()
        showFvtList()
    }

    private fun setupBinding(){
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        favoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
    }

    private fun networkAvailable(){
        binding.apply {
            if (favoritesViewModel.networkAvailable()) {
                pb.visibility = View.GONE
                rvFvt.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                pb.visibility = View.GONE
                rvFvt.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun checkListFavorite(){
        binding.apply {
            if (favoritesViewModel.isFavoriteEmpty()){
                emptyList.visibility = View.VISIBLE
                rvFvt.visibility = View.GONE
            }else {
                emptyList.visibility = View.GONE
                rvFvt.visibility = View.VISIBLE
            }
        }
    }

    private fun clickEvent() {
        binding.apply {

            FragmentsPracticalCodes.onlyBack(this@FavoritesFragment)

            ivBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun observer(){
        binding.apply {
            favoritesViewModel.checkNetworkConnection.observe(owner) { isConnected->
                pb.visibility = View.VISIBLE
                rvFvt.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                if (isConnected) {
                    if (MainWidgets.isPlay) {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }else {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    }
                    pb.visibility = View.GONE
                    rvFvt.visibility = View.VISIBLE
                    liveNoConnection.visibility = View.GONE
                }else {
                    pb.visibility = View.GONE
                    rvFvt.visibility = View.GONE
                    liveNoConnection.visibility = View.VISIBLE
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    MainWidgets.playPause.setImageResource(R.drawable.play)
                    MainWidgets.upPlayPause.setImageResource(R.drawable.play)
                    try {
                        MainWidgets.player.pause()
                    }catch (e: UninitializedPropertyAccessException) {
                        e.message?.let { Log.e("Catch", it) }
                    }
                }
            }
        }
    }

    private fun showFvtList(){
        binding.apply {
            rvFvt.adapter = FavoritesAdapter(favoritesViewModel.showAllFavorite(),this@FavoritesFragment)
            rvFvt.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }

    override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
        sharedViewModel.latestMp3.value = latestSongInfo
        sharedViewModel.latestMp3List.value = latestSongsList
    }
}