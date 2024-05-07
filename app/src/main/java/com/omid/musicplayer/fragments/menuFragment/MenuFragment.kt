package com.omid.musicplayer.fragments.menuFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.databinding.FragmentMenuBinding
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.share.Share
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var checkNetworkConnection: CheckNetworkConnection
    private lateinit var menuViewModel: MenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkAvailable()
        clickEvent()
        slidingUpPanelStatus()
        observer()
    }

    private fun setupBinding() {
        binding = FragmentMenuBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        menuViewModel = ViewModelProvider(requireActivity())[MenuViewModel::class.java]
        checkNetworkConnection = CheckNetworkConnection(requireActivity().application)
    }

    private fun clickEvent() {
        binding.apply {

            FragmentsPracticalCodes.backPressed(this@MenuFragment)

            ivBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgetStatus.visible()
            }

            clDownloads.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_downloadsFragment)
            }

            clFavorites.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_favoritesFragment)
            }

            clShare.setOnClickListener {
                Share.shareApp(this@MenuFragment)
            }
        }
    }

    private fun networkAvailable(){
        binding.apply {
            if (menuViewModel.checkNetworkAvailable()) {
                clDownloads.visibility = View.VISIBLE
                clFavorites.visibility = View.VISIBLE
                clShare.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                clDownloads.visibility = View.GONE
                clFavorites.visibility = View.GONE
                clShare.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun observer(){
        binding.apply {
            checkNetworkConnection.observe(viewLifecycleOwner) { isConnect->
                if (isConnect) {
                    if (MainWidgets.isPlay) {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }else {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    }
                    liveNoConnection.visibility = View.GONE
                    clDownloads.visibility = View.VISIBLE
                    clFavorites.visibility = View.VISIBLE
                    clShare.visibility = View.VISIBLE
                }else {
                    liveNoConnection.visibility = View.VISIBLE
                    clDownloads.visibility = View.GONE
                    clFavorites.visibility = View.GONE
                    clShare.visibility = View.GONE
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    MainWidgets.playPause.setImageResource(R.drawable.play)
                    MainWidgets.upPlayPause.setImageResource(R.drawable.play)
                    try {
                        MainWidgets.player.pause()
                    }catch (e: UninitializedPropertyAccessException) {
                        Log.e("catch",e.message.toString())
                    }

                }
            }
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}