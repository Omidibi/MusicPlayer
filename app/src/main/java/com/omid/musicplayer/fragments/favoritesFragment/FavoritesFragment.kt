package com.omid.musicplayer.fragments.favoritesFragment

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.databinding.FragmentFavoritesBinding
import com.omid.musicplayer.db.RoomDBInstance
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var owner: LifecycleOwner

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
        clickEvent()
        slidingUpPanelStatus()
    }

    override fun onResume() {
        super.onResume()
        showFvtList()
    }

    private fun setupBinding(){
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    private fun clickEvent() {
        binding.apply {

            FragmentsPracticalCodes.onlyBack(this@FavoritesFragment)

            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun showFvtList(){
        binding.apply {
            rvFvt.adapter = FavoritesAdapter(RoomDBInstance.roomDbInstance.dao().showAllLatest(),object : IOnSongClickListener{
                override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                   sharedViewModel.latestMp3.value = latestSongInfo
                    sharedViewModel.latestMp3List.value = latestSongsList
                }

            })

            rvFvt.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}