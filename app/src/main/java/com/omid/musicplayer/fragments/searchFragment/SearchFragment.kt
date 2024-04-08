package com.omid.musicplayer.fragments.searchFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentSearchBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.SearchSong
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import retrofit2.Call

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val webServiceCaller = WebServiceCaller()
    private var content = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickEvent()
        slidingUpPanelStatus()
    }

    private fun setupBinding() {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
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
                    content = songSearch.text.toString()
                    resultSearch.visibility = View.GONE
                    rvSearchSong.visibility = View.VISIBLE
                    songSearch()
                }

            }
        }
    }

    private fun songSearch() {
        binding.apply {
            webServiceCaller.getSearchSong(content, object : IListener<SearchSong> {
                override fun onSuccess(call: Call<SearchSong>, response: SearchSong) {
                    Log.e("", "")
                    rvSearchSong.adapter = SongSearchAdapter(response.onlineMp3)
                    rvSearchSong.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<SearchSong>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                }

            })
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}