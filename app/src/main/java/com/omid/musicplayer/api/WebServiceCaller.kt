package com.omid.musicplayer.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.model.AlbumByIdList
import com.omid.musicplayer.model.AlbumsList
import com.omid.musicplayer.model.ArtistsList
import com.omid.musicplayer.model.CategoriesList
import com.omid.musicplayer.model.LatestSong
import com.omid.musicplayer.model.PlayLists
import com.omid.musicplayer.model.PlaylistByIdList
import com.omid.musicplayer.model.RecentArtistList
import com.omid.musicplayer.model.SearchSong
import com.omid.musicplayer.model.SongListByArtistName
import com.omid.musicplayer.model.SongsByCatId
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WebServiceCaller {

    private val iService = ApiRetrofit.retrofit.create(IService::class.java)
    val albumList = MutableLiveData<AlbumsList>()
    val artistList = MutableLiveData<ArtistsList>()
    val categoriesList = MutableLiveData<CategoriesList>()
    val playLists = MutableLiveData<PlayLists>()

    suspend fun getLatestSongs(): LatestSong? {
        iService.latestSongs().apply { return if (this.isSuccessful) this.body() else null }
    }

    suspend fun getRecentArtist(): RecentArtistList? {
        iService.recentArtist().apply { return if (this.isSuccessful) this.body() else null }
    }

    fun getArtistsList() {
        CompositeDisposable().apply {
            val disposable = iService.artistsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ artistList ->
                    this@WebServiceCaller.artistList.postValue(artistList)
                }, { error ->
                    Log.e("", error.message.toString())
                })
            this.add(disposable)
        }
    }

    fun getPlayLists() {
        CompositeDisposable().apply {
            val disposable = iService.playLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ playLists ->
                    this@WebServiceCaller.playLists.postValue(playLists)
                }, { error ->
                    Log.e("", error.message.toString())
                })
            this.add(disposable)
        }
    }

    suspend fun getPlaylistById(playlistId: String): PlaylistByIdList? {
        iService.playlistById(playlistId).apply { return if (this.isSuccessful) this.body() else null }
    }

    fun getAlbums() {
        CompositeDisposable().apply {
            val disposable = iService.albums()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ albumList ->
                    this@WebServiceCaller.albumList.postValue(albumList)
                }, { error ->
                    Log.e("", error.message.toString())
                })
            this.add(disposable)
        }
    }

    suspend fun getAlbumsById(albumId: String): AlbumByIdList? {
        iService.albumsById(albumId).apply { return if (this.isSuccessful) this.body() else null }
    }

    fun getCategories() {
        CompositeDisposable().apply {
            val disposable = iService.categories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ categoriesList ->
                    this@WebServiceCaller.categoriesList.postValue(categoriesList)
                }, { error ->
                    Log.e("", error.message.toString())
                })
            this.add(disposable)
        }
    }

    suspend fun getSongsListByCatId(catId: String): SongsByCatId? {
        iService.songsListByCatId(catId).apply { return if (this.isSuccessful) this.body() else null }
    }

    suspend fun getSongListByArtistName(artistName: String): SongListByArtistName? {
        iService.songListByArtistName(artistName).apply { return if (this.isSuccessful) this.body() else null }
    }

    suspend fun getSearchSong(searchText: String): SearchSong? {
        iService.searchSong(searchText).apply { return if (this.isSuccessful) this.body() else null }
    }
}