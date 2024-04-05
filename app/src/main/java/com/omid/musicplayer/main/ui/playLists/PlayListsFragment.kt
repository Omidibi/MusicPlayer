package com.omid.musicplayer.main.ui.playLists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.omid.musicplayer.MainWidgets
import com.omid.musicplayer.R
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentPlaylistsBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.PlayLists
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import retrofit2.Call

class PlayListsFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistsBinding
    private val webServiceCaller = WebServiceCaller()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutProgressBar()
        playLists()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        binding = FragmentPlaylistsBinding.inflate(layoutInflater)
    }

    private fun aboutProgressBar() {
        binding.apply {
            val wrapDrawable = DrawableCompat.wrap(pbPlaylist.indeterminateDrawable)
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(requireContext(), R.color.torchRed))
            pbPlaylist.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        }
    }

    private fun playLists() {
        binding.apply {
            pbPlaylist.visibility = View.VISIBLE
            webServiceCaller.getPlayLists(object : IListener<PlayLists> {
                override fun onSuccess(call: Call<PlayLists>, response: PlayLists) {
                    Log.e("", "")
                    pbPlaylist.visibility = View.GONE
                    rvPlaylists.visibility = View.VISIBLE
                    rvPlaylists.adapter = PlayListsAdapter(this@PlayListsFragment,response.onlineMp3)
                    rvPlaylists.layoutManager = GridLayoutManager(requireContext(), 2)
                }

                override fun onFailure(call: Call<PlayLists>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                    pbPlaylist.visibility = View.VISIBLE
                    rvPlaylists.visibility = View.GONE
                }

            })
        }
    }

    private fun slidingUpPanelStatus() {
        MainWidgets.slidingUpPanel.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {

            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                when (newState) {
                    SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                        MainWidgets.bnv.visibility = View.VISIBLE
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

    private fun clickEvents(){
        binding.apply {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                if (MainWidgets.slidingUpPanel.panelState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }
            }
        }
    }
}