package com.omid.musicplayer.fragments.menuFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.databinding.FragmentMenuBinding
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var checkNetworkConnection: CheckNetworkConnection

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

            clAbout.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_aboutFragment)
            }

            clShare.setOnClickListener {
                Toast.makeText(requireContext(), "clShare is OK", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun networkAvailable(){
        binding.apply {
            if (NetworkAvailable.isNetworkAvailable(requireContext())) {
                clDownloads.visibility = View.VISIBLE
                clFavorites.visibility = View.VISIBLE
                clAbout.visibility = View.VISIBLE
                clShare.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                clDownloads.visibility = View.GONE
                clFavorites.visibility = View.GONE
                clAbout.visibility = View.GONE
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
                    clAbout.visibility = View.VISIBLE
                    clShare.visibility = View.VISIBLE
                }else {
                    liveNoConnection.visibility = View.VISIBLE
                    clDownloads.visibility = View.GONE
                    clFavorites.visibility = View.GONE
                    clAbout.visibility = View.GONE
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