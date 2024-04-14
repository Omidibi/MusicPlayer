package com.omid.musicplayer.fragments.specialSongsMoreFragment

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
import com.omid.musicplayer.databinding.FragmentSpecialSongsMoreBinding
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.internetLiveData.CheckNetworkConnection
import com.omid.musicplayer.utils.networkAvailable.NetworkAvailable
import com.omid.musicplayer.utils.practicalCodes.FragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class SpecialSongsMoreFragment : Fragment() {

    private lateinit var binding: FragmentSpecialSongsMoreBinding
    private lateinit var specialSong: MutableList<LatestMp3>
    private lateinit var sharedViewModel : SharedViewModel
    private lateinit var checkNetworkConnection: CheckNetworkConnection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkAvailable()
        specialSong()
        clickEvents()
        slidingUpPanelStatus()
        observer()
    }

    private fun setupBinding(){
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentSpecialSongsMoreBinding.inflate(layoutInflater)
        checkNetworkConnection = CheckNetworkConnection(requireActivity().application)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        specialSong = mutableListOf()
    }

    private fun networkAvailable(){
        binding.apply {
            if (NetworkAvailable.isNetworkAvailable(requireContext())) {
                rvMoreSpecial.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
            }else {
                rvMoreSpecial.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun observer(){
        binding.apply {
            checkNetworkConnection.observe(viewLifecycleOwner) { isConnect->
                if (isConnect) {
                    if (MainWidgets.isPlay) {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }else {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    }
                    liveNoConnection.visibility = View.GONE
                    rvMoreSpecial.visibility = View.VISIBLE
                }else {
                    liveNoConnection.visibility = View.VISIBLE
                    rvMoreSpecial.visibility = View.GONE
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    MainWidgets.playPause.setImageResource(R.drawable.play)
                    MainWidgets.upPlayPause.setImageResource(R.drawable.play)
                    try {
                        MainWidgets.player.pause()
                    }catch (e: UninitializedPropertyAccessException) {
                        Log.e("catch",e.message.toString())
                    }

                }

            }
        }
    }

    private fun clickEvents(){
        binding.apply {

            FragmentsPracticalCodes.backPressed(this@SpecialSongsMoreFragment)

            ivBack.setOnClickListener {
                findNavController().popBackStack()
                MainWidgetStatus.visible()
            }
        }
    }

    private fun specialSong(){
        binding.apply {
            val david = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.david}",
                "android.resource://com.omid.musicplayer/${R.drawable.david}",
                "","","899","David Kushner",
                "","03:33",
                "android.resource://com.omid.musicplayer/${R.drawable.david}",
                "android.resource://com.omid.musicplayer/${R.drawable.david}",
                "Daylight","",
                "android.resource://com.omid.musicplayer/${R.raw.david_kushner_daylight}",
                "","","","")

            val syml = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.syml}",
                "android.resource://com.omid.musicplayer/${R.drawable.syml}",
                "","","898","Syml",
                "","04:08",
                "android.resource://com.omid.musicplayer/${R.drawable.syml}",
                "android.resource://com.omid.musicplayer/${R.drawable.syml}",
                "Wheres My Love","",
                "android.resource://com.omid.musicplayer/${R.raw.wheres_my_love}",
                "","","","")

            val zach = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.zach}",
                "android.resource://com.omid.musicplayer/${R.drawable.zach}",
                "","","897","Zach Bryan",
                "","04:14",
                "android.resource://com.omid.musicplayer/${R.drawable.zach}",
                "android.resource://com.omid.musicplayer/${R.drawable.zach}",
                "Something in The Orange","",
                "android.resource://com.omid.musicplayer/${R.raw.zach_bryan_something_in_the_orange}",
                "","","","")

            val wolves = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.wolves}",
                "android.resource://com.omid.musicplayer/${R.drawable.wolves}",
                "","","896","Down Like Silver",
                "","04:32",
                "android.resource://com.omid.musicplayer/${R.drawable.wolves}",
                "android.resource://com.omid.musicplayer/${R.drawable.wolves}",
                "Wolves","",
                "android.resource://com.omid.musicplayer/${R.raw.down_like_silver_wolves}",
                "","","","")

            val jelly = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.jelly_save_me}",
                "android.resource://com.omid.musicplayer/${R.drawable.jelly_save_me}",
                "","","895","Jelly Roll",
                "","03:57",
                "android.resource://com.omid.musicplayer/${R.drawable.jelly_save_me}",
                "android.resource://com.omid.musicplayer/${R.drawable.jelly_save_me}",
                "Save Me","",
                "android.resource://com.omid.musicplayer/${R.raw.jelly_roll_save_me}",
                "","","","")

            val kane = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.kane}",
                "android.resource://com.omid.musicplayer/${R.drawable.kane}",
                "","","894","Kane Brown",
                "","03:29",
                "android.resource://com.omid.musicplayer/${R.drawable.kane}",
                "android.resource://com.omid.musicplayer/${R.drawable.kane}",
                "Whiskey Sour","",
                "android.resource://com.omid.musicplayer/${R.raw.kane_brown_whiskey_sour}",
                "","","","")

            val libianca = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.libianca}",
                "android.resource://com.omid.musicplayer/${R.drawable.libianca}",
                "","","893","Libianca",
                "","03:05",
                "android.resource://com.omid.musicplayer/${R.drawable.libianca}",
                "android.resource://com.omid.musicplayer/${R.drawable.libianca}",
                "People","",
                "android.resource://com.omid.musicplayer/${R.raw.libianca_people}",
                "","","","")

            val little = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.little}",
                "android.resource://com.omid.musicplayer/${R.drawable.little}",
                "","","892","Little Big Town",
                "","03:15",
                "android.resource://com.omid.musicplayer/${R.drawable.little}",
                "android.resource://com.omid.musicplayer/${R.drawable.little}",
                "Girl Crush","",
                "android.resource://com.omid.musicplayer/${R.raw.little_big_town_girl_crush}",
                "","","","")

            val jason = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.jason}",
                "android.resource://com.omid.musicplayer/${R.drawable.jason}",
                "","","891","Json Aldean",
                "","02:58",
                "android.resource://com.omid.musicplayer/${R.drawable.jason}",
                "android.resource://com.omid.musicplayer/${R.drawable.jason}",
                "Got What I Got","",
                "android.resource://com.omid.musicplayer/${R.raw.jason_aldean_got_what_i_got}",
                "","","","")

            val lewis = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.lewis}",
                "android.resource://com.omid.musicplayer/${R.drawable.lewis}",
                "","","890","Lewis Capaldi",
                "","03:02",
                "android.resource://com.omid.musicplayer/${R.drawable.lewis}",
                "android.resource://com.omid.musicplayer/${R.drawable.lewis}",
                "Someone You Loved","",
                "android.resource://com.omid.musicplayer/${R.raw.lewis_capaldi_someone_you_loved}",
                "","","","")

            specialSong.add(david)
            specialSong.add(syml)
            specialSong.add(zach)
            specialSong.add(wolves)
            specialSong.add(jelly)
            specialSong.add(kane)
            specialSong.add(libianca)
            specialSong.add(little)
            specialSong.add(jason)
            specialSong.add(lewis)
            rvMoreSpecial.adapter = SpecialSongsMoreAdapter(specialSong,object : IOnSongClickListener {
                override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                    sharedViewModel.latestMp3.value = latestSongInfo
                    sharedViewModel.latestMp3List.value = latestSongsList
                }

            })
            rvMoreSpecial.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun slidingUpPanelStatus() {
        FragmentsPracticalCodes.slidingUpPanelStatus()
    }
}