package com.omid.musicplayer.fragments.menuFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.omid.musicplayer.MainWidgets
import com.omid.musicplayer.databinding.FragmentMenuBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

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
        binding = FragmentMenuBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    private fun clickEvent() {
        binding.apply {

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
                if (MainWidgets.slidingUpPanel.panelState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                } else {
                    findNavController().popBackStack()
                    MainWidgets.bnv.visibility = View.VISIBLE
                    MainWidgets.toolbar.visibility = View.VISIBLE
                }
            }

            ivBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgets.bnv.visibility = View.VISIBLE
                MainWidgets.toolbar.visibility = View.VISIBLE
            }

            clVideoArchive.setOnClickListener {
                Toast.makeText(requireContext(), "clVideoArchive is OK", Toast.LENGTH_LONG).show()
            }

            clDownloads.setOnClickListener {
                Toast.makeText(requireContext(), "clDownloads is OK", Toast.LENGTH_LONG).show()
            }

            clFavorites.setOnClickListener {
                Toast.makeText(requireContext(), "clFavorites is OK", Toast.LENGTH_LONG).show()
            }

            clRate.setOnClickListener {
                Toast.makeText(requireContext(), "clRate is OK", Toast.LENGTH_LONG).show()
            }

            clAbout.setOnClickListener {
                Toast.makeText(requireContext(), "clAbout is OK", Toast.LENGTH_LONG).show()
            }

            clPrivacyPolicy.setOnClickListener {
                Toast.makeText(requireContext(), "clPrivacyPolicy is OK", Toast.LENGTH_LONG).show()
            }

            clShare.setOnClickListener {
                Toast.makeText(requireContext(), "clShare is OK", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun slidingUpPanelStatus() {
        MainWidgets.slidingUpPanel.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {

            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                when (newState) {
                    SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                        MainWidgets.bnv.visibility = View.GONE
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
}