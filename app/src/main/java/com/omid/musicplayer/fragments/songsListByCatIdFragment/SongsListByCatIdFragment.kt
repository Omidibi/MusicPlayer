package com.omid.musicplayer.fragments.songsListByCatIdFragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
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
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.databinding.FragmentSongsListByCatIdBinding
import com.omid.musicplayer.model.models.CategoriesMp3
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class SongsListByCatIdFragment : Fragment() {

    private lateinit var binding: FragmentSongsListByCatIdBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var owner: LifecycleOwner
    private lateinit var checkNetworkConnection: CheckNetworkConnection
    private lateinit var songsListByCatIdViewModel: SongsListByCatIdViewModel
    private lateinit var categoriesMp3 : CategoriesMp3

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBindingAndInitialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarStatus()
        networkAvailable()
        songsListByCatIDObservers()
        srlStatus()
        clickEvent()
        slidingUpPanelStatus()
    }

    private fun networkAvailable(){
        binding.apply {
            if (NetworkAvailable.isNetworkAvailable(requireContext())) {
                pb.visibility = View.GONE
                srl.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                pb.visibility = View.GONE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun songsListByCatIDObservers() {
        binding.apply {
            checkNetworkConnection.observe(owner) { isConnected->
                pb.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                if (isConnected) {
                    songsListByCatIdViewModel.getSongsListByCatId(categoriesMp3.cid).observe(owner) { songsByCatId->
                        pb.visibility = View.GONE
                        srl.visibility = View.VISIBLE
                        liveNoConnection.visibility = View.GONE
                        rvSongsListByCatId.adapter = SongsListByCatIdAdapter(songsByCatId!!.onlineMp3,object : IOnSongClickListener {
                            override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                                sharedViewModel.latestMp3.value = latestSongInfo
                                sharedViewModel.latestMp3List.value = latestSongsList
                            }

                        })
                        rvSongsListByCatId.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                }else {
                    pb.visibility = View.GONE
                    srl.visibility = View.GONE
                    liveNoConnection.visibility = View.VISIBLE
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    try {
                        MainWidgets.player.stop()
                    }catch (e: UninitializedPropertyAccessException) {
                        e.message?.let { Log.e("Catch", it) }
                    }
                }
            }
        }
    }

    private fun srlStatus(){
        binding.apply {
            srl.setOnRefreshListener {
                pb.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                songsListByCatIdViewModel.getSongsListByCatId(categoriesMp3.cid)
                srl.isRefreshing = false
            }
        }
    }

    private fun setupBindingAndInitialize() {
        binding = FragmentSongsListByCatIdBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        songsListByCatIdViewModel = ViewModelProvider(this)[SongsListByCatIdViewModel::class.java]
        checkNetworkConnection = CheckNetworkConnection(requireActivity().application)
        binding.apply {
            categoriesMp3 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireArguments().getParcelable("catListInfo", CategoriesMp3::class.java)!!
            } else {
                requireArguments().getParcelable("catListInfo")!!
            }
            nameCat.text = categoriesMp3.categoryName
        }
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pb)
    }

    private fun clickEvent() {
        binding.apply {

            FragmentsPracticalCodes.backPressed(this@SongsListByCatIdFragment)

            imgBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgetStatus.visible()
            }
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}