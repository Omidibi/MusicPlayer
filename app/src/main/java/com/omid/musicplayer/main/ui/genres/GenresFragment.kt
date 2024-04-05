package com.omid.musicplayer.main.ui.genres

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
import com.omid.musicplayer.databinding.FragmentGenresBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.CategoriesList
import com.sothree.slidinguppanel.SlidingUpPanelLayout
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
        aboutProgressBar()
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

    private fun aboutProgressBar() {
        binding.apply {
            val wrapDrawable = DrawableCompat.wrap(pbGenres.indeterminateDrawable)
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(requireContext(), R.color.torchRed))
            pbGenres.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        }
    }

    private fun setupBinding() {
        binding = FragmentGenresBinding.inflate(layoutInflater)
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