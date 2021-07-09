package io.eskills.Views.Fragments

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
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
import com.google.gson.Gson
import io.eskills.Adapters.CoursesViewPagerAdapter
import io.eskills.Models.AllDetailsResponse
import io.eskills.R
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentSingleCourseBinding

class SingleCourseFragment(id: String?) : Fragment(), View.OnClickListener {

    private var chapters: TextView? = null
    private var about: TextView? = null
    private var discussions: TextView? = null
    private var binding: FragmentSingleCourseBinding? = null
    var coursesViewPager: ViewPager? = null
    private var playerView: PlayerView? = null
    var scaleGestureDetector: ScaleGestureDetector? = null
    private var id = id
    private var fullscreen: ImageView? = null
    private var flag = false
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private val url = "https://e-skills.io/api/courses/intro/" + id + "/index.m3u8"
    //private val url = "https://www.youtube.com/watch?v=Q8_XPxQePSY"
    private val gson = Gson()
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
        // Inflate the layout for this fragment
        binding = FragmentSingleCourseBinding.inflate(layoutInflater)
        val view = binding?.root
        playerView = binding?.playerView
        scaleGestureDetector = ScaleGestureDetector(context,
            CustomOnScaleGestureListener(playerView!!))
        playerView?.controllerAutoShow = true
        playerView?.useController = true
        playerView?.controllerShowTimeoutMs = 2000
        val params = playerView!!.layoutParams as LinearLayout.LayoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = (ViewGroup.LayoutParams.MATCH_PARENT * 51) / 100
        playerView!!.layoutParams = params
        fullscreen = view?.findViewById(R.id.exo_fullscreen_icon)
        fullscreen?.setOnClickListener(this)
        chapters = binding?.chapters
        about = binding?.about
        discussions = binding?.discussions
        coursesViewPager = binding?.coursesViewpager
        val adapter = CoursesViewPagerAdapter(childFragmentManager)
        val allDetailsResponse =
            gson.fromJson(activity?.intent?.getStringExtra("allDetailsResponse"),
                AllDetailsResponse::class.java)
        val allProjects: Int = activity?.intent?.getIntExtra("allProjects", 0)!!
        adapter.addFragment(CourseChaptersFragment(this.id!!, allDetailsResponse), " One ")
        adapter.addFragment(CourseAboutFragment(this.id!!, allDetailsResponse, allProjects),
            " Two ")
        coursesViewPager?.adapter = adapter
        coursesViewPager?.currentItem = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            chapters?.background?.setTint(
                view?.resources!!.getColor(
                    R.color.dashboardpurple,
                    ((activity as HomeActivity?)!!).theme
                )
            )
            chapters?.setTextColor(
                view?.resources!!.getColor(
                    R.color.white,
                    ((activity as HomeActivity?)!!).theme
                )
            )
            about?.background?.setTint(
                view?.resources!!.getColor(
                    R.color.white,
                    ((activity as HomeActivity?)!!).theme
                )
            )
            about?.setTextColor(
                view?.resources!!.getColor(
                    R.color.textdarkcolor,
                    ((activity as HomeActivity?)!!).theme
                )
            )
            discussions?.background?.setTint(
                view?.resources!!.getColor(
                    R.color.white,
                    ((activity as HomeActivity?)!!).theme
                )
            )
            discussions?.setTextColor(
                view?.resources!!.getColor(
                    R.color.textdarkcolor,
                    ((activity as HomeActivity?)!!).theme
                )
            )
        }
        coursesViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            chapters?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.dashboardpurple,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            chapters?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            about?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            about?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            discussions?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            discussions?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                        }

                    }
                    1 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            chapters?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            chapters?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            about?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.dashboardpurple,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            about?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            discussions?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            discussions?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                        }

                    }
                    3 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            chapters?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            chapters?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            about?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            about?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            discussions?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.dashboardpurple,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            discussions?.setTextColor(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                        }

                    }

                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        chapters?.setOnClickListener(this)
        about?.setOnClickListener(this)
        return view
    }


    override fun onClick(v: View?) {
        if (v == chapters) {
            coursesViewPager?.currentItem = 0
        }
        if (v == about) {
            coursesViewPager?.currentItem = 1
        }
        if (v == fullscreen) {

            if (flag == false) {
                setLandscape()

            } else {
                setPortrait()

            }
        }
    }

    private fun initializeExoPlayer() {
        @Suppress("DEPRECATION")
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
            context,
            trackSelector,
            loadControl)
        prepareExoPlayer()
        playerView?.player = simpleExoPlayer
        simpleExoPlayer.playWhenReady = false
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

    fun setPortrait() {
        fullscreen?.setImageResource(R.drawable.ic_fullscreen)
        val params = playerView!!.layoutParams as LinearLayout.LayoutParams
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            activity?.window?.clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = (ViewGroup.LayoutParams.MATCH_PARENT * 51) / 100
        playerView!!.layoutParams = params
        ((activity as HomeActivity?)!!).setVisibility(false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        flag = false
    }

    fun setLandscape() {
        flag = true
        fullscreen?.setImageResource(R.drawable.ic_fullscreen_2)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        val params = playerView!!.layoutParams as LinearLayout.LayoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        playerView!!.layoutParams = params
        ((activity as HomeActivity?)!!).setVisibility(true)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
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