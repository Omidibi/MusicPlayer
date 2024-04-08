package com.omid.musicplayer.fragments.songsListByCatIdFragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentSongsListByCatIdBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.CategoriesMp3
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.model.models.SongsByCatId
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
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
        progressBarStatus()
        songsListByCatID()
        clickEvent()
        slidingUpPanelStatus()
    }

    private fun songsListByCatID() {
        binding.apply {
            pb.visibility = View.VISIBLE
            rvSongsListByCatId.visibility = View.GONE
            webServiceCaller.getSongsListByCatId(catListInfo.cid, object : IListener<SongsByCatId> {
                override fun onSuccess(call: Call<SongsByCatId>, response: SongsByCatId) {
                    pb.visibility = View.GONE
                    rvSongsListByCatId.visibility = View.VISIBLE
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