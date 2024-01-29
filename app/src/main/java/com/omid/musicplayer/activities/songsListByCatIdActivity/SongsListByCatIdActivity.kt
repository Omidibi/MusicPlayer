package com.omid.musicplayer.activities.songsListByCatIdActivity

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.omid.musicplayer.api.WebServiceCaller
import com.omid.musicplayer.databinding.ActivitySongsListByCatIdBinding
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.CategoriesMp3
import com.omid.musicplayer.model.models.SongsByCatId
import retrofit2.Call

class SongsListByCatIdActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongsListByCatIdBinding
    private val webServiceCaller = WebServiceCaller()
    private lateinit var catListInfo: CategoriesMp3
    private lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindingAndInitialize()
        songsListByCatID()
        clickEvent()

    }

    private fun songsListByCatID() {
        binding.apply {
            webServiceCaller.getSongsListByCatId(catListInfo.cid, object : IListener<SongsByCatId> {
                override fun onSuccess(call: Call<SongsByCatId>, response: SongsByCatId) {
                    Log.e("", "")
                    rvSongsListByCatId.adapter = SongsListByCatIdAdapter(response.onlineMp3)
                    rvSongsListByCatId.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                }

                override fun onFailure(call: Call<SongsByCatId>, t: Throwable, errorResponse: String) {
                    Log.e("", "")
                }

            })
        }
    }

    private fun setupBindingAndInitialize() {
        binding = ActivitySongsListByCatIdBinding.inflate(layoutInflater)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding.apply {
            setContentView(root)
            bundle = intent.extras!!
            catListInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("catListInfo", CategoriesMp3::class.java)!!
            } else {
                intent.getParcelableExtra("catListInfo")!!
            }
            nameCat.text = catListInfo.categoryName
            Log.e("", "")
        }
    }

    private fun clickEvent() {
        binding.apply {
            imgBack.setOnClickListener { finish() }
        }
    }
}