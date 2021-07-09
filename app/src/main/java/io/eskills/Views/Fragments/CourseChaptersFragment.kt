package io.eskills.Views.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import io.eskills.Adapters.ChapterAdapter
import io.eskills.Models.AllDetailsResponse
import io.eskills.Models.ChapterItem
import io.eskills.Models.CoursesListResponse.SingleCourseResponse
import io.eskills.R
import io.eskills.Retrofit.RetrofitClient
import io.eskills.databinding.FragmentCourseChaptersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class CourseChaptersFragment(id: String, allDetailsResponse: AllDetailsResponse) : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var arrayList: ArrayList<ChapterItem>? = null
    private var chapterAdapter: ChapterAdapter? = null
    private var id = id
    private var index = 0
    private var allDetailsResponse = allDetailsResponse
    private var binding: FragmentCourseChaptersBinding? = null
    private val TAG = "xxx"
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCourseChaptersBinding.inflate(layoutInflater)
        val view = binding?.root
        preferences = activity?.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        editor = preferences?.edit()
        recyclerView = binding?.chaptersList
        gridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        gridLayoutManager?.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView?.layoutManager = gridLayoutManager
        arrayList = ArrayList()
        getAllDetails()
        getACourse()
        return view
    }

    private fun getAllDetails() {
        if (allDetailsResponse.last_checkpoint_chapters.courseId == id) {
            index = allDetailsResponse.last_checkpoint_chapters.last_chapter_index
        }
    }

    private fun getACourse() {
        RetrofitClient.instanceConnected.getAcourse(this.id).enqueue(object :
            Callback<SingleCourseResponse> {
            override fun onResponse(
                call: Call<SingleCourseResponse>,
                response: Response<SingleCourseResponse>,
            ) {
                if (response.code() == 200) {
                    editor?.putString("chapters", Gson().toJson(response.body()))
                    editor?.commit()
                    arrayList = ArrayList<ChapterItem>()
                    for (chapter in response.body()?.requests?.chapters!!) {
                        if (index != 0) {
                            if (chapter.index <= index) {
                                arrayList!!.add(ChapterItem(R.drawable.ic_played,
                                    chapter.lectures_count.toString() + " lectures - " + chapter.title,
                                    "total : " + TimeUnit.SECONDS.toMinutes(chapter.lectures.sumOf { x -> x.duration }
                                        .toLong()) + " minutes",
                                    R.drawable.greenline, R.drawable.ic_ticked,
                                    chapter.title,
                                    chapter.lectures))
                            } else {
                                arrayList!!.add(ChapterItem(R.drawable.ic_notplayed,
                                    chapter.lectures_count.toString() + " lectures - " + chapter.title,
                                    "total : " + TimeUnit.SECONDS.toMinutes(chapter.lectures.sumOf { x -> x.duration }
                                        .toLong()) + " minutes",
                                    R.drawable.grayline, R.drawable.ic_notticked,
                                    chapter.title,
                                    chapter.lectures))
                            }
                        } else {
                            arrayList!!.add(ChapterItem(R.drawable.ic_notplayed,
                                chapter.lectures_count.toString() + " lectures - " + chapter.title,
                                "total : " + TimeUnit.SECONDS.toMinutes(chapter.lectures.sumOf { x -> x.duration }
                                    .toLong()) + " minutes",
                                R.drawable.grayline, R.drawable.ic_notticked,
                                chapter.title,
                                chapter.lectures))
                        }
                    }

                    chapterAdapter = ChapterAdapter(context!!, arrayList!!)
                    recyclerView?.adapter = chapterAdapter
                } else {


                }
            }

            override fun onFailure(call: Call<SingleCourseResponse>, t: Throwable) {
            }

        })
    }

}