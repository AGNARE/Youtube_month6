package com.example.youtube_month6.ui.playlist

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.youtube_month6.core.base.BaseActivity
import com.example.youtube_month6.core.network.Resource.Status.*
import com.example.youtube_month6.data.model.PlayListsModel
import com.example.youtube_month6.databinding.ActivityPlaylistsBinding
import com.example.youtube_month6.internet.ConnectionInternet
import com.example.youtube_month6.ui.detail.DetailPlaylistActivity

class PlayListsActivity: BaseActivity<ActivityPlaylistsBinding, PlaylistViewModel>() {

    private var adapter = AdapterPlayLists(this::onClick)
    private lateinit var connectionInternet: ConnectionInternet

    override val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this)[PlaylistViewModel::class.java]
    }

    override fun inflateViewBinding() = ActivityPlaylistsBinding.inflate(layoutInflater)

    override fun setupLiveData() {
        super.setupLiveData()

        viewModel.playlists().observe(this) {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { it1 ->
                        adapter.setList(it1.items)
                    }
                    binding.recyclerView.adapter = adapter
                    Toast.makeText(this, it.data?.kind ?: "null", Toast.LENGTH_SHORT).show()
                }

                ERROR -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                LOADING -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun checkInternet() {
        super.checkInternet()
        connectionInternet = ConnectionInternet(this)
        connectionInternet.observe(this) { isConnection ->
            if (isConnection) {
                binding.btnTryAgain.setOnClickListener {
                    binding.noInternet.visibility = View.GONE
                    binding.yesInternet.visibility = View.VISIBLE
                }
            } else {
                binding.noInternet.visibility = View.VISIBLE
                binding.yesInternet.visibility = View.GONE
            }
        }
    }

    private fun onClick(item: PlayListsModel.Item) {
        val intent = Intent(this, DetailPlaylistActivity::class.java)
        intent.putExtra("title", item.snippet.title)
        intent.putExtra("desc", item.snippet.description)
        intent.putExtra("id", item.id)
        intent.putExtra("count", item.contentDetails.itemCount)
        startActivity(intent)
    }

}