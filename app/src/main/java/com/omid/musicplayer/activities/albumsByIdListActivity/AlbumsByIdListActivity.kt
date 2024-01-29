package com.omid.musicplayer.activities.albumsByIdListActivity

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.ActivityAlbumsByIdListBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.AlbumByIdList
import com.omid.musicplayer.model.models.AlbumsListMp3
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.util.sendData.MusicPlayer
import com.omid.musicplayer.util.sendData.MusicPlayer.player
import retrofit2.Call
import kotlin.random.Random

class AlbumsByIdListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumsByIdListBinding
    private val webServiceCaller = WebServiceCaller()
    private lateinit var bundle: Bundle
    private var albumsListInfo: AlbumsListMp3? = null
    private lateinit var albumPlayer: ExoPlayer
    private lateinit var albumLatestMp3: LatestMp3
    private lateinit var albumHandler: Handler
    private lateinit var albumRunnable: Runnable
    private lateinit var albumMainList: List<LatestMp3>
    private var albumIsRepeat = false
    private var albumIsRandom = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindingAndInitialize()
        albumsByIdList()
        clickEvent(albumPlayer, albumLatestMp3, albumRunnable, albumHandler)
        setupPlayer(albumLatestMp3, albumIsRepeat, albumIsRandom, albumHandler, albumRunnable, albumPlayer, albumMainList)
        Log.e("", "")
    }

    private fun setupBindingAndInitialize() {
        binding = ActivityAlbumsByIdListBinding.inflate(layoutInflater)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding.apply {
            setContentView(root)
            bundle = intent.extras!!
            albumsListInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("albumsListInfo", AlbumsListMp3::class.java)
            } else {
                intent.getParcelableExtra("albumsListInfo")
            }
            albumUpSlide.nameAlbum.text = albumsListInfo?.albumName
            albumPlayer = MusicPlayer.player!!
            albumHandler = MusicPlayer.handler!!
            albumRunnable = MusicPlayer.runnable!!
            albumLatestMp3 = MusicPlayer.latestMp3!!
            albumMainList = MusicPlayer.mainLatestList!!
            albumIsRepeat = MusicPlayer.isRepeat
            albumIsRandom = MusicPlayer.isRandom

            Log.e("", "")
        }
    }

    private fun albumsByIdList() {
        binding.apply {
            webServiceCaller.getAlbumsById(albumsListInfo?.aid!!, object : IListener<AlbumByIdList> {
                override fun onSuccess(call: Call<AlbumByIdList>, response: AlbumByIdList) {
                    albumUpSlide.rvAlbumsList.adapter = AlbumsByIdAdapter(response.onlineMp3)
                    albumUpSlide.rvAlbumsList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                    Log.e("", "")
                }

                override fun onFailure(call: Call<AlbumByIdList>, t: Throwable, errorResponse: String) {

                }

            })
        }
    }

    private fun clickEvent(player: ExoPlayer, latestMp3: LatestMp3, runnable: Runnable, handler: Handler) {
        binding.apply {
            albumUpSlide.imgBack.setOnClickListener { finish() }

            albumDownSlide.albumPlayPause.setOnClickListener {
                if (!player.isPlaying) {
                    if (player.playbackState == Player.STATE_ENDED) {
                        albumDownSlide.albumShowStart.text = latestMp3.mp3Duration
                        player.setMediaItem(MediaItem.fromUri(Uri.parse(latestMp3.mp3Url)))
                        player.prepare()
                    }
                    player.play()
                    albumDownSlide.albumPlayPause.setImageResource(R.drawable.pause)

                    handler.postDelayed(runnable, 1000)
                } else {
                    player.pause()
                    albumDownSlide.albumPlayPause.setImageResource(R.drawable.play)
                    handler.removeCallbacks(runnable)
                }

            }
        }
    }

    private fun setupPlayer(latestMp3: LatestMp3, isRepeat: Boolean, isRandom: Boolean, handler: Handler, runnable: Runnable, player: ExoPlayer, mainLatestList: List<LatestMp3>) {
          binding.apply {
              Glide.with(applicationContext).load(latestMp3.mp3ThumbnailB).circleCrop()
                  .into(albumDownSlide.albumImgSong)
              albumDownSlide.albumPlayPause.setImageResource(R.drawable.pause)
              albumDownSlide.albumSongName.text = latestMp3.mp3Title
              albumDownSlide.albumSingerName.text = latestMp3.mp3Artist
              player.repeatMode = if (isRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
              player.prepare()
              player.addListener(object : Player.Listener {
                  override fun onPlaybackStateChanged(state: Int) {
                      if (state == Player.STATE_READY) {
                          player.seekTo(MusicPlayer.currentPosition)
                          player.play()
                          albumDownSlide.albumSeekbar.progress = MusicPlayer.seekBarPosition
                          albumDownSlide.albumSeekbar.max = player.duration.toInt()
                          albumDownSlide.albumPlayPause.setImageResource(R.drawable.pause)
                      } else if (state == Player.STATE_ENDED) {
                          if (isRandom) {
                              val randomIndex = Random.nextInt(mainLatestList.size)
                              val randomMp3 = mainLatestList[randomIndex]
                              setupPlayer(
                                  albumLatestMp3,
                                  albumIsRepeat,
                                  albumIsRandom,
                                  albumHandler,
                                  albumRunnable,
                                  albumPlayer,
                                  albumMainList
                              )
                              setupSeekBar(albumHandler, albumRunnable, albumPlayer)

                          }
                          albumDownSlide.albumSeekbar.progress = 0
                          albumDownSlide.albumPlayPause.setImageResource(R.drawable.play)

                          handler.removeCallbacks(runnable)
                          if (isRepeat) {
                              setupPlayer(
                                  albumLatestMp3,
                                  albumIsRepeat,
                                  albumIsRandom,
                                  albumHandler,
                                  albumRunnable,
                                  albumPlayer,
                                  albumMainList
                              )
                              setupSeekBar(albumHandler, albumRunnable, albumPlayer)
                          }

                      }
                  }
              })
          }
      }

    private fun setupSeekBar(handler: Handler, runnable: Runnable, player: ExoPlayer) {
        binding.apply {
            albumDownSlide.albumSeekbar.setOnSeekBarChangeListener(object :
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
            handler.post(Runnable {
                val totalSeconds = player.currentPosition / 1000
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                albumDownSlide.albumShowStart.text = String.format("%02d:%02d", minutes, seconds)
                albumDownSlide.albumSeekbar.progress = player.currentPosition.toInt()
                handler.postDelayed(runnable, 1000)
            })
        }

    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            albumDownSlide.albumSeekbar.progress = MusicPlayer.seekBarPosition
            player?.seekTo(MusicPlayer.currentPosition)
            player?.prepare()
        }
    }

}