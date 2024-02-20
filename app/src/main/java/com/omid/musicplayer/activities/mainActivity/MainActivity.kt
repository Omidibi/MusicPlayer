package com.omid.musicplayer.activities.mainActivity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.activities.menuActivity.MenuActivity
import com.omid.musicplayer.activities.searchActivity.SearchActivity
import com.omid.musicplayer.databinding.ActivityMainBinding
import com.omid.musicplayer.main.ui.albums.AlbumsFragment
import com.omid.musicplayer.main.ui.artists.ArtistsFragment
import com.omid.musicplayer.main.ui.genres.GenresFragment
import com.omid.musicplayer.main.ui.home.HomeFragment
import com.omid.musicplayer.main.ui.playLists.PlayListsFragment
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.util.sendData.ISend
import com.omid.musicplayer.util.sendData.ISendToActivity
import com.omid.musicplayer.util.sendData.MusicPlayer
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var allFragmentList: MutableList<Fragment>
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
        fragmentStatusInActivity()
        slidingUpPanelStatusInActivity()
        setupBnv()
        clickEvent()

    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding.apply {
            setContentView(root)
            slidingLayout.panelState = PanelState.HIDDEN
        }
    }

    private fun fragmentStatusInActivity() {
        binding.apply {
            allFragmentList = mutableListOf()
            allFragmentList.add(ArtistsFragment())
            allFragmentList.add(PlayListsFragment())
            allFragmentList.add(HomeFragment(object : ISendToActivity {
                override fun sendSongInfo(latestMp3: LatestMp3, latestSongsList: List<LatestMp3>) {
                    Log.e("", "")
                    mainLatestList = latestSongsList
                    this@MainActivity.latestMp3 = latestMp3
                    setupPlayer(latestMp3)
                    setupSeekBar()
                    MusicPlayer.player = player
                    MusicPlayer.handler = handler
                    MusicPlayer.runnable = runnable
                    MusicPlayer.latestMp3 = latestMp3
                    MusicPlayer.mainLatestList = mainLatestList
                    MusicPlayer.isRepeat = isRepeat
                    MusicPlayer.isRandom = isRandom
                    MusicPlayer.currentPosition = player.currentPosition
                    MusicPlayer.seekBarPosition = downSlide.uiPlayer.seekbar.progress
                    downSlide.uiPlayer.rvListSong.adapter = SongListSlidingUpPanelAdapter(latestSongsList,object : ISend {
                        override fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>) {
                            setupPlayer(latestSongInfo)
                            setupSeekBar()
                            MusicPlayer.player = player
                            MusicPlayer.handler = handler
                            MusicPlayer.runnable = runnable
                            MusicPlayer.latestMp3 = latestSongInfo
                            MusicPlayer.mainLatestList = mainLatestList
                            MusicPlayer.isRepeat = isRepeat
                            MusicPlayer.isRandom = isRandom
                            MusicPlayer.currentPosition = player.currentPosition
                            MusicPlayer.seekBarPosition = downSlide.uiPlayer.seekbar.progress
                            Log.e("","")
                        }

                    })
                    Log.e("","")
                    downSlide.uiPlayer.rvListSong.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
                }
            }))
            allFragmentList.add(AlbumsFragment())
            allFragmentList.add(GenresFragment())
            upSlide.vpFragmentList.adapter = TabAdapter(allFragmentList, this@MainActivity).apply {
                upSlide.vpFragmentList.post { upSlide.vpFragmentList.setCurrentItem(2, false) }
            }

            upSlide.vpFragmentList.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> {
                            bnvMain.menu.findItem(R.id.artists).isChecked = true
                            binding.upSlide.titleToolbar.text = getString(R.string.artists)
                        }

                        1 -> {
                            bnvMain.menu.findItem(R.id.playlists).isChecked = true
                            binding.upSlide.titleToolbar.text = getString(R.string.playlists)
                        }

                        2 -> {
                            bnvMain.menu.findItem(R.id.home).isChecked = true
                            binding.upSlide.titleToolbar.text = getString(R.string.home)
                        }

                        3 -> {
                           bnvMain.menu.findItem(R.id.albums).isChecked = true
                            binding.upSlide.titleToolbar.text = getString(R.string.albums)
                        }

                        4 -> {
                            bnvMain.menu.findItem(R.id.genres).isChecked = true
                            binding.upSlide.titleToolbar.text = getString(R.string.genres)
                        }
                    }
                    upSlide.vpFragmentList.isUserInputEnabled = false
                }
            })
            Log.e("","")
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

            if (slidingLayout.panelState == PanelState.HIDDEN) {
                slidingLayout.panelState = PanelState.COLLAPSED
            }else if (slidingLayout.panelState == PanelState.EXPANDED) {
            slidingLayout.panelState = PanelState.EXPANDED
            }

            downSlide.uiPlayer.singerName.text = latestMp3.mp3Artist
            downSlide.uiPlayer.songName.text = latestMp3.mp3Title
            downSlide.uiPlayer.upSongName.text = latestMp3.mp3Title
            downSlide.uiPlayer.upSingerName.text = latestMp3.mp3Artist
            downSlide.uiPlayer.showStart.text = getString(R.string._00_00)
            downSlide.uiPlayer.upShowStart.text = getString(R.string._00_00)
            Glide.with(applicationContext).load(latestMp3.mp3ThumbnailB).circleCrop().into(downSlide.uiPlayer.imgSong)
            Glide.with(applicationContext).load(latestMp3.mp3ThumbnailB).circleCrop().into(downSlide.uiPlayer.imgUpPlayer)
            downSlide.uiPlayer.upShowEnd.text = latestMp3.mp3Duration
            currentIndex = mainLatestList.indexOf(latestMp3)
            downSlide.uiPlayer.playPause.setImageResource(R.drawable.pause)
            downSlide.uiPlayer.upPlayPause.setImageResource(R.drawable.pause)
            player = ExoPlayer.Builder(applicationContext).build()
            player.volume = currentVolume
            player.repeatMode = if (isRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
            player.setMediaItem(MediaItem.fromUri(Uri.parse(latestMp3.mp3Url)))
            player.prepare()
            player.play()
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
                    MusicPlayer.seekBarPosition = p1
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
                    MusicPlayer.seekBarPosition = p1
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
            slidingLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
                override fun onPanelSlide(panel: View?, slideOffset: Float) {

                }

                override fun onPanelStateChanged(panel: View?, previousState: PanelState?, newState: PanelState?) {
                    when (newState) {
                        PanelState.COLLAPSED -> {
                            bnvMain.visibility = View.VISIBLE
                            downSlide.uiPlayer.llDownPlayer.visibility = View.VISIBLE
                            downSlide.uiPlayer.clUpPlayer.visibility = View.GONE

                        }

                        PanelState.EXPANDED -> {
                            bnvMain.visibility = View.GONE
                            downSlide.uiPlayer.llDownPlayer.visibility = View.GONE
                            downSlide.uiPlayer.clUpPlayer.visibility = View.VISIBLE
                        }

                        else -> {

                        }
                    }
                }

            })
        }
    }

    private fun setupBnv() {
        binding.apply {
            bnvMain.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.artists -> {
                        upSlide.vpFragmentList.currentItem = 0
                    }

                    R.id.playlists -> {
                        upSlide.vpFragmentList.currentItem = 1
                    }

                    R.id.home -> {
                        upSlide.vpFragmentList.currentItem = 2
                    }

                    R.id.albums -> {
                        upSlide.vpFragmentList.currentItem = 3
                    }

                    R.id.genres -> {
                        upSlide.vpFragmentList.currentItem = 4
                    }
                }
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun clickEvent() {
        binding.apply {
            upSlide.menu.setOnClickListener {
                val intent = Intent(applicationContext, MenuActivity::class.java)
                startActivity(intent)
            }

            upSlide.search.setOnClickListener {
                val intent = Intent(applicationContext, SearchActivity::class.java)
                startActivity(intent)

            }

           /* downSlide.uiPlayer.favorite.setOnClickListener {
                Toast.makeText(applicationContext, "Favorite", Toast.LENGTH_SHORT).show()
            }*/

           /* downSlide.uiPlayer.muteLoad.setOnClickListener {
                if (player.volume == 0f) {
                    downSlide.uiPlayer.muteLoad.setImageResource(R.drawable.speaker)
                    player.volume = 1f
                    currentVolume = 1f
                } else {
                    downSlide.uiPlayer.muteLoad.setImageResource(R.drawable.mute)
                    player.volume = 0f
                    currentVolume = 0f
                }
            }*/

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

        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity"))
    override fun onBackPressed() {
        binding.apply {
            if (slidingLayout.panelState == PanelState.EXPANDED){
                slidingLayout.panelState = PanelState.COLLAPSED
            }else {
                super.onBackPressed()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.apply {
            MusicPlayer.currentPosition = player.currentPosition
            MusicPlayer.seekBarPosition = downSlide.uiPlayer.seekbar.progress
        }
    }

}