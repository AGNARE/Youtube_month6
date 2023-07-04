package com.example.youtube_month6.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube_month6.BuildConfig
import com.example.youtube_month6.core.network.Resource
import com.example.youtube_month6.core.network.RetrofitClient
import com.example.youtube_month6.data.model.PlayListsModel
import com.example.youtube_month6.data.remote.ApiService
import com.example.youtube_month6.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val apiService: ApiService = RetrofitClient.create()

    fun getPlaylistItems(id: String?): LiveData<Resource<PlayListsModel>> {
        val dataItems = MutableLiveData<Resource<PlayListsModel>>()

        dataItems.value = Resource.loading()
        apiService.getPlaylistItems(
            apiKey = BuildConfig.API_KEY,
            part = Constants.PART,
            id = id!!,
        ).enqueue(object : Callback<PlayListsModel> {
            override fun onResponse(
                call: Call<PlayListsModel>,
                response: Response<PlayListsModel>
            ) {
                if (response.isSuccessful) {
                    dataItems.value = Resource.success(response.body())
                }
            }

            override fun onFailure(call: Call<PlayListsModel>, t: Throwable) {
                Resource.error(t.message.toString(), null)
            }
        })
        return dataItems

    }

    fun getPlayLists(): LiveData<Resource<PlayListsModel>> {
        val data = MutableLiveData<Resource<PlayListsModel>>()

        data.value = Resource.loading()
        apiService.getPlaylists(
            part = Constants.PART,
            channelId = Constants.CHANNEL_ID,
            apiKey = BuildConfig.API_KEY,
        ).enqueue(object : Callback<PlayListsModel> {
            override fun onResponse(
                call: Call<PlayListsModel>,
                response: Response<PlayListsModel>
            ) {
                if (response.isSuccessful) {
                    data.value = Resource.success(response.body())
                }
            }

            override fun onFailure(call: Call<PlayListsModel>, t: Throwable) {
                Resource.error(t.message.toString(), null)
            }
        })
        return data
    }
}