package com.omid.musicplayer.fragments.newSongsMoreFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.databinding.FragmentNewSongsMoreBinding
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class NewSongsMoreFragment : Fragment() {

    private lateinit var binding : FragmentNewSongsMoreBinding
    private lateinit var newSong: MutableList<LatestMp3>
    private lateinit var sharedViewModel : SharedViewModel
    private lateinit var checkNetworkConnection: CheckNetworkConnection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkAvailable()
        newSongsList()
        clickEvents()
        slidingUpPanelStatus()
        observer()
    }

    private fun setupBinding(){
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentNewSongsMoreBinding.inflate(layoutInflater)
        checkNetworkConnection = CheckNetworkConnection(requireActivity().application)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        newSong = mutableListOf()
    }

    private fun networkAvailable(){
        binding.apply {
            if (NetworkAvailable.isNetworkAvailable(requireContext())) {
                rvMoreNew.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                rvMoreNew.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun observer(){
        binding.apply {
            checkNetworkConnection.observe(viewLifecycleOwner) { isConnect->
                if (isConnect) {
                    liveNoConnection.visibility = View.GONE
                    rvMoreNew.visibility = View.VISIBLE
                }else {
                    liveNoConnection.visibility = View.VISIBLE
                    rvMoreNew.visibility = View.GONE
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    try {
                        MainWidgets.player.stop()
                    }catch (e: UninitializedPropertyAccessException) {
                        Log.e("catch",e.message.toString())
                    }

                }

            }
        }
    }

    private fun newSongsList(){
        binding.apply {
            val teddySwims = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.teddy}",
                "android.resource://com.omid.musicplayer/${R.drawable.teddy}",
                "","","","Teddy Swims",
                "","03:30",
                "android.resource://com.omid.musicplayer/${R.drawable.teddy}",
                "android.resource://com.omid.musicplayer/${R.drawable.teddy}",
                "Lose Control","",
                "android.resource://com.omid.musicplayer/${R.raw.teddy_swims_lose_control}",
                "","","","")

            val tamar = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.tamar}",
                "android.resource://com.omid.musicplayer/${R.drawable.tamar}",
                "","","","Tamar Braxton",
                "","03:29",
                "android.resource://com.omid.musicplayer/${R.drawable.tamar}",
                "android.resource://com.omid.musicplayer/${R.drawable.tamar}",
                "Notice Me","",
                "android.resource://com.omid.musicplayer/${R.raw.tamar_braxton_notice_me}",
                "","","","")

            val morgan = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.morgan}",
                "android.resource://com.omid.musicplayer/${R.drawable.morgan}",
                "","","","Morgan Wallen",
                "","02:44",
                "android.resource://com.omid.musicplayer/${R.drawable.morgan}",
                "android.resource://com.omid.musicplayer/${R.drawable.morgan}",
                "Last Night","",
                "android.resource://com.omid.musicplayer/${R.raw.morgan_wallen_last_night}",
                "","","","")

            val jack = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.jack}",
                "android.resource://com.omid.musicplayer/${R.drawable.jack}",
                "","","","Jack Harlow",
                "","02:19",
                "android.resource://com.omid.musicplayer/${R.drawable.jack}",
                "android.resource://com.omid.musicplayer/${R.drawable.jack}",
                "Lovin On Me","",
                "android.resource://com.omid.musicplayer/${R.raw.jack_harlow_lovin_on_me}",
                "","","","")

            val bailey = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.bailey}",
                "android.resource://com.omid.musicplayer/${R.drawable.bailey}",
                "","","","Bailey Zimmerman",
                "","03:49",
                "android.resource://com.omid.musicplayer/${R.drawable.bailey}",
                "android.resource://com.omid.musicplayer/${R.drawable.bailey}",
                "Fall in Love","",
                "android.resource://com.omid.musicplayer/${R.raw.bailey_zimmerman_fall_in_love}",
                "","","","")

            val doja = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.doja}",
                "android.resource://com.omid.musicplayer/${R.drawable.doja}",
                "","","","Doja Cat",
                "","03:51",
                "android.resource://com.omid.musicplayer/${R.drawable.doja}",
                "android.resource://com.omid.musicplayer/${R.drawable.doja}",
                "Paint the Town Red","",
                "android.resource://com.omid.musicplayer/${R.raw.doja_cat_paint_the_town_red}",
                "","","","")

            val jelly = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.jelly}",
                "android.resource://com.omid.musicplayer/${R.drawable.jelly}",
                "","","","Jelly Roll",
                "","03:17",
                "android.resource://com.omid.musicplayer/${R.drawable.jelly}",
                "android.resource://com.omid.musicplayer/${R.drawable.jelly}",
                "Need a Favor","",
                "android.resource://com.omid.musicplayer/${R.raw.jelly_roll_need_a_favor}",
                "","","","")

            val lizzo = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.lizzo}",
                "android.resource://com.omid.musicplayer/${R.drawable.lizzo}",
                "","","","Lizzo",
                "","02:54",
                "android.resource://com.omid.musicplayer/${R.drawable.lizzo}",
                "android.resource://com.omid.musicplayer/${R.drawable.lizzo}",
                "Special","",
                "android.resource://com.omid.musicplayer/${R.raw.lizzo_special}",
                "","","","")

            val luckCombs = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.luck_combs}",
                "android.resource://com.omid.musicplayer/${R.drawable.luck_combs}",
                "","","","Luck Combs",
                "","04:14",
                "android.resource://com.omid.musicplayer/${R.drawable.luck_combs}",
                "android.resource://com.omid.musicplayer/${R.drawable.luck_combs}",
                "Doin this","",
                "android.resource://com.omid.musicplayer/${R.raw.luke_combs_doin_this}",
                "","","","")

            val tom = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.tom}",
                "android.resource://com.omid.musicplayer/${R.drawable.tom}",
                "","","","Tom Mackdonald",
                "","04:05",
                "android.resource://com.omid.musicplayer/${R.drawable.tom}",
                "android.resource://com.omid.musicplayer/${R.drawable.tom}",
                "Names","",
                "android.resource://com.omid.musicplayer/${R.raw.tom_macdonald_names}",
                "","","","")

            newSong.add(teddySwims)
            newSong.add(tamar)
            newSong.add(morgan)
            newSong.add(jack)
            newSong.add(bailey)
            newSong.add(doja)
            newSong.add(jelly)
            newSong.add(lizzo)
            newSong.add(luckCombs)
            newSong.add(tom)
            rvMoreNew.adapter = NewSongsMoreAdapter(newSong,object : IOnSongClickListener {
                override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                    sharedViewModel.latestMp3.value = latestSongInfo
                    sharedViewModel.latestMp3List.value = latestSongsList
                }

            })
            rvMoreNew.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun clickEvents(){
        binding.apply {

            FragmentsPracticalCodes.backPressed(this@NewSongsMoreFragment)

            ivBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgetStatus.visible()
            }
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}