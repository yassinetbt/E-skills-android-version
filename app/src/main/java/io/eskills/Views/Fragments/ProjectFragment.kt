package io.eskills.Views.Fragments

import ProjectResponse
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.eskills.Adapters.ProjectsAdapter
import io.eskills.Models.AllDetailsResponse
import io.eskills.Models.Project
import io.eskills.databinding.FragmentProjectBinding
import java.text.SimpleDateFormat

class ProjectFragment : Fragment() {
    private var binding: FragmentProjectBinding? = null
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var projectAdapter: ProjectsAdapter? = null
    private var arrayList: ArrayList<Project>? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProjectBinding.inflate(layoutInflater)
        val view = binding?.root
        val listType = object : TypeToken<ArrayList<ProjectResponse?>?>() {}.type
        val allProjectResponse: ArrayList<ProjectResponse> =
            Gson().fromJson(activity?.intent?.getStringExtra("Projects"), listType)
        recyclerView = binding?.myProjectsList
        gridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        gridLayoutManager?.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView?.layoutManager = gridLayoutManager
        arrayList = ArrayList()
        for (project in allProjectResponse) {
            val allDetailsResponse =
                Gson().fromJson(activity?.intent?.getStringExtra("allDetailsResponse"),
                    AllDetailsResponse::class.java)
            val idUser = allDetailsResponse._id
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val outputDate = SimpleDateFormat("yyyy-MM-dd")
            val eventDate = dateFormat.parse(project.deadline)
            val date = outputDate.format(eventDate)
            var technologies = "Technologies :"
            for (technology in project.technologies) {
                technologies = "$technologies $technology/"
            }
            val projectUser = project.work_tracking.find { it.userId == idUser }
            if (projectUser != null) {
                arrayList!!.add(Project(project.title,
                    project.github_link,
                    projectUser.status,
                    technologies.substring(0, technologies.length - 1),
                    "DeadLine :$date"))
            } else {
                arrayList!!.add(Project(project.title,
                    project.github_link,
                    "No project uploaded",
                    technologies.substring(0, technologies.length - 1),
                    "DeadLine :$date"))
            }
            projectAdapter = ProjectsAdapter(context!!, arrayList!!)
            recyclerView?.adapter = projectAdapter
        }
        binding?.searchbar?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    projectAdapter?.filterList(arrayList)
                } else {
                    val filteredArrayList: ArrayList<Project>? = ArrayList()
                    for (project in arrayList!!) {
                        if (project.title?.toLowerCase()
                                ?.contains(binding?.searchbar?.text.toString())!!
                        ) {
                            filteredArrayList?.add(project)
                        }
                    }
                    projectAdapter?.filterList(filteredArrayList)
                }
            }
        })

        return view
    }


}