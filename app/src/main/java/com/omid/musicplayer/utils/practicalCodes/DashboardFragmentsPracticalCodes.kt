package com.omid.musicplayer.utils.practicalCodes

import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.omid.musicplayer.activity.MainWidgets
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class DashboardFragmentsPracticalCodes {

    companion object {

        fun backPressed(fragment: Fragment){
            fragment.requireActivity().onBackPressedDispatcher.addCallback(fragment.viewLifecycleOwner) {
                if (MainWidgets.slidingUpPanel.panelState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }
            }
        }

        fun slidingUpPanelStatus(){
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
    }
}