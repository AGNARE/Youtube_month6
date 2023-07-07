package com.example.youtube_month6.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.youtube_month6.R
import com.example.youtube_month6.core.base.BaseActivity
import com.example.youtube_month6.core.network.Resource
import com.example.youtube_month6.databinding.ActivityDetailPlaylistBinding
import com.example.youtube_month6.databinding.ActivityPlaylistsBinding
import com.example.youtube_month6.ui.playlist.PlaylistViewModel

class DetailPlaylistActivity : BaseActivity<ActivityDetailPlaylistBinding, DetailViewModel>() {

    private lateinit var adapter: AdapterDetailPlaylist

    override fun inflateViewBinding(): ActivityDetailPlaylistBinding {
        return ActivityDetailPlaylistBinding.inflate(layoutInflater)
    }

    override val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this)[DetailViewModel::class.java]
    }

    override fun setUI() {
        super.setUI()
        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")
        val id = intent.getStringExtra("id")
        val count = intent.getIntExtra("count",0)

        viewModel.playlistItems(id ?: "").observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> adapter.setList(it1.items) }
                    binding.tvTitle.text = title
                    binding.tvDesc.text = desc
                    binding.tvVideos.text = "$count video series"
                }
                Resource.Status.ERROR -> {}
                Resource.Status.LOADING -> {}

            }

    }
    adapter = AdapterDetailPlaylist()
    binding.recyclerView.adapter = adapter
}


}