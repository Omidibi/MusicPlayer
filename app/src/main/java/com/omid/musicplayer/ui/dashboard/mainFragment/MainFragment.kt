package com.omid.musicplayer.ui.dashboard.mainFragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.activity.SharedViewModel
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.FragmentMainBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.BannerModel
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.model.models.LatestSong
import com.omid.musicplayer.model.models.RecentArtistList
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import retrofit2.Call
import java.util.Timer
import java.util.TimerTask

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val webServiceCaller = WebServiceCaller()
    private lateinit var sharedViewModel : SharedViewModel
    private lateinit var newSong: MutableList<LatestMp3>
    private lateinit var specialSong: MutableList<LatestMp3>
    private lateinit var banner: MutableList<BannerModel>
    private var currentPage = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBanner()
        aboutProgressBar()
        latestSongs()
        recentArtist()
        newSongs()
        specialSong()
        slidingUpPanelStatus()
        clickEvents()
    }

    private fun setupBinding() {
        binding = FragmentMainBinding.inflate(layoutInflater)
        requireActivity().requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
       sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
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
                "","","","David Kushner",
                "","03:33",
                "android.resource://com.omid.musicplayer/${R.drawable.david}",
                "android.resource://com.omid.musicplayer/${R.drawable.david}",
                "Daylight","",
                "android.resource://com.omid.musicplayer/${R.raw.david_kushner_daylight}",
                "","","","")

            val syml = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.syml}",
                "android.resource://com.omid.musicplayer/${R.drawable.syml}",
                "","","","Syml",
                "","04:08",
                "android.resource://com.omid.musicplayer/${R.drawable.syml}",
                "android.resource://com.omid.musicplayer/${R.drawable.syml}",
                "Wheres My Love","",
                "android.resource://com.omid.musicplayer/${R.raw.wheres_my_love}",
                "","","","")

            val zach = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.zach}",
                "android.resource://com.omid.musicplayer/${R.drawable.zach}",
                "","","","Zach Bryan",
                "","04:14",
                "android.resource://com.omid.musicplayer/${R.drawable.zach}",
                "android.resource://com.omid.musicplayer/${R.drawable.zach}",
                "Something in The Orange","",
                "android.resource://com.omid.musicplayer/${R.raw.zach_bryan_something_in_the_orange}",
                "","","","")

            val wolves = LatestMp3("",
                "android.resource://com.omid.musicplayer/${R.drawable.wolves}",
                "android.resource://com.omid.musicplayer/${R.drawable.wolves}",
                "","","","Down Like Silver",
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

    private fun aboutProgressBar() {
        binding.apply {
            val wrapDrawable = DrawableCompat.wrap(pbLoading.indeterminateDrawable)
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(requireContext(), R.color.torchRed))
            pbLoading.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        }
    }

    private fun latestSongs() {
        binding.apply {
            pbLoading.visibility = View.VISIBLE
            mainScroll.visibility = View.GONE
            webServiceCaller.getLatestSongs(object : IListener<LatestSong> {
                override fun onSuccess(call: Call<LatestSong>, response: LatestSong) {
                    pbLoading.visibility = View.GONE
                    mainScroll.visibility = View.VISIBLE
                    val adapter = LatestSongsAdapter(requireActivity(), response.onlineMp3, object : IOnSongClickListener {
                        override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                            sharedViewModel.latestMp3.value = latestSongInfo
                            sharedViewModel.latestMp3List.value = latestSongsList
                        }
                    })
                    rvLatestSongs.adapter = adapter
                    rvLatestSongs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }

                override fun onFailure(call: Call<LatestSong>, t: Throwable, errorResponse: String) {
                    pbLoading.visibility = View.VISIBLE
                    mainScroll.visibility = View.GONE
                }
            })
        }
    }

    private fun recentArtist() {
        binding.apply {
            pbLoading.visibility = View.VISIBLE
            mainScroll.visibility = View.GONE
            webServiceCaller.getRecentArtist(object : IListener<RecentArtistList> {
                override fun onSuccess(call: Call<RecentArtistList>, response: RecentArtistList) {
                    pbLoading.visibility = View.GONE
                    mainScroll.visibility = View.VISIBLE
                    rvRecentArtist.adapter = RecentArtistAdapter(this@MainFragment,response.onlineMp3)
                    rvRecentArtist.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }

                override fun onFailure(call: Call<RecentArtistList>, t: Throwable, errorResponse: String) {
                    pbLoading.visibility = View.VISIBLE
                    mainScroll.visibility = View.GONE
                }

            })
        }
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

            moreNewSongs.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_newSongsMoreFragment)
                MainWidgets.bnv.visibility = View.GONE
                MainWidgets.toolbar.visibility = View.GONE
            }

            moreSpecialSongs.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_specialSongsMoreFragment)
                MainWidgets.bnv.visibility = View.GONE
                MainWidgets.toolbar.visibility = View.GONE
            }
        }
    }
}