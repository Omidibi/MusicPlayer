package com.omid.musicplayer.main.ui.albums

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
import com.omid.musicplayer.databinding.FragmentAlbumsBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.AlbumsList
import retrofit2.Call

class AlbumsFragment : Fragment() {

    private lateinit var binding: FragmentAlbumsBinding
    private val webServiceCaller = WebServiceCaller()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        aboutProgressBar()
        albumsList()

        return binding.root
    }

    private fun setupBinding() {
        binding = FragmentAlbumsBinding.inflate(layoutInflater)
        binding.apply {

        }
    }

    private fun aboutProgressBar() {
        binding.apply {
            val wrapDrawable = DrawableCompat.wrap(pbAlbums.indeterminateDrawable)
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(requireContext(), R.color.torchRed))
            pbAlbums.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        }
    }

    private fun albumsList() {
        binding.apply {
            pbAlbums.visibility = View.VISIBLE
            webServiceCaller.getAlbums(object : IListener<AlbumsList>{
                override fun onSuccess(call: Call<AlbumsList>, response: AlbumsList) {
                    Log.e("", "")
                    pbAlbums.visibility = View.GONE
                    rvAlbumsList.visibility = View.VISIBLE
                    rvAlbumsList.adapter = AlbumsListAdapter(response.onlineMp3)
                    rvAlbumsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<AlbumsList>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                    pbAlbums.visibility = View.VISIBLE
                    rvAlbumsList.visibility = View.GONE
                }

            })
        }
    }
}