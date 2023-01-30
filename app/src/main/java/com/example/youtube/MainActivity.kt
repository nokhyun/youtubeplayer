package com.example.youtube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube.databinding.ActivityMainBinding
import com.example.youtube.databinding.ListYoutubeButtonBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

class MainActivity : YouTubeBaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.rvYoutube.apply {
            adapter = YoutubeAdapter()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}

class YoutubeAdapter : RecyclerView.Adapter<YoutubeViewHolder>() {

    private val lists = arrayOf(
        "MEHF1MU3qyw",
        "YGDl7JBSNKQ"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeViewHolder {
        return YoutubeViewHolder(ListYoutubeButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) {
        holder.bind(lists[position])
    }
}

class YoutubeViewHolder(private val binding: ListYoutubeButtonBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(id: String) {
        log("id: $id")

        binding.run {
            val options = IFramePlayerOptions.Builder().controls(0).build()
            youTubePlayerView.enableAutomaticInitialization = false

            val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    // using pre-made custom ui
                    val defaultPlayerUiController = DefaultPlayerUiController(binding.youTubePlayerView, youTubePlayer)
                    binding.youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                    youTubePlayer.cueVideo(id, 0F)
                }
            }
            youTubePlayerView.initialize(listener, options)
            youTubePlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener {
                override fun onYouTubePlayerEnterFullScreen() {
                    youTubePlayerView.enterFullScreen()
                }

                override fun onYouTubePlayerExitFullScreen() {
                    youTubePlayerView.exitFullScreen()
                }
            })
        }
    }
}

fun log(msg: String) {
    Log.e("LOG.E", msg)
}