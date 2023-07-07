package com.example.youtube_month6.ui.detail

import androidx.lifecycle.LiveData
import com.example.youtube_month6.App.Companion.repository
import com.example.youtube_month6.core.base.BaseViewModel
import com.example.youtube_month6.core.network.Resource
import com.example.youtube_month6.data.model.PlayListsModel

class DetailViewModel: BaseViewModel() {

    fun playlistItems(id: String): LiveData<Resource<PlayListsModel>> {
        return repository.getPlaylistItems(id)
    }
}
