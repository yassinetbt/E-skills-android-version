package io.eskills.Views.Fragments

import AllCoursesResponse
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.eskills.Models.AllDetailsResponse
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment(), View.OnClickListener {
    private var gson = Gson()
    private var binding: FragmentDashboardBinding? = null
    private var id1: String? = null
    private var id2: String? = null
    var myCoursesList: ArrayList<AllCoursesResponse>? = null
    var idList: ArrayList<String>? = ArrayList()
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private val TAG = "xxx"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        val view = binding?.root
        val firstCourse = binding?.firstcourse
        val secondCourse = binding?.secondcourse
        val scrollview = binding?.scrollview
        preferences = this.activity!!.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        editor = preferences?.edit()
        scrollview?.isVerticalScrollBarEnabled = false
        binding?.secondcourse?.visibility = View.INVISIBLE
        binding?.firstcourse?.visibility = View.INVISIBLE
        binding?.firstrecommand?.visibility = View.INVISIBLE
        binding?.secondrecommand?.visibility = View.INVISIBLE
        firstCourse?.setOnClickListener(this)
        secondCourse?.setOnClickListener(this)
        binding?.myCourses?.setOnClickListener(this)
        getAllDetails()
        getCourses()
        return view
    }

    override fun onClick(v: View?) {
        if (v == binding?.firstcourse) {
            ((activity as HomeActivity?)!!).setCurrentFragment()
            ((activity as HomeActivity?)!!).loadMainFragment(SingleCourseFragment(id1))

            ((activity as HomeActivity?)!!).bottomNavigationMenu?.menu?.getItem(2)?.isChecked = true
        }
        if (v == binding?.secondcourse) {
            ((activity as HomeActivity?)!!).setCurrentFragment()
            ((activity as HomeActivity?)!!).loadMainFragment(SingleCourseFragment(id2))

            ((activity as HomeActivity?)!!).bottomNavigationMenu?.menu?.getItem(2)?.isChecked = true
        }
        if (v == binding?.myCourses) {
            ((activity as HomeActivity?)!!).loadMainFragment(MyCoursesFragment(myCoursesList))
            ((activity as HomeActivity?)!!).bottomNavigationMenu?.menu?.getItem(2)?.isChecked = true
        }
    }

    private fun getAllDetails() {
        val allDetailsResponse =
            gson.fromJson(activity?.intent?.getStringExtra("allDetailsResponse"),
                AllDetailsResponse::class.java)
        binding?.expscore?.text = "" + allDetailsResponse?.achievements_count + " Exp"
        binding?.projectsscore?.text = "" + allDetailsResponse?.projects_done + ""
        var lectures =
            ((allDetailsResponse?.last_checkpoint_chapters?.last_chapter_index?.toDouble()!! / allDetailsResponse.last_checkpoint_chapters.chapters_count) * 100)
        var quiz =
            (allDetailsResponse.last_checkpoint_quiz.question_index.toDouble() / allDetailsResponse.last_checkpoint_quiz.questions_count) * 100
        binding?.lecturesscore?.text = "" + lectures.toInt() + "%"
        binding?.quizscore?.text = "" + quiz.toInt() + "%"
    }

    private fun getCourses() {
        val listType = object : TypeToken<ArrayList<AllCoursesResponse?>?>() {}.type
        var x = activity?.intent?.getStringExtra("allCoursesResponse")
        val allCoursesResponse: ArrayList<AllCoursesResponse> =
            Gson().fromJson(activity?.intent?.getStringExtra("allCoursesResponse"), listType)
        myCoursesList = allCoursesResponse
        for (course in allCoursesResponse) {
            idList?.add(course._id)
        }
        editor?.putString("idList", gson.toJson(idList))
        editor?.commit()
        if (allCoursesResponse.size == 0) {

        } else if (allCoursesResponse.size == 1) {
            binding?.firstcourse?.visibility = View.VISIBLE
            binding?.firstrecommand?.visibility = View.VISIBLE
            binding?.secondrecommand?.visibility = View.VISIBLE
            id1 = allCoursesResponse.get(0)._id
            binding?.courseFirstTitle?.text = allCoursesResponse.get(0).title
            Glide.with(context!!).load(allCoursesResponse.get(0).image)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
                    ) {
                        binding?.courseFirstImage?.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
            binding?.courseFirstAbout?.text =
                allCoursesResponse.get(0).description.substring(
                    0,
                    150) + " ..."

        } else {
            binding?.secondcourse?.visibility = View.VISIBLE
            binding?.firstcourse?.visibility = View.VISIBLE
            binding?.firstrecommand?.visibility = View.VISIBLE
            binding?.secondrecommand?.visibility = View.VISIBLE
            Glide.with(context!!).load(allCoursesResponse[0].image)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
                    ) {
                        binding?.courseFirstImage?.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
            Glide.with(context!!).load(allCoursesResponse[1].image)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
                    ) {
                        binding?.courseSecondImage?.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
            id1 = allCoursesResponse[0]._id
            id2 = allCoursesResponse[1]._id
            binding?.courseFirstTitle?.text = allCoursesResponse.get(0).title
            binding?.courseFirstAbout?.text =
                allCoursesResponse.get(0).description.substring(
                    0,
                    150) + " ..."
            Glide.with(context!!).load(allCoursesResponse.get(0).image)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
                    ) {
                        binding?.courseFirstImage?.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
            //////////////////////////////////////////////////////////////////
            binding?.courseSecondTitle?.text = allCoursesResponse.get(1).title
            binding?.courseSecondAbout?.text =
                allCoursesResponse[1].description.substring(
                    0,
                    150) + " ..."
            Glide.with(context!!).load(allCoursesResponse.get(1).image)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
                    ) {
                        binding?.courseSecondImage?.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
        }


    }

}
