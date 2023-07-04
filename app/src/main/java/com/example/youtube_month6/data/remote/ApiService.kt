package com.example.youtube_month6.data.remote

import com.example.youtube_month6.data.model.PlayListsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    fun getPlaylists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 10
    ):Call<PlayListsModel>

    @GET("playlistItems")
    fun getPlaylistItems(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("id") id: String,
        @Query("maxResults") maxResults: Int = 20
    ):Call<PlayListsModel>


}