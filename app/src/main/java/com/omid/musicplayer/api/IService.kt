package com.omid.musicplayer.api

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
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IService {

    @GET("api.php?latest")
    suspend fun latestSongs(): Response<LatestSong>

    @GET("api.php?cat_list")
    fun categories(): Observable<CategoriesList>

    @GET("api.php?recent_artist_list")
    suspend fun recentArtist(): Response<RecentArtistList>

    @GET("api.php?artist_list")
    fun artistsList(): Observable<ArtistsList>

    @GET("api.php?album_list")
    fun albums(): Observable<AlbumsList>

    @GET("api.php?")
    suspend fun albumsById(@Query("album_id") id: String): Response<AlbumByIdList>

    @GET("api.php?playlist")
    fun playLists(): Observable<PlayLists>

    @GET("api.php?")
    suspend fun playlistById(@Query("playlist_id") id: String): Response<PlaylistByIdList>

    @GET("api.php?")
    suspend fun songsListByCatId(@Query("cat_id") id: String): Response<SongsByCatId>

    @GET("api.php?")
    suspend fun songListByArtistName(@Query("artist_name") name: String): Response<SongListByArtistName>

    @GET("api.php?")
    suspend fun searchSong(@Query("search_text") text: String): Response<SearchSong>
}