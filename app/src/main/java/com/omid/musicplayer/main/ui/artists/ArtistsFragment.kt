package com.omid.musicplayer.main.ui.artists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.R
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentArtistsBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.ArtistsList
import retrofit2.Call

class ArtistsFragment : Fragment() {
    private lateinit var binding: FragmentArtistsBinding
    private val webServiceCaller = WebServiceCaller()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        aboutProgressBar()
        artistsList()

        return binding.root
    }

    private fun setupBinding() {
        binding = FragmentArtistsBinding.inflate(layoutInflater)
        binding.apply {

        }
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
                    rvArtists.adapter = ArtistAdapter(response.onlineMp3)
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
}