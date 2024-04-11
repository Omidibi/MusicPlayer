package com.omid.musicplayer.api

import androidx.lifecycle.MutableLiveData
import com.omid.musicplayer.model.listener.IListener
import com.omid.musicplayer.model.models.AlbumByIdList
import com.omid.musicplayer.model.models.AlbumsList
import com.omid.musicplayer.model.models.ArtistsList
import com.omid.musicplayer.model.models.CategoriesList
import com.omid.musicplayer.model.models.LatestSong
import com.omid.musicplayer.model.models.PlayLists
import com.omid.musicplayer.model.models.PlaylistByIdList
import com.omid.musicplayer.model.models.RecentArtistList
import com.omid.musicplayer.model.models.SearchSong
import com.omid.musicplayer.model.models.SongListByArtistName
import com.omid.musicplayer.model.models.SongsByCatId
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        iService.recentArtist().apply { return if (this.isSuccessful) this.body() else null}
    }

    fun getArtistsList() {
        CompositeDisposable().apply {
            val disposable = iService.artistsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ artistList->
                    this@WebServiceCaller.artistList.postValue(artistList)
                },{ error->

                })
                this.add(disposable)
        }
    }

    fun getPlayLists() {
        CompositeDisposable().apply {
            val disposable = iService.playLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ playLists->
                    this@WebServiceCaller.playLists.postValue(playLists)
                },{ error->

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
                .subscribe({ albumList->
                           this@WebServiceCaller.albumList.postValue(albumList)
                },{ error ->

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
                },{ error->

                })
            this.add(disposable)
        }
    }

    fun getSongsListByCatId(catId: String, iListener: IListener<SongsByCatId>) {
        iService.songsListByCatId(catId).enqueue(object : Callback<SongsByCatId> {
            override fun onResponse(call: Call<SongsByCatId>, response: Response<SongsByCatId>) {
                iListener.onSuccess(call, response.body()!!)
            }

            override fun onFailure(call: Call<SongsByCatId>, t: Throwable) {
                iListener.onFailure(call, t, "Error")
            }

        })
    }

    fun getSongListByArtistName(artistName: String, iListener: IListener<SongListByArtistName>) {
        iService.songListByArtistName(artistName).enqueue(object : Callback<SongListByArtistName> {
            override fun onResponse(
                call: Call<SongListByArtistName>,
                response: Response<SongListByArtistName>
            ) {
                iListener.onSuccess(call, response.body()!!)
            }

            override fun onFailure(call: Call<SongListByArtistName>, t: Throwable) {
                iListener.onFailure(call, t, "Error")
            }

        })
    }

    fun getSearchSong(searchText: String, iListener: IListener<SearchSong>) {
        iService.searchSong(searchText).enqueue(object : Callback<SearchSong> {
            override fun onResponse(call: Call<SearchSong>, response: Response<SearchSong>) {
                iListener.onSuccess(call, response.body()!!)
            }

            override fun onFailure(call: Call<SearchSong>, t: Throwable) {
                iListener.onFailure(call, t, "Error")
            }

        })
    }
}