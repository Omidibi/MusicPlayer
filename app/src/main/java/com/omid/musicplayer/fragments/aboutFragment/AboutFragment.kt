package com.omid.musicplayer.fragments.aboutFragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.databinding.FragmentAboutBinding
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private lateinit var owner: LifecycleOwner
    private lateinit var checkNetworkConnection: CheckNetworkConnection

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
        clickEvent()
        slidingUpPanelStatus()
        observer()
    }

    private fun setupBinding(){
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentAboutBinding.inflate(layoutInflater)
        checkNetworkConnection = CheckNetworkConnection(requireActivity().application)
    }

    private fun networkAvailable(){
        binding.apply {
            if (NetworkAvailable.isNetworkAvailable(requireContext())) {
                liveNoConnection.visibility = View.GONE
            }else {
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun observer(){
        binding.apply {
            checkNetworkConnection.observe(owner) { isConnected->
                if (isConnected) {
                    if (MainWidgets.isPlay) {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }else {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    }
                    liveNoConnection.visibility = View.GONE
                }else {
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

    private fun clickEvent() {
        binding.apply {

            FragmentsPracticalCodes.onlyBack(this@AboutFragment)

            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}