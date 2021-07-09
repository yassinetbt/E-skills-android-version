package io.eskills.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.eskills.Adapters.MainViewPagerAdapter
import io.eskills.R
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentViewPagerMainBinding


class ViewPagerMainFragment() : Fragment() {
    constructor(position: Int) : this() {
        this.position = position
    }

    private var position: Int = 0
    private var binding: FragmentViewPagerMainBinding? = null
    private var adapter: MainViewPagerAdapter? = null
    private var bottomNavigationMenu: BottomNavigationView? = null
    var mainViewPager: ViewPager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerMainBinding.inflate(layoutInflater)
        val view = binding?.root
        mainViewPager = binding?.MainViewPager
        bottomNavigationMenu = ((activity as HomeActivity?)!!).bottomNavigationMenu
        adapter = MainViewPagerAdapter(childFragmentManager)
        adapter?.addFragment(DashboardFragment(), " One ")
        var frag = adapter?.getItem(0) as DashboardFragment
        adapter?.addFragment(CoursesFragment(frag.idList!!), " Two ")
        adapter?.addFragment(StackFragment(), " Three ")
        adapter?.addFragment(ProjectFragment(), "Four")
        mainViewPager?.adapter = adapter
        mainViewPager?.currentItem = position
        mainViewPager?.offscreenPageLimit = 3
        mainViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavigationMenu?.menu?.findItem(R.id.menu)?.isChecked = true
                    1 -> bottomNavigationMenu?.menu?.findItem(R.id.courses)?.isChecked = true
                    2 -> bottomNavigationMenu?.menu?.findItem(R.id.stack)?.isChecked = true
                    3 -> bottomNavigationMenu?.menu?.findItem(R.id.project)?.isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })


        return view
    }

}