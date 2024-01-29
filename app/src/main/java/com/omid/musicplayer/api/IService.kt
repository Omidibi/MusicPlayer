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
import com.omid.musicplayer.model.models.SingleSong
import com.omid.musicplayer.model.models.SongsByCatId
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IService {
    @GET("api.php?latest")
    fun latestSongs(): Call<LatestSong>

    @GET("api.php?recent_artist_list")
    fun recentArtist(): Call<RecentArtistList>

    @GET("api.php?artist_list")
    fun artistsList(): Call<ArtistsList>

    @GET("api.php?playlist")
    fun playLists(): Call<PlayLists>

    @GET("api.php?")
    fun playlistById(@Query("playlist_id") id: String): Call<PlaylistByIdList>

    @GET("api.php?album_list")
    fun albums(): Call<AlbumsList>

    @GET("api.php?")
    fun albumsById(@Query("album_id") id: String): Call<AlbumByIdList>

    @GET("api.php?cat_list")
    fun categories(): Call<CategoriesList>

    @GET("api.php?")
    fun songsListByCatId(@Query("cat_id") id: String): Call<SongsByCatId>

    @GET("api.php?")
    fun singleSong(@Query("mp3_id=14") mp3Id: String, @Query("device_id") deviceId: String): Call<SingleSong>

    @GET("api.php?")
    fun searchSong(@Query("search_text") text: String): Call<SearchSong>
}