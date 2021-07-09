package io.eskills.Views.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.colorgreen.swiper.OnSwipeTouchListener
import com.colorgreen.swiper.SwipeAction
import com.colorgreen.swiper.SwipeActionListener
import io.eskills.Adapters.CoursesViewPagerAdapter
import io.eskills.R
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentSingleChapterBinding


class SingleChapterFragment(
    private var title: String, private var lecture: String,
    private var description: String,
) : Fragment(), View.OnClickListener {
    private var binding: FragmentSingleChapterBinding? = null
    private var quizViewPager: ViewPager? = null
    var adapter: CoursesViewPagerAdapter? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSingleChapterBinding.inflate(layoutInflater)
        val view = binding?.root
        val playerView = binding?.playerLectureView
        playerView?.controllerAutoShow = true
        playerView?.useController = true
        playerView?.showController()
        playerView?.controllerShowTimeoutMs = 2000
        quizViewPager = binding?.chapterViewPager
        adapter = CoursesViewPagerAdapter(childFragmentManager)
        adapter!!.addFragment(ChapterDetailsFragment(description, lecture), " Two ")
        adapter!!.addFragment(QuizFragment2(title), " One ")
        quizViewPager?.adapter = adapter
        binding?.details?.background?.setTint(
            view?.resources!!.getColor(
                R.color.dashboardpurple,
                ((activity as HomeActivity?)!!).theme
            )
        )
        binding?.details?.setTextColor(
            view?.resources!!.getColor(
                R.color.white,
                ((activity as HomeActivity?)!!).theme
            )
        )
        binding?.quizResult?.background?.setTint(
            view?.resources!!.getColor(
                R.color.white,
                ((activity as HomeActivity?)!!).theme
            )
        )
        binding?.quizResult?.setTextColor(
            view?.resources!!.getColor(
                R.color.textdarkcolor,
                ((activity as HomeActivity?)!!).theme
            )
        )
        quizViewPager?.currentItem = 0
        binding?.details?.setOnClickListener(this)
        binding?.quizResult?.setOnClickListener(this)
        quizViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding?.details?.background?.setTint(
                            view?.resources!!.getColor(
                                R.color.dashboardpurple,
                                ((activity as HomeActivity?)!!).theme
                            )
                        )
                        binding?.details?.setTextColor(
                            view?.resources!!.getColor(
                                R.color.white,
                                ((activity as HomeActivity?)!!).theme
                            )
                        )
                        binding?.quizResult?.background?.setTint(
                            view?.resources!!.getColor(
                                R.color.white,
                                ((activity as HomeActivity?)!!).theme
                            )
                        )
                        binding?.quizResult?.setTextColor(
                            view?.resources!!.getColor(
                                R.color.textdarkcolor,
                                ((activity as HomeActivity?)!!).theme
                            )
                        )
                    }
                    1 -> {
                        binding?.quizResult?.background?.setTint(
                            view?.resources!!.getColor(
                                R.color.dashboardpurple,
                                ((activity as HomeActivity?)!!).theme
                            )
                        )
                        binding?.quizResult?.setTextColor(
                            view?.resources!!.getColor(
                                R.color.white,
                                ((activity as HomeActivity?)!!).theme
                            )
                        )
                        binding?.details?.background?.setTint(
                            view?.resources!!.getColor(
                                R.color.white,
                                ((activity as HomeActivity?)!!).theme
                            )
                        )
                        binding?.details?.setTextColor(
                            view?.resources!!.getColor(
                                R.color.textdarkcolor,
                                ((activity as HomeActivity?)!!).theme
                            )
                        )
                    }

                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        val listener = OnSwipeTouchListener()
        val swipeAction = SwipeAction()
        swipeAction.swipeActionListener = object : SwipeActionListener {
            override fun onDragStart(vale: Float, totalFriction: Float) {}
            override fun onDrag(vale: Float, friction: Float) {
                quizViewPager?.layoutParams =
                    LinearLayout.LayoutParams(quizViewPager?.width!!, vale.toInt())
            }

            override fun onDragEnd(`val`: Float, totalFriction: Float) {
                // use setBlocked() for blocking another panels from extending

            }
        }
        swipeAction.direction = SwipeAction.DragDirection.Up
        val targetHeight = binding?.mainview?.height?.toFloat()
        val viewPagerHeight = quizViewPager?.height?.toFloat()
        swipeAction.setSteps(floatArrayOf(1460f, 1460f - 1460 * 0.4f, 0f))
        swipeAction.dragThreshold = 0.2f
        listener.addAction(swipeAction)
        view?.setOnTouchListener(listener)
        return view
    }


    override fun onClick(v: View?) {
        if (v == binding?.details) {
            quizViewPager?.currentItem = 0
        }

        if (v == binding?.quizResult) {
            quizViewPager?.currentItem = 1
        }
    }

}