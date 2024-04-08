package com.omid.musicplayer.ui.dashboard.genres

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentGenresBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.CategoriesList
import com.omid.musicplayer.utils.practicalCodes.DashboardFragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import retrofit2.Call

class GenresFragment : Fragment() {

    private lateinit var binding: FragmentGenresBinding
    private val webServiceCaller = WebServiceCaller()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarStatus()
        categoriesList()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun categoriesList() {
        binding.apply {
            pbGenres.visibility = View.VISIBLE
            webServiceCaller.getCategories(object : IListener<CategoriesList> {
                override fun onSuccess(call: Call<CategoriesList>, response: CategoriesList) {
                    Log.e("", "")
                    pbGenres.visibility = View.GONE
                    rvCat.visibility = View.VISIBLE
                    rvCat.adapter = CatListAdapter(this@GenresFragment,response.onlineMp3)
                    rvCat.layoutManager = GridLayoutManager(requireContext(), 2)
                }

                override fun onFailure(call: Call<CategoriesList>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                    pbGenres.visibility = View.VISIBLE
                    rvCat.visibility = View.GONE
                }

            })
        }
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbGenres)
    }

    private fun setupBinding() {
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentGenresBinding.inflate(layoutInflater)
    }

    private fun slidingUpPanelStatus() {
        DashboardFragmentsPracticalCodes.slidingUpPanelStatus()
    }

    private fun clickEvents(){
        DashboardFragmentsPracticalCodes.backPressed(this@GenresFragment)
    }
}