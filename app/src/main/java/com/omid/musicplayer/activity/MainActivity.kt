package com.omid.musicplayer.activity

import android.Manifest
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.omid.musicplayer.db.RoomDBInstance
import com.omid.musicplayer.model.models.DownloadedMp3
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
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
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private var storagePermissions33 = arrayOf(Manifest.permission.READ_MEDIA_AUDIO)
    private var storagePermissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        currentPage()
        bottomNavigationViewStateInActivity()
        clickEvents()
        fragmentStatusInActivity()
        slidingUpPanelStatusInActivity()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        binding.downSlide.uiPlayer.popupMenu.setOnClickListener {
            setupPopupMenu()
        }
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

            /*downSlide.uiPlayer.popupMenu.setOnClickListener {
                setupPopupMenu()
            }*/
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
            Log.e("fuckk", latestMp3.mp3Url)
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
            if (latestMp3.id.toInt() in 890..999){
                popup.menu.findItem(R.id.download).isVisible = false
            }
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.download -> {
                        downloadMusic()
                    }

                    R.id.add_to_favorite -> {
                        if (RoomDBInstance.roomDbInstance.dao().searchById(latestMp3.id).isEmpty()){
                            RoomDBInstance.roomDbInstance.dao().insert(latestMp3)
                            Toast.makeText(applicationContext,"${latestMp3.mp3Title} Added To Favorites List", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(applicationContext,"${latestMp3.mp3Title} There is in Favorites List", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                false
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun downloadMusic() {
        /** روش دانلود برای نسخه 13 به بعد اندروید*/
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, storagePermissions33, 1)
            } else {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val url = URL(latestMp3.mp3Url)
                        val connection = url.openConnection()
                        connection.connect()
                        val inputStream: InputStream = connection.getInputStream()
                        val resolver = applicationContext.contentResolver
                        val contentValues = ContentValues().apply {
                            put(MediaStore.MediaColumns.DISPLAY_NAME, "${latestMp3.mp3Title}.mp3")
                            put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3")
                            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC + "/MusicPlayer")
                        }
                        val uri = resolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues)
                        val outputStream: OutputStream? = resolver.openOutputStream(uri!!)
                        inputStream.copyTo(outputStream!!)
                        outputStream.close()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, getString(R.string.download_completed), Toast.LENGTH_LONG).show()
                        }
                        if (RoomDBInstance.roomDbInstance.dao().searchByIdDownload(latestMp3.id).isEmpty()){
                            RoomDBInstance.roomDbInstance.dao().insertDownload(latestMp3.toDownloadMp3())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        } else {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, storagePermissions, 1)
            } else {
                /** روش دانلود برای نسخه 10 اندروید*/
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                    val request = DownloadManager.Request(Uri.parse(latestMp3.mp3Url))
                        .setTitle(this.title)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${latestMp3.mp3Title}.mp3")
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)
                    val downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    downloadManager.enqueue(request)
                    if (RoomDBInstance.roomDbInstance.dao().searchByIdDownload(latestMp3.id).isEmpty()){
                        RoomDBInstance.roomDbInstance.dao().insertDownload(latestMp3.toDownloadMp3())
                    }
                } else {
                    /** روش دانلود برای دیگر نسخه های اندروید*/
                    val request = DownloadManager.Request(Uri.parse(latestMp3.mp3Url))
                        .setTitle(this.title)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/MusicPlayer/${latestMp3.mp3Title}.mp3")
                    val downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    downloadManager.enqueue(request)
                    if (RoomDBInstance.roomDbInstance.dao().searchByIdDownload(latestMp3.id).isEmpty()){
                        RoomDBInstance.roomDbInstance.dao().insertDownload(latestMp3.toDownloadMp3())
                    }
                }
            }
        }
    }

    private fun LatestMp3.toDownloadMp3(): DownloadedMp3 {
        return DownloadedMp3(catId, categoryImage, categoryImageThumb, categoryName, cid, id, mp3Artist, mp3Description, mp3Duration, mp3ThumbnailB, mp3ThumbnailS, mp3Title, mp3Type, mp3Url, rateAvg, totalDownload, totalRate, totalViews)
    }
}