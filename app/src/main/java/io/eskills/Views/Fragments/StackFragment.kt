package io.eskills.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import io.eskills.Adapters.CoursesViewPagerAdapter
import io.eskills.Models.AllDetailsResponse
import io.eskills.R
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentStackBinding

class StackFragment : Fragment(), View.OnClickListener {


    private var stack1Text: TextView? = null
    private var stack2Text: TextView? = null
    private var stackEskills: LinearLayout? = null
    private var stackOverflow: LinearLayout? = null
    private var eskillsText: TextView? = null
    private var overFlowText: TextView? = null
    private var stackViewPager: ViewPager? = null
    private var binding: FragmentStackBinding? = null
    private var adapter: CoursesViewPagerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStackBinding.inflate(layoutInflater)
        val view = binding?.root
        stack1Text = binding?.stack1Text
        stack2Text = binding?.stack2Text
        stackEskills = binding?.stackeskills
        stackOverflow = binding?.stackoverflow
        eskillsText = binding?.eskillsText
        overFlowText = binding?.overflowText
        stackViewPager = binding?.stackViewPager
        adapter = CoursesViewPagerAdapter(childFragmentManager)
        adapter?.addFragment(StackEskillsFragment(), " One ")
        adapter?.addFragment(StackOverFlowFragment(), " Two ")
        stackViewPager?.adapter = adapter
        stackViewPager?.currentItem = 0
        stackViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            stackEskills?.background?.setTint(
                                view?.resources?.getColor(
                                    R.color.dashboardpurple,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                            stack1Text?.setTextColor(
                                view?.resources?.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                            eskillsText?.setTextColor(
                                view?.resources?.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                            stackOverflow?.background?.setTint(
                                view?.resources?.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                            stack2Text?.setTextColor(
                                view?.resources?.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                            overFlowText?.setTextColor(
                                view?.resources?.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                        }
                    }
                    1 -> {

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            stackEskills?.background?.setTint(
                                view?.resources!!.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )
                            )
                            stack1Text?.setTextColor(
                                view?.resources?.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                            eskillsText?.setTextColor(
                                view?.resources?.getColor(
                                    R.color.textdarkcolor,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                            stackOverflow?.background?.setTint(
                                view?.resources?.getColor(
                                    R.color.dashboardpurple,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                            stack2Text?.setTextColor(
                                view?.resources?.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                            overFlowText?.setTextColor(
                                view?.resources?.getColor(
                                    R.color.white,
                                    ((activity as HomeActivity?)!!).theme
                                )!!
                            )
                        }
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        stackOverflow?.setOnClickListener(this)
        stackEskills?.setOnClickListener(this)
        binding?.searchStack?.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        if (v == stackEskills) {
            stackViewPager?.currentItem = 0
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                stackEskills?.background?.setTint(
                    view?.resources?.getColor(
                        R.color.dashboardpurple,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
                stack1Text?.setTextColor(
                    view?.resources?.getColor(
                        R.color.white,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
                eskillsText?.setTextColor(
                    view?.resources?.getColor(
                        R.color.white,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
                stackOverflow?.background?.setTint(
                    view?.resources?.getColor(
                        R.color.white,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
                stack2Text?.setTextColor(
                    view?.resources?.getColor(
                        R.color.textdarkcolor,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
                overFlowText?.setTextColor(
                    view?.resources?.getColor(
                        R.color.textdarkcolor,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
            }
        }
        if (v == stackOverflow) {
            stackViewPager?.currentItem = 1
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                stackEskills?.background?.setTint(
                    view?.resources!!.getColor(
                        R.color.white,
                        ((activity as HomeActivity?)!!).theme
                    )
                )
                stack1Text?.setTextColor(
                    view?.resources?.getColor(
                        R.color.textdarkcolor,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
                eskillsText?.setTextColor(
                    view?.resources?.getColor(
                        R.color.textdarkcolor,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
                stackOverflow?.background?.setTint(
                    view?.resources?.getColor(
                        R.color.dashboardpurple,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
                stack2Text?.setTextColor(
                    view?.resources?.getColor(
                        R.color.white,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
                overFlowText?.setTextColor(
                    view?.resources?.getColor(
                        R.color.white,
                        ((activity as HomeActivity?)!!).theme
                    )!!
                )
            }
        }
        if (v == binding?.searchStack) {
            if (binding?.searchStack?.text.toString() == "search") {
                if (binding?.searchbar?.text.toString() != "") {
                    binding?.searchStack?.text = "reset"
                    var stackEskillsFragment = adapter?.getItem(0) as StackEskillsFragment
                    stackEskillsFragment.filterList(binding?.searchbar?.text.toString())
                    var stackOverFlowFragment = adapter?.getItem(1) as StackOverFlowFragment
                    stackOverFlowFragment.filterList(binding?.searchbar?.text.toString())
                }
            } else {
                binding?.searchStack?.text = "search"
                binding?.searchbar?.setText("")
                var stackEskillsFragment = adapter?.getItem(0) as StackEskillsFragment
                val allDetailsResponse =
                    Gson().fromJson(activity?.intent?.getStringExtra("allDetailsResponse"),
                        AllDetailsResponse::class.java)
                val user_id = allDetailsResponse._id
                stackEskillsFragment.getAllQuestions(user_id)
                var stackOverFlowFragment = adapter?.getItem(1) as StackOverFlowFragment
                stackOverFlowFragment.getStackOverFlow("javascript")
            }

        }
    }


}