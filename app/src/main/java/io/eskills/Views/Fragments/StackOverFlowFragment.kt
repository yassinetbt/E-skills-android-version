package io.eskills.Views.Fragments

import StackOverFlowAnswer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.eskills.Adapters.StackOverFlowAdapter
import io.eskills.Models.StackItem
import io.eskills.Retrofit.RetrofitClient
import io.eskills.databinding.FragmentStackOverFlowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StackOverFlowFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var arrayList: ArrayList<StackItem>? = ArrayList()
    private var stackAdapter: StackOverFlowAdapter? = null
    private var binding: FragmentStackOverFlowBinding? = null
    private var pagesize = 10
    private var page = 1
    private var title = "javascript"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStackOverFlowBinding.inflate(layoutInflater)
        val view = binding?.root
        recyclerView = binding?.stackOverflowList
        gridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        gridLayoutManager?.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView?.layoutManager = gridLayoutManager
        stackAdapter = StackOverFlowAdapter(context!!, arrayList!!)
        recyclerView?.adapter = stackAdapter

        getStackOverFlow(title)

        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(0) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RetrofitClient.stackOverFlow.getStackOverFlow(page,
                        "desc",
                        pagesize,
                        "votes",
                        title,
                        "stackoverflow").enqueue(object :
                        Callback<StackOverFlowAnswer> {
                        override fun onResponse(
                            call: Call<StackOverFlowAnswer>,
                            response: Response<StackOverFlowAnswer>,
                        ) {
                            if (response.code() == 200) {
                                for (item in response.body()?.items!!) {
                                    var index =
                                        arrayList!!.indexOfFirst { it.id_question == item.question_id.toString() }
                                    if (index == -1) {
                                        arrayList!!.add(StackItem(
                                            item.question_id.toString(),
                                            item.owner.profile_image,
                                            item.owner.display_name,
                                            "",
                                            item.title,
                                            item.title,
                                            "" + item.score + "",
                                            "" + item.answer_count + "",
                                            "" + item.view_count + "",
                                            item.link))

                                    }

                                }
                                stackAdapter?.addToList(arrayList, arrayList?.size!!)
                                pagesize += 10
                                if (pagesize == 100) {
                                    pagesize = 10
                                    page += 1
                                }
                            }
                        }

                        override fun onFailure(call: Call<StackOverFlowAnswer>, t: Throwable) {

                        }

                    })
                }
            }
        })

        return view
    }

    fun getStackOverFlow(title: String) {
        RetrofitClient.stackOverFlow.getStackOverFlow(1,
            "desc",
            10,
            "votes",
            title,
            "stackoverflow").enqueue(object :
            Callback<StackOverFlowAnswer> {
            override fun onResponse(
                call: Call<StackOverFlowAnswer>,
                response: Response<StackOverFlowAnswer>,
            ) {
                if (response.code() == 200) {
                    arrayList = ArrayList()
                    for (item in response.body()?.items!!) {
                        arrayList!!.add(StackItem(
                            item.question_id.toString(),
                            item.owner.profile_image,
                            item.owner.display_name,
                            "",
                            item.title,
                            item.title,
                            "" + item.score + "",
                            "" + item.answer_count + "",
                            "" + item.view_count + "",
                            item.link))
                    }
                    stackAdapter?.filterList(arrayList)
                }
            }

            override fun onFailure(call: Call<StackOverFlowAnswer>, t: Throwable) {

            }

        })
    }

    fun filterList(s: String) {
        if (s == "") {
            title = "javascript"
            getStackOverFlow(title)
        } else {
            title = s
            RetrofitClient.stackOverFlow.getStackOverFlow(1,
                "desc",
                10,
                "votes",
                title,
                "stackoverflow").enqueue(object :
                Callback<StackOverFlowAnswer> {
                override fun onResponse(
                    call: Call<StackOverFlowAnswer>,
                    response: Response<StackOverFlowAnswer>,
                ) {
                    if (response.code() == 200) {
                        arrayList = ArrayList()
                        for (item in response.body()?.items!!) {
                            arrayList!!.add(StackItem(
                                item.question_id.toString(),
                                item.owner.profile_image,
                                item.owner.display_name,
                                "",
                                item.title,
                                item.title,
                                "" + item.score + "",
                                "" + item.answer_count + "",
                                "" + item.view_count + "",
                                item.link))
                        }
                        stackAdapter?.filterList(arrayList)
                    }
                }

                override fun onFailure(call: Call<StackOverFlowAnswer>, t: Throwable) {

                }

            })
        }


    }
}