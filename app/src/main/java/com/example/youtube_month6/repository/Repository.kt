package com.example.youtube_month6.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.youtube_month6.core.network.RemoteDataSource
import com.example.youtube_month6.core.network.Resource
import com.example.youtube_month6.core.network.RetrofitClient
import com.example.youtube_month6.data.model.PlayListsModel
import com.example.youtube_month6.data.remote.ApiService
import kotlinx.coroutines.Dispatchers

class Repository {

    private val apiService: ApiService = RetrofitClient.create()
    private val remoteDataSource: RemoteDataSource = RemoteDataSource(apiService)

    fun getPlaylistItems(id: String): LiveData<Resource<PlayListsModel>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val data = remoteDataSource.getPlaylistItems(id)
            emit(data)
        }
    }

    fun getPlayLists(): LiveData<Resource<PlayListsModel>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val data = remoteDataSource.getPlaylists()
            emit(data)
        }
    }
}