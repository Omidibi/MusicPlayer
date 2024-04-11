package com.omid.musicplayer.api

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
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IService {

    @GET("api.php?latest")
    suspend fun latestSongs(): Response<LatestSong> //***

    @GET("api.php?cat_list")
    fun categories(): Observable<CategoriesList> //***

    @GET("api.php?recent_artist_list")
    suspend fun recentArtist(): Response<RecentArtistList> //***

    @GET("api.php?artist_list")
    fun artistsList(): Observable<ArtistsList> //***

    @GET("api.php?album_list")
    fun albums(): Observable<AlbumsList> //***

    @GET("api.php?")
    suspend fun albumsById(@Query("album_id") id: String): Response<AlbumByIdList> //***

    @GET("api.php?playlist")
    fun playLists(): Observable<PlayLists> //***

    @GET("api.php?")
    suspend fun playlistById(@Query("playlist_id") id: String): Response<PlaylistByIdList> //***

    @GET("api.php?")
    fun songsListByCatId(@Query("cat_id") id: String): Call<SongsByCatId>

    @GET("api.php?")
    fun songListByArtistName(@Query("artist_name") name: String): Call<SongListByArtistName>

    @GET("api.php?")
    fun searchSong(@Query("search_text") text: String): Call<SearchSong>
}