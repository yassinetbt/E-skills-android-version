package io.eskills.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.eskills.Models.Project
import io.eskills.R
import io.eskills.Views.Fragments.SingleStackOverFlowQuestionFragment
import io.eskills.Views.HomeActivity

class ProjectsAdapter(var context: Context, var arrayList: ArrayList<Project>) :
    RecyclerView.Adapter<ProjectsAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_project_item, parent, false)
        return ItemHolder(itemHolder)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var listItem: Project = arrayList.get(position)

        holder.title.text = listItem.title
        holder.technologies.text = listItem.technologies
        holder.status.text = listItem.status
        holder.deadline.text = listItem.deadline
        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as? HomeActivity
            activity?.setCurrentFragment()
            activity?.loadMainFragment(SingleStackOverFlowQuestionFragment(listItem.link))

        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.my_project_title)
        var technologies: TextView = itemView.findViewById(R.id.github_link)
        var status: TextView = itemView.findViewById(R.id.status)
        var deadline: TextView = itemView.findViewById(R.id.deadline)

    }

    fun filterList(filteredList: ArrayList<Project>?) {
        arrayList = filteredList!!
        notifyDataSetChanged()
    }


}
