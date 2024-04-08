package com.omid.musicplayer.fragments.songsListByCatIdFragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.omid.musicplayer.databinding.FragmentSongsListByCatIdBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.CategoriesMp3
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.model.models.SongsByCatId
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import retrofit2.Call

class SongsListByCatIdFragment : Fragment() {

    private lateinit var binding: FragmentSongsListByCatIdBinding
    private val webServiceCaller = WebServiceCaller()
    private lateinit var catListInfo: CategoriesMp3
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBindingAndInitialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songsListByCatID()
        clickEvent()
        slidingUpPanelStatus()
    }

    private fun songsListByCatID() {
        binding.apply {
            webServiceCaller.getSongsListByCatId(catListInfo.cid, object : IListener<SongsByCatId> {
                override fun onSuccess(call: Call<SongsByCatId>, response: SongsByCatId) {
                    Log.e("", "")
                    rvSongsListByCatId.adapter = SongsListByCatIdAdapter(response.onlineMp3,object :IOnSongClickListener{
                        override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                           sharedViewModel.latestMp3.value = latestSongInfo
                            sharedViewModel.latestMp3List.value = latestSongsList
                        }

                    })
                    rvSongsListByCatId.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<SongsByCatId>, t: Throwable, errorResponse: String) {
                }

            })
        }
    }

    private fun setupBindingAndInitialize() {
        binding = FragmentSongsListByCatIdBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        binding.apply {
            catListInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireArguments().getParcelable("catListInfo", CategoriesMp3::class.java)!!
            } else {
                requireArguments().getParcelable("catListInfo")!!
            }
            nameCat.text = catListInfo.categoryName
        }
    }

    private fun clickEvent() {
        binding.apply {

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                if (MainWidgets.slidingUpPanel.panelState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }else {
                    findNavController().popBackStack()
                    MainWidgets.bnv.visibility = View.VISIBLE
                    MainWidgets.toolbar.visibility = View.VISIBLE
                }
            }

            imgBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgets.bnv.visibility = View.VISIBLE
                MainWidgets.toolbar.visibility = View.VISIBLE
            }
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
}