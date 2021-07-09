package io.eskills.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.eskills.Adapters.LiveMessageAdapter
import io.eskills.Models.LiveMessageItem
import io.eskills.R
import io.eskills.databinding.FragmentMessagingBoxBinding

class MessagingBoxFragment : Fragment() {

    private var binding: FragmentMessagingBoxBinding? = null
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var arrayList: ArrayList<LiveMessageItem>? = null
    private var liveMessageAdapter: LiveMessageAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessagingBoxBinding.inflate(layoutInflater)
        val view = binding?.root
        recyclerView = binding?.boxMessagesList
        gridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        gridLayoutManager?.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView?.layoutManager = gridLayoutManager
        arrayList = ArrayList()
        arrayList = setDataList()
        liveMessageAdapter = LiveMessageAdapter(this.context!!, arrayList!!)
        recyclerView?.adapter = liveMessageAdapter
        recyclerView?.scrollToPosition(arrayList!!.size - 1)

        return view
    }

    private fun setDataList(): ArrayList<LiveMessageItem> {
        val arrayList: ArrayList<LiveMessageItem> = ArrayList()

        arrayList.add(LiveMessageItem(R.drawable.ic_profile_anonyme, "Helloo", 2))
        arrayList.add(LiveMessageItem(R.drawable.ic_profile_anonyme, "This is a continuation of my previous", 1))
        arrayList.add(
            LiveMessageItem(
                R.drawable.ic_profile_anonyme,
                "This is a continuation of my prev This is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prev",
                2
            )
        )
        arrayList.add(LiveMessageItem(R.drawable.ic_profile_anonyme, "Not much to say about this one", 1))
        arrayList.add(
            LiveMessageItem(
                R.drawable.ic_profile_anonyme,
                "Not much to say about this one much to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this one",
                2
            )
        )
        arrayList.add(LiveMessageItem(R.drawable.ic_profile_anonyme, "Helloo", 1))
        arrayList.add(LiveMessageItem(R.drawable.ic_profile_anonyme, "This is a continuation of my previous", 2))
        arrayList.add(
            LiveMessageItem(
                R.drawable.ic_profile_anonyme,
                "This is a continuation of my prev This is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prevThis is a continuation of my prev",
                1
            )
        )
        arrayList.add(LiveMessageItem(R.drawable.ic_profile_anonyme, "this is it", 2))
        arrayList.add(
            LiveMessageItem(
                R.drawable.ic_profile_anonyme,
                "Not much to say about this one much to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this onemuch to say about this one",
                1
            )
        )

        return arrayList
    }

}