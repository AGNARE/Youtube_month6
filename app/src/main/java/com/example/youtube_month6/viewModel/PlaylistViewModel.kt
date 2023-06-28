package com.example.youtube_month6.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube_month6.BuildConfig
import com.example.youtube_month6.core.base.BaseViewModel
import com.example.youtube_month6.core.network.RetrofitClient
import com.example.youtube_month6.data.model.PlayListsModel
import com.example.youtube_month6.data.remote.ApiService
import com.example.youtube_month6.utils.Constants
import retrofit2.Call
import retrofit2.Response

class PlaylistViewModel : BaseViewModel() {

    private val apiService: ApiService = RetrofitClient.create()

    fun playlists(): LiveData<PlayListsModel> {
        return getPlaylists()
    }

    private fun getPlaylists(): LiveData<PlayListsModel> {
        val data = MutableLiveData<PlayListsModel>()
        apiService.getPlaylists(BuildConfig.API_KEY, Constants.CHANNEL_ID, Constants.PART)
            .enqueue(object : retrofit2.Callback<PlayListsModel> {
                override fun onResponse(
                    call: Call<PlayListsModel>,
                    response: Response<PlayListsModel>
                ) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PlayListsModel>, t: Throwable) {
                    print(t.stackTrace)
                }
            })
        return data
    }
}