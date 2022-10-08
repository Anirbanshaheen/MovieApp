package com.example.movieapp.videoStreamPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentVideoStreamBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.MimeTypes

class VideoStreamFragment : Fragment(), Player.Listener {

    private var binding: FragmentVideoStreamBinding? = null
    private lateinit var player: ExoPlayer

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentVideoStreamBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPlayer(savedInstanceState) // video Player initialization
    }

    private fun setupPlayer(savedInstanceState: Bundle?) {
        player = ExoPlayer.Builder(requireContext()).build()
        binding?.videoView?.player = player
        player.addListener(this)

        val mediaItem = MediaItem.Builder()
            .setUri(getString(R.string.media_url_dash)) // Set streaming URL for play video
            .setMimeType(MimeTypes.APPLICATION_MPD)
            .build()
        binding?.videoView?.player?.setMediaItem(mediaItem)
        binding?.videoView?.player?.playWhenReady = playWhenReady
        binding?.videoView?.player?.seekTo(currentItem, playbackPosition)
        binding?.videoView?.player?.prepare()

        // for playing video perfectly, when screen rotation
        savedInstanceState?.getInt("mediaItem")?.let { restoredMedia ->
            val seekTime = savedInstanceState.getLong("SeekTime")
            player.seekTo(restoredMedia, seekTime)
            player.play()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // current play position
        outState.putLong("SeekTime", player.currentPosition)
        // current mediaItem
        outState.putInt("mediaItem", player.currentMediaItemIndex)
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        when(playbackState){ // Backward playing
            Player.STATE_BUFFERING -> {
                binding?.progressBar?.visibility = View.VISIBLE
            }
            Player.STATE_READY -> {
                binding?.progressBar?.visibility = View.INVISIBLE
            }
        }
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }
}