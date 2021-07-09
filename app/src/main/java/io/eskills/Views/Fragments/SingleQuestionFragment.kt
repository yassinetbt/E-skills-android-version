package io.eskills.Views.Fragments

import ProfileResponse
import SingleQuestionResponse
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.google.gson.Gson
import io.eskills.Adapters.StackEskillsResponsesAdapter
import io.eskills.Models.*
import io.eskills.R
import io.eskills.Retrofit.RetrofitClient
import io.eskills.Views.HomeActivity
import io.eskills.databinding.FragmentSingleQuestionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.round

class SingleQuestionFragment(stackItem: StackItem) : Fragment(), View.OnClickListener {
    var binding: FragmentSingleQuestionBinding? = null
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var stackEskillsResponsesAdapter: StackEskillsResponsesAdapter? = null
    private var stackItem = stackItem
    private var arrayList: ArrayList<QuestionResponseItem> = ArrayList()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSingleQuestionBinding.inflate(layoutInflater)
        val view = binding?.root
        recyclerView = binding?.responsesList
        gridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        gridLayoutManager?.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView?.layoutManager = gridLayoutManager
        Glide.with(context!!).load(stackItem.stack_profile_image)
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
                ) {
                    binding?.questionProfileImage?.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
        binding?.questionProfileName?.text = stackItem.stack_profile_name
        binding?.questionProfileTime?.text = stackItem.stack_profile_time
        binding?.questionContent?.text = stackItem.stack_question
        RetrofitClient.instanceConnected.getQuestion(stackItem.id_question!!).enqueue(object :
            Callback<SingleQuestionResponse> {
            override fun onResponse(
                call: Call<SingleQuestionResponse>,
                response: Response<SingleQuestionResponse>,
            ) {
                if (response.code() == 200) {
                    if (!response.body()?.contentCode.isNullOrEmpty()) {
                        val str: String = response.body()?.contentCode.toString()
                        val ss = CodeEditText.setHighLight(str)
                        binding?.questionCode?.text = ss
                    }
                    if (!response.body()?.tags.isNullOrEmpty()) {
                        binding?.questionTags?.text = "Tags: " + response.body()?.tags
                    }

                }
            }

            override fun onFailure(call: Call<SingleQuestionResponse>, t: Throwable) {

            }

        })
        if (stackItem.voted == true) {
            binding?.votesSingleQuestion?.setBackgroundResource(R.drawable.ic_blue_votes)
        }
        binding?.votesSingleQuestion?.setOnClickListener(this)
        if (stackItem.stack_title?.length!! > 25) {
            binding?.questionQuestionTop?.text = stackItem.stack_title?.substring(0, 25) + "..."
        } else {
            binding?.questionQuestionTop?.text = stackItem.stack_title + "..."
        }
        binding?.questionViewsNumber?.text = stackItem.views_number
        binding?.questionVotesNumber?.text = stackItem.votes_number
        binding?.questionRepliesNumber?.text = stackItem.replies_number
        stackEskillsResponsesAdapter = StackEskillsResponsesAdapter(context!!, arrayList)
        recyclerView?.adapter = stackEskillsResponsesAdapter
        val allDetailsResponse =
            Gson().fromJson(activity?.intent?.getStringExtra("allDetailsResponse"),
                AllDetailsResponse::class.java)
        val user_id = allDetailsResponse._id
        getAnswers(user_id)

        binding?.fabAnswer?.setOnClickListener(View.OnClickListener {
            val activity = this.activity as? HomeActivity
            activity?.setCurrentFragment()
            activity?.loadMainFragment(AnswerFragment(stackItem))
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
            "$month month ago ago"
        }
    }

    private fun getAnswers(userId: String) {
        var i = 0
        arrayList = ArrayList()
        stackItem.responses?.forEach { answer ->
            var b = false
            var p = 0
            p = answer.voters.indexOfFirst { it.id == userId }
            if (p != -1) {
                if (answer.voters[p].vote == "up") {
                    b = true
                }
            }
            arrayList.add(QuestionResponseItem(answer._id,
                counterTime(answer.answer_date),
                answer.contentText,
                "" + answer.votes + "",
                "" + answer.views + "",
                answer.accepted,
                b))
            RetrofitClient.instanceConnected.getProfile(answer.owner)
                .enqueue(
                    object : Callback<ProfileResponse> {
                        override fun onResponse(
                            call: Call<ProfileResponse>,
                            response: Response<ProfileResponse>,
                        ) {
                            //TODO
                            //if (response.code() == 200) {
                            //    var index =
                            //        arrayList.indexOfFirst { it.id == answer._id }
                            //    arrayList[index].stack_profile_image =
                            //        "https://test.e-skills.io/api/user/profile/getAvatar/" + response.body()?.avatar
                            //    arrayList[index].stack_profile_name =
                            //        response.body()?.firstName + " " + response.body()?.lastName
                            //    i++
                            //    if (i != arrayList.size) {
//
                            //    } else {
                            //            stackEskillsResponsesAdapter?.filterList(arrayList)
                            //    }
                            //}
                            stackEskillsResponsesAdapter?.filterList(arrayList)

                        }

                        override fun onFailure(
                            call: Call<ProfileResponse>,
                            t: Throwable,
                        ) {
                        }

                    })
        }
        stackEskillsResponsesAdapter?.filterList(arrayList)
    }

    override fun onClick(v: View?) {
        if (v == binding?.votesSingleQuestion) {
            if (stackItem.voted == false) {
                binding?.votesSingleQuestion?.setBackgroundResource(R.drawable.ic_blue_votes)
                binding?.questionVotesNumber?.text =
                    (stackItem.votes_number!!.toInt() + 1).toString()
                stackItem.votes_number = (stackItem.votes_number!!.toInt() + 1).toString()
                RetrofitClient.instanceConnected.voteAnswer(VoteBody(stackItem.id_question!!, "up"))
                    .enqueue(
                        object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.code() == 200) {
                                    Toast.makeText(context,
                                        "Your vote is added successfully ",
                                        Toast.LENGTH_SHORT).show()
                                    stackItem.voted = true
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                            }

                        })
            } else {
                binding?.votesSingleQuestion?.setBackgroundResource(R.drawable.ic_votes)
                binding?.questionVotesNumber?.text =
                    (stackItem.votes_number!!.toInt() - 1).toString()
                stackItem.votes_number = (stackItem.votes_number!!.toInt() - 1).toString()
                RetrofitClient.instanceConnected.voteAnswer(VoteBody(stackItem.id_question!!,
                    "down")).enqueue(
                    object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.code() == 200) {
                                Toast.makeText(context,
                                    "You canceled your vote",
                                    Toast.LENGTH_SHORT).show()
                                stackItem.voted = false
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                        }

                    })
            }
        }
    }

}