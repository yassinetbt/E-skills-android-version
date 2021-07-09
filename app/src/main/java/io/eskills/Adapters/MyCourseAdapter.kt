package io.eskills.Adapters

import AllCoursesResponse
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import io.eskills.R
import io.eskills.Views.Fragments.SingleCourseFragment
import io.eskills.Views.HomeActivity


class MyCourseAdapter(var context: Context, var arrayList: ArrayList<AllCoursesResponse>) :
    RecyclerView.Adapter<MyCourseAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_course_item, parent, false)
        return ItemHolder(itemHolder)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var listItem: AllCoursesResponse = arrayList.get(position)

        Glide.with(context).load(listItem.image)
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
                ) {
                    holder.img.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
        holder.title.text = listItem.title
        holder.about.text = listItem.description.substring(0, 150) + " ..."

        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as? HomeActivity
            activity?.setCurrentFragment()
            activity?.loadMainFragment(SingleCourseFragment(listItem._id))

        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: LinearLayout = itemView.findViewById(R.id.my_course_image)
        var title: TextView = itemView.findViewById(R.id.my_course_title)
        var about: TextView = itemView.findViewById(R.id.my_course_about)

    }

    fun filterList(filteredList: ArrayList<AllCoursesResponse>?) {
        arrayList = filteredList!!
        notifyDataSetChanged()
    }


}