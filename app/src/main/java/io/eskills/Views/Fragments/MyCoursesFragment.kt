package io.eskills.Views.Fragments

import AllCoursesResponse
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.eskills.Adapters.MyCourseAdapter
import io.eskills.databinding.FragmentMyCoursesBinding

class MyCoursesFragment(myCoursesList: ArrayList<AllCoursesResponse>?) : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var myCourseAdapter: MyCourseAdapter? = null
    private var binding: FragmentMyCoursesBinding? = null
    private var arrayList: ArrayList<AllCoursesResponse> = myCoursesList!!


    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyCoursesBinding.inflate(layoutInflater)
        val view = binding?.root
        recyclerView = binding?.myCoursesList
        gridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        gridLayoutManager?.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView?.layoutManager = gridLayoutManager
        myCourseAdapter = MyCourseAdapter(context!!, arrayList)
        recyclerView?.adapter = myCourseAdapter

        binding?.searchbar?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    myCourseAdapter?.filterList(arrayList)
                } else {
                    val filteredArrayList: ArrayList<AllCoursesResponse>? = ArrayList()
                    for (course in arrayList) {
                        if (course.title.toLowerCase()
                            .contains(binding?.searchbar?.text.toString())
                        ) {
                            filteredArrayList?.add(course)
                        }
                    }
                    myCourseAdapter?.filterList(filteredArrayList)
                }
            }

        })

        return view
    }
}