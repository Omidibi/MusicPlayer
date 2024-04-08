package com.omid.musicplayer.ui.dashboard.artists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.R
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentArtistsBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.ArtistsList
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import retrofit2.Call

class ArtistsFragment : Fragment() {

    private lateinit var binding: FragmentArtistsBinding
    private val webServiceCaller = WebServiceCaller()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutProgressBar()
        artistsList()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        binding = FragmentArtistsBinding.inflate(layoutInflater)
    }

    private fun aboutProgressBar() {
        binding.apply {
            val wrapDrawable = DrawableCompat.wrap(pbArtist.indeterminateDrawable)
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(requireContext(), R.color.torchRed))
            pbArtist.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        }
    }

    private fun artistsList() {
        binding.apply {
            pbArtist.visibility = View.VISIBLE
            webServiceCaller.getArtistsList(object : IListener<ArtistsList>{
                override fun onSuccess(call: Call<ArtistsList>, response: ArtistsList) {
                    Log.e("", "")
                    pbArtist.visibility = View.GONE
                    rvArtists.visibility = View.VISIBLE
                    rvArtists.adapter = ArtistAdapter(this@ArtistsFragment,response.onlineMp3)
                    rvArtists.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<ArtistsList>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                    pbArtist.visibility = View.VISIBLE
                    rvArtists.visibility = View.GONE
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