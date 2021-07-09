package io.eskills.Views.Fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import io.eskills.Adapters.LiveMessageAdapter
import io.eskills.Models.LiveMessageItem
import io.eskills.R
import io.eskills.databinding.FragmentLiveBinding


class LiveFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var arrayList: ArrayList<LiveMessageItem>? = null
    private var liveMessageAdapter: LiveMessageAdapter? = null
    private var binding: FragmentLiveBinding? = null
    var scaleGestureDetector: ScaleGestureDetector? = null
    private var flag = false
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    //private val url = "https://test.e-skills.io/live"
    private val url = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"
    private var playerView: PlayerView? = null
    private val bandwidthMeter by lazy {
        DefaultBandwidthMeter()
    }

    private val videoFactory by lazy {
        AdaptiveTrackSelection.Factory(bandwidthMeter)
    }

    private val trackSelector by lazy {
        DefaultTrackSelector(videoFactory)
    }

    private val loadControl by lazy {
        DefaultLoadControl()
    }

    override fun onStart() {
        super.onStart()
        initializeExoPlayer()
    }


    override fun onStop() {
        super.onStop()
        simpleExoPlayer.playWhenReady = false

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLiveBinding.inflate(layoutInflater)
        val view = binding?.root
        playerView = binding?.livePlayer
        recyclerView = binding?.messagesList
        gridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        gridLayoutManager?.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView?.layoutManager = gridLayoutManager
        arrayList = ArrayList()
        arrayList = setDataList()
        liveMessageAdapter = LiveMessageAdapter(this.context!!, arrayList!!)
        recyclerView?.adapter = liveMessageAdapter
        recyclerView?.scrollToPosition(arrayList!!.size - 1)
        return view
    }

    private fun setDataList(): ArrayList<LiveMessageItem> {
        var arrayList: ArrayList<LiveMessageItem> = ArrayList()

        arrayList.add(LiveMessageItem(R.drawable.ic_profile_anonyme, "Helloo", 2))
        arrayList.add(LiveMessageItem(R.drawable.ic_profile_anonyme, "This is a continuation of my previous", 1))
        arrayList.add(
            LiveMessageItem(
                R.drawable.ic_profile_anonyme,
                "This is a continuation of my prev This is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prev",
                1
            )
        )
        arrayList.add(LiveMessageItem(R.drawable.ic_profile_anonyme, "Not much to say about this one", 2))
        arrayList.add(
            LiveMessageItem(
                R.drawable.ic_profile_anonyme,
                "Not much to say about this one much to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this one",
                1
            )
        )
        arrayList.add(LiveMessageItem(R.drawable.ic_profile_anonyme, "Helloo", 2))




        return arrayList
    }

    private fun initializeExoPlayer() {
        @Suppress("DEPRECATION")
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
            context,
            trackSelector,
            loadControl)
        prepareExoPlayer()
        playerView?.player = simpleExoPlayer
        simpleExoPlayer.playWhenReady = true
    }


    private fun prepareExoPlayer() {
        val userAgent: String = Util.getUserAgent(context, "User Agent")
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory(
            userAgent, null,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            60000,
            true)
        val media = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url))
        simpleExoPlayer.prepare(media)

    }

    private class CustomOnScaleGestureListener(
        private val player: PlayerView,
    ) : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        private var scaleFactor = 0f

        override fun onScale(
            detector: ScaleGestureDetector,
        ): Boolean {
            scaleFactor = detector.scaleFactor
            return true
        }

        override fun onScaleBegin(
            detector: ScaleGestureDetector,
        ): Boolean {
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
            if (scaleFactor > 1) {
                player.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL)
            } else {
                player.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT)
            }
        }
    }


}


