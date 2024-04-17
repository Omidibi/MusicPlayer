package com.omid.musicplayer.api

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
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
import com.omid.musicplayer.utils.configuration.AppConfiguration
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.SocketException
import java.net.SocketTimeoutException

class WebServiceCaller {

    private val iService = ApiRetrofit.retrofit.create(IService::class.java)
    val albumList = MutableLiveData<AlbumsList>()
    val artistList = MutableLiveData<ArtistsList>()
    val categoriesList = MutableLiveData<CategoriesList>()
    val playLists = MutableLiveData<PlayLists>()

    suspend fun getLatestSongs(): LatestSong? {
        return try {
            return if (iService.latestSongs().isSuccessful) {
                iService.latestSongs().body()
            } else {
                null
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
            null
        }
    }

    suspend fun getRecentArtist(): RecentArtistList? {
        return try {
            return if (iService.recentArtist().isSuccessful) {
                iService.recentArtist().body()
            } else {
                null
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
            null
        }
    }

    fun getArtistsList() {
        try {
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
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
        }
    }

    fun getPlayLists() {
        try {
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
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
        }
    }

    suspend fun getPlaylistById(playlistId: String): PlaylistByIdList? {
        return try {
            return if (iService.playlistById(playlistId).isSuccessful) {
                iService.playlistById(playlistId).body()
            } else {
                null
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
            null
        }
    }

    fun getAlbums() {
        try {
            CompositeDisposable().apply {
                val disposable = iService.albums()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ albumList ->
                        this@WebServiceCaller.albumList.postValue(albumList) }, { error ->
                        Log.e("", error.message.toString())
                    })
                this.add(disposable)
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
        }
    }

    suspend fun getAlbumsById(albumId: String): AlbumByIdList? {
        return try {
            return if (iService.albumsById(albumId).isSuccessful) {
                iService.albumsById(albumId).body()
            } else {
                null
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
            null
        }
    }

    fun getCategories() {
        try {
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
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
        }
    }

    suspend fun getSongsListByCatId(catId: String): SongsByCatId? {
        return try {
            return if (iService.songsListByCatId(catId).isSuccessful) {
                iService.songsListByCatId(catId).body()
            } else {
                null
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
            null
        }
    }

    suspend fun getSongListByArtistName(artistName: String): SongListByArtistName? {
        return try {
            return if (iService.songListByArtistName(artistName).isSuccessful) {
                iService.songListByArtistName(artistName).body()
            } else {
                null
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
            null
        }
    }

    suspend fun getSearchSong(searchText: String): SearchSong? {
        return try {
            return if (iService.searchSong(searchText).isSuccessful) {
                iService.searchSong(searchText).body()
            } else {
                null
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException, is SocketException -> {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(AppConfiguration.getContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> throw e
            }
            null
        }
    }
}