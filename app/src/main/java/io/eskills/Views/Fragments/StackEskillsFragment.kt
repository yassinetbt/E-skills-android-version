package io.eskills.Views.Fragments

import ProfileResponse
import QuestionResponse
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import io.eskills.Adapters.StackEskillsAdapter
import io.eskills.Models.AllDetailsResponse
import io.eskills.Models.StackItem
import io.eskills.Retrofit.RetrofitClient
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentStackEskillsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.round


class StackEskillsFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var arrayList: ArrayList<StackItem>? = ArrayList()
    private var stackEskillsAdapter: StackEskillsAdapter? = null
    private var binding: FragmentStackEskillsBinding? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentStackEskillsBinding.inflate(layoutInflater)
        val view = binding?.root
        recyclerView = binding?.stackList
        gridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        gridLayoutManager?.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView?.layoutManager = gridLayoutManager
        stackEskillsAdapter = StackEskillsAdapter(context!!, arrayList!!)
        recyclerView?.adapter = stackEskillsAdapter
        val allDetailsResponse =
            Gson().fromJson(activity?.intent?.getStringExtra("allDetailsResponse"),
                AllDetailsResponse::class.java)
        val user_id = allDetailsResponse._id
        getAllQuestions(user_id)
        binding?.fab?.setOnClickListener(View.OnClickListener {
            val activity = this.activity as? HomeActivity
            activity?.setCurrentFragment()
            activity?.loadMainFragment(AddQuestionFragment())
        })
        return view
    }

    fun counterTime(eventtime: String): String {
        var day = 0
        var hh = 0
        var mm = 0

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val eventDate = dateFormat.parse(eventtime)
        val cDate = Date()
        val timeDiff = cDate.time - eventDate.time

        day = TimeUnit.MILLISECONDS.toDays(timeDiff).toInt()
        hh = (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day.toLong())).toInt()
        mm =
            (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(
                timeDiff))).toInt()


        return if (day == 0) {
            "$hh hour ago"
        } else if (hh == 0) {
            "$mm min ago"
        } else if (day < 29) {
            "$day day ago"
        } else {
            var month = round((day / 30).toDouble()).toInt()
            "$month month ago"
        }
    }

    fun filterList(s: String) {
        if (s == "") {
            stackEskillsAdapter?.filterList(arrayList)
        } else {
            val filteredArrayList: ArrayList<StackItem>? = ArrayList()
            for (stack in arrayList!!) {
                if (stack.stack_question?.toLowerCase()
                        ?.contains(s)!!
                ) {
                    filteredArrayList?.add(stack)
                }
            }
            stackEskillsAdapter?.filterList(filteredArrayList)
        }
    }

    fun getAllQuestions(id: String) {
        var i = 0
        RetrofitClient.instanceConnected.getAllQuestions(0)
            .enqueue(object : Callback<QuestionResponse> {
                override fun onResponse(
                    call: Call<QuestionResponse>,
                    response: Response<QuestionResponse>,
                ) {
                    if (response.code() == 200) {
                        arrayList = ArrayList()
                        for (question in response.body()?.sliced!!) {
                            var b = false
                            var p = 0
                            p = question.voters.indexOfFirst { it.id == id }
                            if (p != -1) {
                                if (question.voters[p].vote == "up") {
                                    b = true
                                }
                            }
                            if (question.votes < 0) {
                                question.votes = 0
                            }
                            arrayList!!.add(StackItem(question._id,
                                counterTime(question.question_date),
                                question.title,
                                question.contentText,
                                "" + question.votes + "",
                                "" + question.answers.size + "",
                                "" + question.views + "",
                                question.answers,
                                b))
                            RetrofitClient.instanceConnected.getProfile(question.owner)
                                .enqueue(
                                    object : Callback<ProfileResponse> {
                                        override fun onResponse(
                                            call: Call<ProfileResponse>,
                                            response: Response<ProfileResponse>,
                                        ) {
                                            if (response.code() == 200) {
                                                var index =
                                                    arrayList?.indexOfFirst { it.id_question == question._id }
                                                arrayList!![index!!].stack_profile_image =
                                                    "https://e-skills.io/api/user/profile/getAvatar/" + response.body()?.avatar
                                                arrayList!![index].stack_profile_name =
                                                    response.body()?.firstName + " " + response.body()?.lastName
                                                i++
                                                if (i != arrayList!!.size) {
                                                    //loader is here
                                                } else {

                                                }
                                            }

                                        }

                                        override fun onFailure(
                                            call: Call<ProfileResponse>,
                                            t: Throwable,
                                        ) {
                                        }

                                    })
                        }
                        stackEskillsAdapter?.filterList(arrayList)

                    }
                }

                override fun onFailure(call: Call<QuestionResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}