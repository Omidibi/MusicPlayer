package com.omid.musicplayer.activity

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.PopupMenu
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.databinding.ActivityMainBinding
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var player: ExoPlayer
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())
    private var currentVolume = 1f
    private lateinit var latestMp3: LatestMp3
    private lateinit var mainLatestList: List<LatestMp3>
    private var currentIndex = 0
    private var isRandom = false
    private var isRepeat = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        currentPage()
        bottomNavigationViewStateInActivity()
        clickEvents()
        fragmentStatusInActivity()
        slidingUpPanelStatusInActivity()
    }

    private fun setupBinding() {
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            MainWidgets.slidingUpPanel = slidingLayout
            MainWidgets.bnv = bnvMain
            MainWidgets.toolbar = upSlide.mainToolbar
            MainWidgets.playPause = downSlide.uiPlayer.playPause
            MainWidgets.upPlayPause = downSlide.uiPlayer.upPlayPause
            mainLatestList = mutableListOf()
            navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHost.navController
            NavigationUI.setupWithNavController(bnvMain, navController)
            sharedViewModel = ViewModelProvider(this@MainActivity)[SharedViewModel::class.java]
        }
    }

    private fun currentPage(){
        binding.apply {
            navController.navigate(R.id.mainFragment)
            upSlide.titleToolbar.text = applicationContext.resources.getText(R.string.app_name)
            bnvMain.menu.findItem(R.id.home).isChecked = true
            slidingLayout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        }
    }

    private fun bottomNavigationViewStateInActivity(){
        binding.apply {
            bnvMain.setOnItemSelectedListener {
                when (it.itemId) {

                    R.id.artists -> {
                        navController.navigate(R.id.artistsFragment)
                        upSlide.titleToolbar.text = applicationContext.resources.getText(R.string.artists)
                    }

                    R.id.playlists -> {
                        navController.navigate(R.id.playListsFragment)
                        upSlide.titleToolbar.text = applicationContext.resources.getText(R.string.playlists)
                    }

                    R.id.home -> {
                        navController.navigate(R.id.mainFragment)
                        upSlide.titleToolbar.text = applicationContext.resources.getText(R.string.app_name)
                    }

                    R.id.albums -> {
                        navController.navigate(R.id.albumsFragment)
                        upSlide.titleToolbar.text = applicationContext.resources.getText(R.string.albums)
                    }

                    R.id.genres -> {
                        navController.navigate(R.id.genresFragment)
                        upSlide.titleToolbar.text = applicationContext.resources.getText(R.string.genres)
                    }
                }
                true
            }
        }
    }

    private fun clickEvents(){
        binding.apply {

            upSlide.menu.setOnClickListener {
                navHost.findNavController().navigate(R.id.menuFragment)
                MainWidgets.bnv.visibility = View.GONE
                MainWidgets.toolbar.visibility = View.GONE
            }

            upSlide.search.setOnClickListener {
                navHost.findNavController().navigate(R.id.searchFragment)
                MainWidgets.bnv.visibility = View.GONE
                MainWidgets.toolbar.visibility = View.GONE
            }

            downSlide.uiPlayer.skip.setOnClickListener {
                currentIndex++
                if (currentIndex >= mainLatestList.size) {
                    currentIndex = 0
                }
                val nextMp3 = mainLatestList[currentIndex]
                setupPlayer(nextMp3)
                setupSeekBar()
            }

            downSlide.uiPlayer.playPause.setOnClickListener {
                if (!player.isPlaying) {
                    if (player.playbackState == Player.STATE_ENDED) {
                        downSlide.uiPlayer.showStart.text = latestMp3.mp3Duration
                        downSlide.uiPlayer.upShowStart.text = latestMp3.mp3Duration
                        player.setMediaItem(MediaItem.fromUri(Uri.parse(latestMp3.mp3Url)))
                        player.prepare()
                    }
                    player.play()
                    downSlide.uiPlayer.playPause.setImageResource(R.drawable.pause)
                    downSlide.uiPlayer.upPlayPause.setImageResource(R.drawable.pause)
                    handler.postDelayed(runnable, 1000)
                } else {
                    player.pause()
                    downSlide.uiPlayer.playPause.setImageResource(R.drawable.play)
                    downSlide.uiPlayer.upPlayPause.setImageResource(R.drawable.play)
                    handler.removeCallbacks(runnable)
                }
            }

            downSlide.uiPlayer.upPlayPause.setOnClickListener {
                if (!player.isPlaying) {
                    if (player.playbackState == Player.STATE_ENDED) {
                        downSlide.uiPlayer.showStart.text = latestMp3.mp3Duration
                        downSlide.uiPlayer.upShowStart.text = latestMp3.mp3Duration
                        player.setMediaItem(MediaItem.fromUri(Uri.parse(latestMp3.mp3Url)))
                        player.prepare()
                    }
                    player.play()
                    downSlide.uiPlayer.playPause.setImageResource(R.drawable.pause)
                    downSlide.uiPlayer.upPlayPause.setImageResource(R.drawable.pause)
                    handler.postDelayed(runnable, 1000)
                } else {
                    player.pause()
                    downSlide.uiPlayer.upPlayPause.setImageResource(R.drawable.play)
                    downSlide.uiPlayer.playPause.setImageResource(R.drawable.play)
                    handler.removeCallbacks(runnable)
                }
            }

            downSlide.uiPlayer.preview.setOnClickListener {
                currentIndex--
                if (currentIndex < 0) {
                    currentIndex = mainLatestList.size - 1
                }
                val previousMp3 = mainLatestList[currentIndex]
                setupPlayer(previousMp3)
                setupSeekBar()
            }

            downSlide.uiPlayer.random.setOnClickListener {
                isRandom = !isRandom
                if (isRandom) {
                    downSlide.uiPlayer.random.alpha = 1.0f
                } else {
                    downSlide.uiPlayer.random.alpha = 0.5f
                }

            }

            downSlide.uiPlayer.repeat.setOnClickListener {
                if (::player.isInitialized) {
                    if (player.repeatMode == Player.REPEAT_MODE_OFF) {
                        downSlide.uiPlayer.repeat.alpha = 1.0f
                        player.repeatMode = Player.REPEAT_MODE_ONE
                        isRepeat = true
                    } else {
                        downSlide.uiPlayer.repeat.alpha = 0.4f
                        player.repeatMode = Player.REPEAT_MODE_OFF
                        isRepeat = false
                    }
                }
            }

            downSlide.uiPlayer.popupMenu.setOnClickListener {
                setupPopupMenu()
            }
        }
    }

    private fun fragmentStatusInActivity() {
        binding.apply {
            sharedViewModel.latestMp3.observe(this@MainActivity) { latestMp3->
                this@MainActivity.latestMp3 = latestMp3
                setupPlayer(latestMp3)
                setupSeekBar()
            }

            sharedViewModel.latestMp3List.observe(this@MainActivity) { latestMp3List ->
                mainLatestList = latestMp3List
                downSlide.uiPlayer.rvListSong.adapter = SongListSlidingUpPanelAdapter(latestMp3List,object : IOnSongClickListener {
                    override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                        setupPlayer(latestSongInfo)
                        setupSeekBar()
                    }
                })
                downSlide.uiPlayer.rvListSong.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
            }
        }
    }

    private fun setupPlayer(latestMp3: LatestMp3) {
        binding.apply {
            if (::player.isInitialized) {
                currentVolume = player.volume
                player.release()
            }
            if (isRepeat) {
                downSlide.uiPlayer.repeat.alpha = 1.0f
            } else {
                downSlide.uiPlayer.repeat.alpha = 0.4f
            }
            if (isRandom) {
                downSlide.uiPlayer.random.alpha = 1.0f
            } else {
                downSlide.uiPlayer.random.alpha = 0.4f
            }

            if (slidingLayout.panelState == SlidingUpPanelLayout.PanelState.HIDDEN) {
                slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }else if (slidingLayout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slidingLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            }

            downSlide.uiPlayer.singerName.text = latestMp3.mp3Artist
            downSlide.uiPlayer.songName.text = latestMp3.mp3Title
            downSlide.uiPlayer.upSongName.text = latestMp3.mp3Title
            downSlide.uiPlayer.upSingerName.text = latestMp3.mp3Artist
            downSlide.uiPlayer.showStart.text = getString(R.string._00_00)
            downSlide.uiPlayer.upShowStart.text = getString(R.string._00_00)
            Glide.with(applicationContext).load(latestMp3.mp3ThumbnailB).into(downSlide.uiPlayer.imgSong)
            Glide.with(applicationContext).load(latestMp3.mp3ThumbnailB).into(downSlide.uiPlayer.imgUpPlayer)
            downSlide.uiPlayer.upShowEnd.text = latestMp3.mp3Duration
            currentIndex = mainLatestList.indexOf(latestMp3)
            downSlide.uiPlayer.playPause.setImageResource(R.drawable.pause)
            downSlide.uiPlayer.upPlayPause.setImageResource(R.drawable.pause)
            player = ExoPlayer.Builder(applicationContext).build()
            MainWidgets.player = player
            player.volume = currentVolume
            player.repeatMode = if (isRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
            player.setMediaItem(MediaItem.fromUri(Uri.parse(latestMp3.mp3Url)))
            player.prepare()
            player.play()
            MainWidgets.isPlay = true
            downSlide.uiPlayer.seekbar.progress = 0
            downSlide.uiPlayer.upSeekbar.progress = 0
            player.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_READY) {
                        downSlide.uiPlayer.seekbar.max = player.duration.toInt()
                        downSlide.uiPlayer.upSeekbar.max = player.duration.toInt()
                        downSlide.uiPlayer.playPause.setImageResource(R.drawable.pause)
                        downSlide.uiPlayer.upPlayPause.setImageResource(R.drawable.pause)
                        downSlide.uiPlayer.upShowEnd.text = latestMp3.mp3Duration
                    } else if (state == Player.STATE_ENDED) {
                        if (isRandom) {
                            downSlide.uiPlayer.random.alpha = 1.0f
                           val randomIndex = Random.nextInt(mainLatestList.size)
                           val randomMp3 = mainLatestList[randomIndex]
                           setupPlayer(randomMp3)
                           setupSeekBar()

                        }
                        downSlide.uiPlayer.seekbar.progress = 0
                        downSlide.uiPlayer.upSeekbar.progress = 0
                        downSlide.uiPlayer.upShowStart.text = latestMp3.mp3Duration
                        downSlide.uiPlayer.showStart.text = getString(R.string._00_00)
                        downSlide.uiPlayer.upShowStart.text = getString(R.string._00_00)
                        downSlide.uiPlayer.playPause.setImageResource(R.drawable.play)
                        downSlide.uiPlayer.upPlayPause.setImageResource(R.drawable.play)
                        handler.removeCallbacks(runnable)
                        if (isRepeat) {
                            setupPlayer(latestMp3)
                            setupSeekBar()
                        }

                    }
                }
            })

        }
    }

    private fun setupSeekBar() {
        binding.apply {
            downSlide.uiPlayer.seekbar.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2) {
                        player.seekTo(p1.toLong())
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
            downSlide.uiPlayer.upSeekbar.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2) {
                        player.seekTo(p1.toLong())
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
            runnable = Runnable {
                downSlide.uiPlayer.seekbar.progress = player.currentPosition.toInt()
                downSlide.uiPlayer.upSeekbar.progress = player.currentPosition.toInt()
                val totalSeconds = player.currentPosition / 1000
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                downSlide.uiPlayer.showStart.text = String.format("%02d:%02d", minutes, seconds)
                downSlide.uiPlayer.upShowStart.text = String.format("%02d:%02d", minutes, seconds)
                handler.postDelayed(runnable, 1000)
            }
            handler.postDelayed(runnable, 1000)
        }
    }

    private fun slidingUpPanelStatusInActivity() {
        binding.apply {
            MainWidgets.slidingUpPanel.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
                override fun onPanelSlide(panel: View?, slideOffset: Float) {

                }

                override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                    when (newState) {
                        SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                            downSlide.uiPlayer.clDownPlayer.visibility = View.VISIBLE
                            downSlide.uiPlayer.clUpPlayer.visibility = View.GONE
                            downSlide.uiPlayer.rvListSong.visibility = View.GONE
                            downSlide.uiPlayer.clStatus.visibility = View.GONE
                            MainWidgets.bnv.visibility = View.VISIBLE
                        }

                        SlidingUpPanelLayout.PanelState.EXPANDED -> {
                            downSlide.uiPlayer.clDownPlayer.visibility = View.GONE
                            downSlide.uiPlayer.clUpPlayer.visibility = View.VISIBLE
                            downSlide.uiPlayer.rvListSong.visibility = View.VISIBLE
                            downSlide.uiPlayer.clStatus.visibility = View.VISIBLE
                            MainWidgets.bnv.visibility = View.GONE
                        }

                        else -> {

                        }
                    }
                }

            })
        }
    }

    private fun setupPopupMenu() {
        binding.apply {
            val popup = PopupMenu(applicationContext, downSlide.uiPlayer.popupMenu)
            popup.inflate(R.menu.popup_menu)
            popup.show()
            popup.menu.forEach { item ->
                SpannableString(item.title.toString()).apply {
                    this.setSpan(ForegroundColorSpan(Color.BLACK), 0, this.length, 0)
                    item.title = this
                }
            }
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.download -> {

                    }

                    R.id.add_to_favorite -> {

                    }

                }
                false
            }
        }
    }
}