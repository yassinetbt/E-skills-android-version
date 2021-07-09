package io.eskills.Views.Fragments

import MessagingResponse
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
import io.eskills.Adapters.MessageAdapter
import io.eskills.Models.CourseItem
import io.eskills.Models.MessageItem
import io.eskills.R
import io.eskills.Retrofit.RetrofitClient
import io.eskills.databinding.FragmentMessagingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.round

class MessagingFragment : Fragment() {

    private var binding: FragmentMessagingBinding? = null
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var messageAdapter: MessageAdapter? = null
    private var arrayList: ArrayList<MessageItem>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessagingBinding.inflate(layoutInflater)
        val view = binding?.root
        recyclerView = binding?.messagesList
        gridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        gridLayoutManager?.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView?.layoutManager = gridLayoutManager
        RetrofitClient.instanceConnected.getMessages().enqueue(object : Callback<ArrayList<MessagingResponse>>{
            override fun onResponse(
                call: Call<ArrayList<MessagingResponse>>,
                response: Response<ArrayList<MessagingResponse>>,
            ) {
                if (response.code() == 200){
                    arrayList = ArrayList()
                    for(message in response.body()!!){
                        if(message.date!="0"){
                            arrayList!!.add(MessageItem(
                                "https://e-skills.io/api/user/profile/getAvatar/" +message.userMessage.avatar,
                                message.message,
                                counterTime(message.date),
                                message.userMessage.firstName))
                        }

                    }
                    messageAdapter = MessageAdapter(context!!, arrayList!!)
                    recyclerView?.adapter = messageAdapter
                }
            }

            override fun onFailure(call: Call<ArrayList<MessagingResponse>>, t: Throwable) {

            }

        })
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
                    messageAdapter?.filterList(arrayList)
                } else {
                    val filteredArrayList: ArrayList<MessageItem>? = ArrayList()
                    for (message in arrayList!!) {
                        if (message.name?.toLowerCase()
                                ?.contains(binding?.searchbar?.text.toString())!!
                        ) {
                            filteredArrayList?.add(message)
                        }
                    }
                    messageAdapter?.filterList(filteredArrayList)
                }
            }

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


}