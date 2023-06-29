package com.example.youtube_month6.ui

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.youtube_month6.core.base.BaseActivity
import com.example.youtube_month6.databinding.ActivityPlaylistsBinding
import com.example.youtube_month6.internet.ConnectionInternet
import com.example.youtube_month6.ui.playlistAdapter.AdapterPlayLists

class PlayListsActivity :
    BaseActivity<ActivityPlaylistsBinding, PlaylistViewModel>() {

    override fun inflateViewBinding(): ActivityPlaylistsBinding {
        return ActivityPlaylistsBinding.inflate(layoutInflater)
    }

    private var adapter = AdapterPlayLists()

    override val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this)[PlaylistViewModel::class.java]
    }

    override fun setupLiveData() {
        super.setupLiveData()

        viewModel.playlists().observe(this){
            adapter.setList(it.items)
            binding.recyclerView.adapter = adapter
            Toast.makeText(this, it.kind.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun checkInternet() {
        super.checkInternet()
        ConnectionInternet(application).observe(this){ isConnection ->
            if (isConnection){
                binding.noInternet.visibility = View.GONE
                binding.yesInternet.visibility = View.VISIBLE
            } else{
                binding.noInternet.visibility = View.VISIBLE
                binding.yesInternet.visibility = View.GONE
            }
        }
    }
}