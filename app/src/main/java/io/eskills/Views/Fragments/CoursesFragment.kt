package io.eskills.Views.Fragments

import CoursesListResponse
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.eskills.Adapters.CourseAdapter
import io.eskills.Models.CourseItem
import io.eskills.R
import io.eskills.databinding.FragmentCoursesBinding


class CoursesFragment(idList: ArrayList<String>) : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var arrayList: ArrayList<CourseItem>? = ArrayList<CourseItem>()
    private var courseAdapter: CourseAdapter? = null
    private var idList = idList
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private val TAG = "xxx"
    private var binding: FragmentCoursesBinding? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCoursesBinding.inflate(layoutInflater)
        val view = binding?.root
        preferences = this.activity!!.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        editor = preferences?.edit()
        recyclerView = binding?.coursesList
        gridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        gridLayoutManager?.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView?.layoutManager = gridLayoutManager
        courseAdapter = CourseAdapter(context!!, arrayList!!)
        recyclerView?.adapter = courseAdapter
        getCoursesList()
        binding?.searchbar?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    courseAdapter?.filterList(arrayList)
                } else {
                    val filteredArrayList: ArrayList<CourseItem>? = ArrayList()
                    for (course in arrayList!!) {
                        if (course.title?.toLowerCase()
                                ?.contains(binding?.searchbar?.text.toString())!!
                        ) {
                            filteredArrayList?.add(course)
                        }
                    }
                    courseAdapter?.filterList(filteredArrayList)
                }
            }

        })
        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCoursesList() {
        arrayList = ArrayList()
        if (idList.size == 0) {
            val listType = object : TypeToken<ArrayList<String?>?>() {}.type
            val idList: ArrayList<String> =
                Gson().fromJson(preferences?.getString("idList", ""), listType)
            this.idList = idList
        }
        val myCoursesResponse: CoursesListResponse =
            Gson().fromJson(activity?.intent?.getStringExtra("myCoursesResponse"),
                CoursesListResponse::class.java)
        val allProjects: Int = activity?.intent?.getIntExtra("allProjects", 0)!!
        for (item in myCoursesResponse.requests) {
            arrayList!!.add(CourseItem(item._id, item.image,
                item.title,
                item.chapters.sumOf { x -> x.lectures.size }
                    .toString() + " videos",
                "" + allProjects + " projects",
                "",
                "Buy now",
                R.drawable.gradient_black_course))
        }
        for (id in idList) {
            var index = arrayList!!.indexOfFirst { it.id == id }
            if (index != -1) {
                arrayList!![index].imageColor = R.drawable.gradient_purple
                arrayList!![index].state = "Continue"
            }
        }
        courseAdapter?.filterList(arrayList)
    }


}