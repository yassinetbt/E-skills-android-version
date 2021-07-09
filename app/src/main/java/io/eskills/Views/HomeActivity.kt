package io.eskills.Views


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.eskills.R
import io.eskills.Views.Fragments.*
import io.eskills.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity(), View.OnClickListener,
    BottomNavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityHomeBinding? = null
    var bottomNavigationMenu: BottomNavigationView
    ? = null
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private val TAG = "xxx"
    private var topBar: RelativeLayout? = null
    private var live: LinearLayout? = null
    private var liveImage: ImageView? = null
    private var currentFragment: Fragment? =
        supportFragmentManager.findFragmentById(R.id.MainFrameLayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
        preferences = getSharedPreferences(TAG, Context.MODE_PRIVATE)
        editor = preferences?.edit()
        topBar = binding?.topBar
        bottomNavigationMenu = binding?.bottomnavmenu
        bottomNavigationMenu?.itemIconTintList = null
        live = binding?.liveButton
        liveImage = binding?.live
        loadMainFragment(ViewPagerMainFragment(0))
        setCurrentFragment()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            live?.background?.setTint(
                window.decorView.rootView.resources.getColor(
                    R.color.white,
                    theme
                )
            )
        }
        liveImage?.setBackgroundResource(R.drawable.ic_live_streaming)
        live?.setOnClickListener(this)
        binding?.messageButton?.setOnClickListener(this)
        binding?.profileButton?.setOnClickListener(this)
        binding?.logoutButton?.setOnClickListener(this)
        bottomNavigationMenu?.setOnNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val frag: Fragment? = supportFragmentManager.findFragmentById(R.id.MainFrameLayout)
        if (frag is ViewPagerMainFragment) {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.MainFrameLayout) as ViewPagerMainFragment
            if (fragment.mainViewPager?.currentItem != 0) {
                fragment.mainViewPager?.currentItem = 0
            } else {
                val exitIntent = Intent(Intent.ACTION_MAIN)
                exitIntent.addCategory(Intent.CATEGORY_HOME)
                exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(exitIntent)
            }
        } else if (frag is SingleCourseFragment) {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.MainFrameLayout) as SingleCourseFragment
            if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                fragment.setPortrait()
            } else if (fragment.coursesViewPager?.currentItem != 0) {
                fragment.coursesViewPager?.currentItem = 0
            } else {
                if (frag == currentFragment) {
                    loadMainFragment(ViewPagerMainFragment())
                    bottomNavigationMenu?.menu?.getItem(0)?.isChecked = true
                } else {
                    loadMainFragment(currentFragment!!)
                    bottomNavigationMenu?.menu?.getItem(0)?.isChecked = true
                }
            }
        } else if (frag is MyCoursesFragment) {
            loadMainFragment(ViewPagerMainFragment())
            bottomNavigationMenu?.menu?.getItem(0)?.isChecked = true
        } else if (frag is SingleQuestionFragment) {
            loadMainFragmentOnRightPosition(2)
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                live?.background?.setTint(
                    window.decorView.rootView.resources.getColor(R.color.white, theme)
                )
            }
            liveImage?.setBackgroundResource(R.drawable.ic_live_streaming)
            if (currentFragment == frag) {
                loadMainFragment(ViewPagerMainFragment())
                bottomNavigationMenu?.menu?.getItem(0)?.isChecked = true
            } else {
                loadMainFragment(currentFragment!!)
            }
        }
    }

    fun loadMainFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.go_up,
            R.anim.go_down,
            R.anim.go_up2,
            R.anim.go_down2
        )
        fragmentTransaction.replace(R.id.MainFrameLayout, fragment)
        fragmentTransaction.commit()

    }

    fun loadMainFragmentOnRightPosition(position: Int) {
        val frag: Fragment? = supportFragmentManager.findFragmentById(R.id.MainFrameLayout)
        if (frag is LiveFragment || frag is SingleCourseFragment || frag is MyCoursesFragment || frag is QuizFragment || frag is SingleChapterFragment || frag is QuizFragment2 || frag is SingleQuestionFragment || frag is MessagingFragment || frag is MessagingBoxFragment || frag is SingleStackOverFlowQuestionFragment || frag is AddQuestionFragment || frag is AnswerFragment || frag is ProfileFragment) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                R.anim.go_up,
                R.anim.go_down,
            )
            fragmentTransaction.replace(R.id.MainFrameLayout, ViewPagerMainFragment(position))
            fragmentTransaction.commit()
        } else {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.MainFrameLayout) as ViewPagerMainFragment
            fragment.mainViewPager?.currentItem = position
        }
    }

    override fun onClick(v: View?) {
        if (v == live) {
            val frag: Fragment? = supportFragmentManager.findFragmentById(R.id.MainFrameLayout)
            if (frag is ViewPagerMainFragment) {
                setCurrentFragment()
                loadMainFragment(LiveFragment())
            } else if (frag is SingleCourseFragment) {
                setCurrentFragment()
                loadMainFragment(LiveFragment())
            } else if (frag is SingleChapterFragment) {
                setCurrentFragment()
                loadMainFragment(LiveFragment())
            } else if (frag is MessagingFragment) {
                setCurrentFragment()
                loadMainFragment(LiveFragment())
            } else if (frag is ProfileFragment) {
                setCurrentFragment()
                loadMainFragment(LiveFragment())
            } else {
                setCurrentFragment()
                loadMainFragment(LiveFragment())
            }
            bottomNavigationMenu?.menu?.getItem(2)?.isChecked = true
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                live?.background?.setTint(
                    window.decorView.rootView.resources.getColor(
                        R.color.redlive,
                        theme
                    )
                )
            }
            liveImage?.setBackgroundResource(R.drawable.ic_red_live)
        }
        if (v == binding?.messageButton) {
            val frag: Fragment? = supportFragmentManager.findFragmentById(R.id.MainFrameLayout)
            if (frag is ViewPagerMainFragment) {
                setCurrentFragment()
                loadMainFragment(MessagingFragment())
            } else if (frag is SingleCourseFragment) {
                setCurrentFragment()
                loadMainFragment(MessagingFragment())
            } else if (frag is SingleChapterFragment) {
                setCurrentFragment()
                loadMainFragment(MessagingFragment())
            } else if (frag is LiveFragment) {
                setCurrentFragment()
                loadMainFragment(MessagingFragment())
            } else if (frag is ProfileFragment) {
                setCurrentFragment()
                loadMainFragment(MessagingFragment())
            } else {
                setCurrentFragment()
                loadMainFragment(MessagingFragment())
            }
            bottomNavigationMenu?.menu?.getItem(2)?.isChecked = true
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                live?.background?.setTint(
                    window.decorView.rootView.resources.getColor(
                        R.color.white,
                        theme
                    )
                )
            }
            liveImage?.setBackgroundResource(R.drawable.ic_live_streaming)
        }

        if (v == binding?.profileButton) {
            val frag: Fragment? = supportFragmentManager.findFragmentById(R.id.MainFrameLayout)
            if (frag is ViewPagerMainFragment) {
                setCurrentFragment()
                loadMainFragment(ProfileFragment())
            } else if (frag is SingleCourseFragment) {
                setCurrentFragment()
                loadMainFragment(ProfileFragment())
            } else if (frag is SingleChapterFragment) {
                setCurrentFragment()
                loadMainFragment(ProfileFragment())
            } else if (frag is LiveFragment) {
                setCurrentFragment()
                loadMainFragment(ProfileFragment())
            } else if (frag is MessagingFragment) {
                setCurrentFragment()
                loadMainFragment(ProfileFragment())
            } else {
                setCurrentFragment()
                loadMainFragment(ProfileFragment())
            }
            bottomNavigationMenu?.menu?.getItem(2)?.isChecked = true
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                live?.background?.setTint(
                    window.decorView.rootView.resources.getColor(
                        R.color.white,
                        theme
                    )
                )
            }
            liveImage?.setBackgroundResource(R.drawable.ic_live_streaming)
        }
        if (v == binding?.logoutButton) {
            val i2 = Intent(applicationContext, MainActivity::class.java)
            editor?.putBoolean("isconnected",
                false)
            editor?.commit()
            startActivity(i2)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            live?.background?.setTint(
                window.decorView.rootView.resources.getColor(R.color.white, theme)
            )
        }
        liveImage?.setBackgroundResource(R.drawable.ic_live_streaming)

        when (item.itemId) {
            R.id.menu -> {
                if (!item.isChecked) {
                    loadMainFragmentOnRightPosition(0)
                }
            }
            R.id.courses -> {
                if (!item.isChecked) {
                    loadMainFragmentOnRightPosition(1)
                }
            }
            R.id.stack -> {
                if (!item.isChecked) {
                    loadMainFragmentOnRightPosition(2)
                }
            }
            R.id.eskills -> {
                loadMainFragmentOnRightPosition(0)
            }
            R.id.project -> {
                loadMainFragmentOnRightPosition(3)
            }

        }

        return true
    }

    fun setCurrentFragment() {
        currentFragment = supportFragmentManager.findFragmentById(R.id.MainFrameLayout)
    }

    fun setVisibility(boolean: Boolean) {
        if (boolean) {
            topBar?.visibility = View.INVISIBLE
            bottomNavigationMenu?.visibility = View.INVISIBLE
            binding?.MainFrameLayout!!.layoutParams = RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        } else {
            topBar?.visibility = View.VISIBLE
            bottomNavigationMenu?.visibility = View.VISIBLE
            (binding?.MainFrameLayout!!.layoutParams as RelativeLayout.LayoutParams).addRule(
                RelativeLayout.BELOW,
                R.id.top_bar)
            (binding?.MainFrameLayout!!.layoutParams as RelativeLayout.LayoutParams).addRule(
                RelativeLayout.ABOVE,
                R.id.bottomnavmenu)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        var mFragmentManager = supportFragmentManager
        val frag: Fragment? = mFragmentManager.findFragmentById(R.id.MainFrameLayout)
        if (frag is SingleCourseFragment) {
            frag.scaleGestureDetector?.onTouchEvent(event)
        }
        // val fragment: Fragment? = mFragmentManager.findFragmentById(R.id.MainFrameLayout)
        // if (fragment is SingleChapterFragment) {
        //    val ff = fragment.adapter?.getItem(0) as ChapterDetailsFragment
        //     ff.onTouchEvent(event!!)
        // }
        return true
    }
}