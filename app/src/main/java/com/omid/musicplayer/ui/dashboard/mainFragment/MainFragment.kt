package com.omid.musicplayer.ui.dashboard.mainFragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.databinding.FragmentMainBinding
import com.omid.musicplayer.model.BannerModel
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.practicalCodes.DashboardFragmentsPracticalCodes
import com.omid.musicplayer.utils.practicalCodes.MainWidgetStatus
import com.omid.musicplayer.utils.practicalCodes.ProgressBarStatus
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.util.Timer
import java.util.TimerTask

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var owner: LifecycleOwner
    private lateinit var sharedViewModel : SharedViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var newSong: MutableList<LatestMp3>
    private lateinit var specialSong: MutableList<LatestMp3>
    private lateinit var banner: MutableList<BannerModel>
    private var currentPage = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetwork()
        setupBanner()
        progressBarStatus()
        mainObservers()
        srlStatus()
        newSongs()
        specialSong()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = FragmentMainBinding.inflate(layoutInflater)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        newSong = mutableListOf()
        specialSong = mutableListOf()
        banner = mutableListOf()
    }

    private fun setupBanner(){
        binding.apply {
            setupPagerBanner()
            val teddy = BannerModel("android.resource://com.omid.musicplayer/${R.drawable.teddy}")
            val morgan = BannerModel("android.resource://com.omid.musicplayer/${R.drawable.morgan}")
            val jack = BannerModel("android.resource://com.omid.musicplayer/${R.drawable.jack}")
            val syml = BannerModel("android.resource://com.omid.musicplayer/${R.drawable.syml}")
            val zach = BannerModel("android.resource://com.omid.musicplayer/${R.drawable.zach}")
            val david = BannerModel("android.resource://com.omid.musicplayer/${R.drawable.david}")
            banner.add(teddy)
            banner.add(morgan)
            banner.add(jack)
            banner.add(syml)
            banner.add(zach)
            banner.add(david)
            vpBanner.adapter = BannerAdapter(banner)
        }
    }

    private fun setupPagerBanner() {
        binding.apply {
            val handler = Handler(Looper.getMainLooper())
            val update = Runnable {
                if (currentPage == (vpBanner.adapter?.count ?: 0)) {
                    currentPage = 0
                }
                vpBanner.setCurrentItem(currentPage++, true)
            }
            Timer().schedule(object : TimerTask() {
                override fun run() { handler.post(update) }
            }, 3000, 3000)

            vpBanner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    currentPage = position
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
            pageIndicatorView.elevation = 1000f
        }
    }

    private fun newSongs(){
        binding.apply {
            val teddySwims = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.teddy}",
                "android.resource://com.omid.musicplayer/${R.drawable.teddy}",
                "","","999","Teddy Swims",
                "","03:30",
                "android.resource://com.omid.musicplayer/${R.drawable.teddy}",
                "android.resource://com.omid.musicplayer/${R.drawable.teddy}",
                "Lose Control","",
                "android.resource://com.omid.musicplayer/${R.raw.teddy_swims_lose_control}",
                "","","","")

            val tamar = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.tamar}",
                "android.resource://com.omid.musicplayer/${R.drawable.tamar}",
                "","","998","Tamar Braxton",
                "","03:29",
                "android.resource://com.omid.musicplayer/${R.drawable.tamar}",
                "android.resource://com.omid.musicplayer/${R.drawable.tamar}",
                "Notice Me","",
                "android.resource://com.omid.musicplayer/${R.raw.tamar_braxton_notice_me}",
                "","","","")

            val morgan = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.morgan}",
                "android.resource://com.omid.musicplayer/${R.drawable.morgan}",
                "","","997","Morgan Wallen",
                "","02:44",
                "android.resource://com.omid.musicplayer/${R.drawable.morgan}",
                "android.resource://com.omid.musicplayer/${R.drawable.morgan}",
                "Last Night","",
                "android.resource://com.omid.musicplayer/${R.raw.morgan_wallen_last_night}",
                "","","","")

            val jack = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.jack}",
                "android.resource://com.omid.musicplayer/${R.drawable.jack}",
                "","","996","Jack Harlow",
                "","02:19",
                "android.resource://com.omid.musicplayer/${R.drawable.jack}",
                "android.resource://com.omid.musicplayer/${R.drawable.jack}",
                "Lovin On Me","",
                "android.resource://com.omid.musicplayer/${R.raw.jack_harlow_lovin_on_me}",
                "","","","")

            newSong.add(teddySwims)
            newSong.add(tamar)
            newSong.add(morgan)
            newSong.add(jack)
            rvNewSongs.adapter = NewSongsAdapter(newSong,object : IOnSongClickListener{
                override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                    sharedViewModel.latestMp3.value = latestSongInfo
                    sharedViewModel.latestMp3List.value = latestSongsList
                }

            })
            rvNewSongs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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

            specialSong.add(david)
            specialSong.add(syml)
            specialSong.add(zach)
            specialSong.add(wolves)
            rvSpecialSongs.adapter = NewSongsAdapter(specialSong,object : IOnSongClickListener{
                override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                    sharedViewModel.latestMp3.value = latestSongInfo
                    sharedViewModel.latestMp3List.value = latestSongsList
                }

            })
            rvSpecialSongs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun progressBarStatus() {
        ProgressBarStatus.pbStatus(binding.pbLoading)
    }

    private fun checkNetwork(){
        binding.apply {
            if (mainViewModel.networkAvailable()) {
                srl.visibility = View.VISIBLE
                pbLoading.visibility = View.GONE
                liveNoConnection.visibility = View.GONE
            } else {
                srl.visibility = View.GONE
                pbLoading.visibility = View.GONE
                liveNoConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun mainObservers(){
        binding.apply {
            mainViewModel.checkNetworkConnection.observe(owner) { isConnected->
                srl.visibility = View.GONE
                pbLoading.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
                if (isConnected) {
                    MainWidgets.bnv.visibility = View.VISIBLE
                    if (MainWidgets.isPlay) {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }else {
                        MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    }

                    mainViewModel.latestSong.observe(owner) { latestSong ->
                        srl.visibility = View.VISIBLE
                        pbLoading.visibility = View.GONE
                        liveNoConnection.visibility = View.GONE
                        val adapter = LatestSongsAdapter(requireActivity(), latestSong.onlineMp3, object : IOnSongClickListener {
                            override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                                sharedViewModel.latestMp3.value = latestSongInfo
                                sharedViewModel.latestMp3List.value = latestSongsList
                            }
                        })
                        rvLatestSongs.adapter = adapter
                        rvLatestSongs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    }
                    mainViewModel.recentArtistList.observe(owner) { recentArtistList ->
                        srl.visibility = View.VISIBLE
                        pbLoading.visibility = View.GONE
                        liveNoConnection.visibility = View.GONE
                        rvRecentArtist.adapter = RecentArtistAdapter(this@MainFragment,recentArtistList.onlineMp3)
                        rvRecentArtist.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    }
                }else {
                    srl.visibility = View.GONE
                    pbLoading.visibility = View.GONE
                    liveNoConnection.visibility = View.VISIBLE
                    MainWidgets.slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    MainWidgets.playPause.setImageResource(R.drawable.play)
                    MainWidgets.upPlayPause.setImageResource(R.drawable.play)
                    MainWidgets.bnv.visibility = View.VISIBLE
                    currentPage = 0
                    try {
                        MainWidgets.player.pause()
                    }catch (e: UninitializedPropertyAccessException) {
                        e.message?.let { Log.e("Catch", it) }
                    }
                }
            }
        }
    }

    private fun srlStatus(){
        binding.apply {
            srl.setOnRefreshListener {
                pbLoading.visibility = View.VISIBLE
                liveNoConnection.visibility = View.GONE
                srl.visibility = View.GONE
                mainViewModel.getLatestSongs()
                mainViewModel.getRecentArtist()
                currentPage = 0
                srl.isRefreshing = false
            }
        }
    }

    private fun slidingUpPanelStatus() {
        DashboardFragmentsPracticalCodes.slidingUpPanelStatus()
    }

    private fun clickEvents(){
        binding.apply {

            DashboardFragmentsPracticalCodes.backPressed(this@MainFragment)

            moreNewSongs.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_newSongsMoreFragment)
                MainWidgetStatus.gone()
            }

            moreSpecialSongs.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_specialSongsMoreFragment)
                MainWidgetStatus.gone()
            }
        }
    }
}