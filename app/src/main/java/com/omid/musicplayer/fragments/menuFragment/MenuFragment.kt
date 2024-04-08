package com.omid.musicplayer.fragments.menuFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.omid.musicplayer.R
import com.omid.musicplayer.databinding.FragmentMenuBinding
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus

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

            FragmentsPracticalCodes.backPressed(this@MenuFragment)

            ivBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgetStatus.visible()
            }

            clDownloads.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_downloadsFragment)
            }

            clFavorites.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_favoritesFragment)
            }

            clAbout.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_aboutFragment)
            }

            clShare.setOnClickListener {
                Toast.makeText(requireContext(), "clShare is OK", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}