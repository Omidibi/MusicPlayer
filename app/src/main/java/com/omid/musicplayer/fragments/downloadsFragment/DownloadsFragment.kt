package com.omid.musicplayer.fragments.downloadsFragment

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
import com.omid.musicplayer.databinding.FragmentDownloadsBinding
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class DownloadsFragment : Fragment(), IOnSongClickListener {

    private lateinit var binding: FragmentDownloadsBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var owner: LifecycleOwner
    private lateinit var downloadViewModel: DownloadsViewModel

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
        checkListDownload()
        clickEvent()
        slidingUpPanelStatus()
        observer()
    }

    override fun onResume() {
        super.onResume()
        showDownloadList()
    }

    private fun setupBinding(){
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentDownloadsBinding.inflate(layoutInflater)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        downloadViewModel = ViewModelProvider(this)[DownloadsViewModel::class.java]
    }

    private fun networkAvailable(){
        binding.apply {
            if (downloadViewModel.networkAvailable()) {
                pb.visibility = View.GONE
                rvDownload.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                pb.visibility = View.GONE
                rvDownload.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun checkListDownload(){
        binding.apply {
            if (downloadViewModel.isDownloadEmpty()){
                emptyList.visibility = View.VISIBLE
                rvDownload.visibility = View.GONE
            }else {
                emptyList.visibility = View.GONE
                rvDownload.visibility = View.VISIBLE
            }
        }
    }

    private fun clickEvent() {
        binding.apply {

            FragmentsPracticalCodes.onlyBack(this@DownloadsFragment)

            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun observer(){
        binding.apply {
            downloadViewModel.checkNetworkConnection.observe(owner) { isConnected->
                pb.visibility = View.VISIBLE
                rvDownload.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                if (isConnected) {
                    if (MainWidgets.isPlay) {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }else {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    }
                    pb.visibility = View.GONE
                    rvDownload.visibility = View.VISIBLE
                    liveNoConnection.visibility = View.GONE
                }else {
                    pb.visibility = View.GONE
                    rvDownload.visibility = View.GONE
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

    private fun showDownloadList(){
        binding.apply {
            rvDownload.adapter = DownloadsAdapter(downloadViewModel.showAllDownload(),this@DownloadsFragment)
            rvDownload.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }

    override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
        sharedViewModel.latestMp3List.value = latestSongsList
        sharedViewModel.latestMp3.value = latestSongInfo
    }
}