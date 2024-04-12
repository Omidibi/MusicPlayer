package com.omid.musicplayer.fragments.searchFragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.omid.musicplayer.databinding.FragmentSearchBinding
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
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
       // songSearchObservers()
        progressStatus()
        clickEvent()
        slidingUpPanelStatus()
    }

    private fun setupBinding() {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        checkNetworkConnection = CheckNetworkConnection(requireActivity().application)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        owner = this

    }

    private fun networkAvailable() {
        binding.apply {
            if (NetworkAvailable.isNetworkAvailable(requireContext())) {
                pbSearch.visibility = View.GONE
                rvSearchSong.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            } else {
                pbSearch.visibility = View.GONE
                rvSearchSong.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun progressStatus() {
        ProgressBarStatus.pbStatus(binding.pbSearch)
    }

    private fun clickEvent() {
        binding.apply {

            FragmentsPracticalCodes.backPressed(this@SearchFragment)

            ivBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgetStatus.visible()
            }

            clIvClearText.setOnClickListener {
                songSearch.setText("")
            }

            clIvSearch.setOnClickListener {
                if (songSearch.text?.isEmpty() == true) {
                    resultSearch.visibility = View.VISIBLE
                    rvSearchSong.visibility = View.GONE

                } else {
                    val content = songSearch.text.toString()
                    resultSearch.visibility = View.GONE
                    rvSearchSong.visibility = View.VISIBLE
                    searchViewModel.getSearchSong(content)
                }
            }
        }
    }

    /*private fun songSearchObservers() {
        binding.apply {
            checkNetworkConnection.observe(owner) { isConnected ->
                pbSearch.visibility = View.VISIBLE
                rvSearchSong.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                if (isConnected) {
                    searchViewModel.getSearchSong(songSearch.text.toString())
                        .observe(owner) { searchSong ->
                            pbSearch.visibility = View.GONE
                            rvSearchSong.visibility = View.VISIBLE
                            liveNoConnection.visibility = View.GONE
                            rvSearchSong.adapter = SongSearchAdapter(searchSong!!.onlineMp3)
                            rvSearchSong.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        }
                } else {
                    pbSearch.visibility = View.GONE
                    rvSearchSong.visibility = View.GONE
                    liveNoConnection.visibility = View.VISIBLE
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    try {
                        MainWidgets.player.stop()
                    } catch (e: UninitializedPropertyAccessException) {
                        e.message?.let { Log.e("Catch", it) }
                    }
                }
            }
        }
    }*/

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}