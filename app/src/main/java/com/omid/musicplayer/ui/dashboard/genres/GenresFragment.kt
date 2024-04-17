package com.omid.musicplayer.ui.dashboard.genres

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
import androidx.recyclerview.widget.GridLayoutManager
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.databinding.FragmentGenresBinding
import com.omid.musicplayer.utils.practicalCodes.DashboardFragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class GenresFragment : Fragment() {

    private lateinit var binding: FragmentGenresBinding
    private lateinit var owner: LifecycleOwner
    private lateinit var genresViewModel: GenresViewModel

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
        progressBarStatus()
        categoriesListObservers()
        slidingUpPanelStatus()
        srlStatus()
        clickEvents()
    }

    private fun networkAvailable(){
        binding.apply {
            if (genresViewModel.networkAvailable()) {
                pbGenres.visibility = View.GONE
                srl.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                pbGenres.visibility = View.GONE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun categoriesListObservers() {
        binding.apply {
            if (isAdded){
                genresViewModel.checkNetworkConnection.observe(owner) { isConnected->
                    pbGenres.visibility = View.VISIBLE
                    srl.visibility = View.GONE
                    liveNoConnection.visibility = View.GONE
                    if (isConnected) {
                        MainWidgets.bnv.visibility = View.VISIBLE
                        if (MainWidgets.isPlay) {
                            MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                        }else {
                            MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                        }

                        genresViewModel.categoriesList.observe(owner) { categoriesList->
                            pbGenres.visibility = View.GONE
                            srl.visibility = View.VISIBLE
                            liveNoConnection.visibility = View.GONE
                            rvCat.adapter = CatListAdapter(this@GenresFragment,categoriesList.onlineMp3)
                            rvCat.layoutManager = GridLayoutManager(requireContext(), 2)
                        }
                    }else {
                        pbGenres.visibility = View.GONE
                        srl.visibility = View.GONE
                        liveNoConnection.visibility = View.VISIBLE
                        MainWidgets.playPause.setImageResource(R.drawable.play)
                        MainWidgets.upPlayPause.setImageResource(R.drawable.play)
                        MainWidgets.bnv.visibility = View.VISIBLE
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                        try {
                            MainWidgets.player.pause()
                        }catch (e: UninitializedPropertyAccessException) {
                            e.message?.let { Log.e("Catch", it) }
                        }
                    }
                }
            }
        }
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbGenres)
    }

    private fun setupBinding() {
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentGenresBinding.inflate(layoutInflater)
        genresViewModel = ViewModelProvider(this)[GenresViewModel::class.java]
    }

    private fun slidingUpPanelStatus() {
        DashboardFragmentsPracticalCodes.slidingUpPanelStatus()
    }

    private fun srlStatus(){
        binding.apply {
            srl.setOnRefreshListener {
                pbGenres.visibility = View.VISIBLE
                srl.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
                genresViewModel.getCategoriesList()
                srl.isRefreshing = false
            }
        }
    }

    private fun clickEvents(){
        DashboardFragmentsPracticalCodes.backPressed(this@GenresFragment)
    }
}