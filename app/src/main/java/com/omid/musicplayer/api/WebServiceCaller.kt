package com.omid.musicplayer.api

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebServiceCaller {

    private val iService = ApiRetrofit.retrofit.create(IService::class.java)

    suspend fun getLatestSongs(): LatestSong? {
        iService.latestSongs().apply { return if (this.isSuccessful) this.body() else null }
    }

    suspend fun getRecentArtist(): RecentArtistList? {
        iService.recentArtist().apply { return if (this.isSuccessful) this.body() else null}
    }

    fun getArtistsList(iListener: IListener<ArtistsList>) {
        iService.artistsList().enqueue(object : Callback<ArtistsList> {
            override fun onResponse(call: Call<ArtistsList>, response: Response<ArtistsList>) {
                iListener.onSuccess(call, response.body()!!)
            }

            override fun onFailure(call: Call<ArtistsList>, t: Throwable) {
                iListener.onFailure(call, t, "Error")
            }

        })
    }

    fun getPlayLists(iListener: IListener<PlayLists>) {
        iService.playLists().enqueue(object : Callback<PlayLists> {
            override fun onResponse(call: Call<PlayLists>, response: Response<PlayLists>) {
                iListener.onSuccess(call, response.body()!!)
            }

            override fun onFailure(call: Call<PlayLists>, t: Throwable) {
                iListener.onFailure(call, t, "Error")
            }

        })
    }

    fun getPlaylistById(playlistId: String, iListener: IListener<PlaylistByIdList>) {
        iService.playlistById(playlistId).enqueue(object : Callback<PlaylistByIdList> {
            override fun onResponse(
                call: Call<PlaylistByIdList>,
                response: Response<PlaylistByIdList>
            ) {
                iListener.onSuccess(call, response.body()!!)
            }

            override fun onFailure(call: Call<PlaylistByIdList>, t: Throwable) {
                iListener.onFailure(call, t, "Error")
            }

        })
    }

    fun getAlbums(iListener: IListener<AlbumsList>) {
        iService.albums().enqueue(object : Callback<AlbumsList> {
            override fun onResponse(call: Call<AlbumsList>, response: Response<AlbumsList>) {
                iListener.onSuccess(call, response.body()!!)
            }

            override fun onFailure(call: Call<AlbumsList>, t: Throwable) {
                iListener.onFailure(call, t, "Error")
            }

        })
    }

    fun getAlbumsById(albumId: String, iListener: IListener<AlbumByIdList>) {
        iService.albumsById(albumId).enqueue(object : Callback<AlbumByIdList> {
            override fun onResponse(call: Call<AlbumByIdList>, response: Response<AlbumByIdList>) {
                iListener.onSuccess(call, response.body()!!)
            }

            override fun onFailure(call: Call<AlbumByIdList>, t: Throwable) {
                iListener.onFailure(call, t, "Error")
            }

        })
    }

    fun getCategories(iListener: IListener<CategoriesList>) {
        iService.categories().enqueue(object : Callback<CategoriesList> {
            override fun onResponse(
                call: Call<CategoriesList>,
                response: Response<CategoriesList>
            ) {
                iListener.onSuccess(call, response.body()!!)
            }

            override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                iListener.onFailure(call, t, "Error")
            }

        })
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