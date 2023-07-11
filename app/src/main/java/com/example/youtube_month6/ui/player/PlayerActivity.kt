package com.example.youtube_month6.ui.player

import android.view.View
import android.widget.Toast
import com.example.youtube_month6.core.base.BaseActivity
import com.example.youtube_month6.core.network.Resource
import com.example.youtube_month6.databinding.ActivityPlayerBinding
import com.example.youtube_month6.internet.ConnectionInternet
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : BaseActivity<ActivityPlayerBinding, PlayerViewModel>() {

    private lateinit var connectionInternet: ConnectionInternet

    override val viewModel: PlayerViewModel by viewModel()

    override fun inflateViewBinding(): ActivityPlayerBinding {
        return ActivityPlayerBinding.inflate(layoutInflater)
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

    override fun setupLiveData() {
        super.setupLiveData()
        val id = intent.getStringExtra("id1")
        val title = intent.getStringExtra("title1")
        val desc = intent.getStringExtra("desc1")
        viewModel.getVideos(id).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 ->
                        binding.tvTitle.text = title
                        binding.tvDesc.text = desc
                    }
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun setUI() {
        super.setUI()
        lifecycle.addObserver(binding.videoView);
        binding.videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                intent.getStringExtra("id1")?.let { youTubePlayer.loadVideo(it, 0f) }
            }
        })
    }
}