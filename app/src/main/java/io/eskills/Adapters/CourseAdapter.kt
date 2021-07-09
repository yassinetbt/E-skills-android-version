package io.eskills.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import io.eskills.Models.CourseItem
import io.eskills.R
import io.eskills.Views.Fragments.SingleCourseFragment
import io.eskills.Views.Fragments.SingleQuestionFragment
import io.eskills.Views.HomeActivity


class CourseAdapter(var context: Context, var arrayList: ArrayList<CourseItem>) :
    RecyclerView.Adapter<CourseAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_item_grid, parent, false)
        return ItemHolder(itemHolder)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var listItem: CourseItem = arrayList.get(position)

        Glide.with(context).load(listItem.img)
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
        holder.videos.text = listItem.videos
        holder.project.text = listItem.project
        holder.saved.text = listItem.saved
        holder.state.text = listItem.state
        holder.cardColor.setBackgroundResource(listItem.imageColor!!)
        holder.itemView.setOnClickListener {
            if(listItem.state=="Buy now"){
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("Course not available")
                builder.setMessage("This course is not available you need to purchase to open it")
                    .setCancelable(false)
                    .setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, id ->
                           dialog.dismiss()
                        })
                val alert: AlertDialog = builder.create()
                alert.show()
            }
            else{
                val activity = holder.itemView.context as? HomeActivity
                activity?.setCurrentFragment()
                activity?.loadMainFragment(SingleCourseFragment(listItem.id))
            }


        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: LinearLayout = itemView.findViewById(R.id.course_image)
        var title: TextView = itemView.findViewById(R.id.course_title)
        var videos: TextView = itemView.findViewById(R.id.course_videos)
        var project: TextView = itemView.findViewById(R.id.course_project)
        var saved: TextView = itemView.findViewById(R.id.course_saved)
        var state: TextView = itemView.findViewById(R.id.state)
        var cardColor: LinearLayout = itemView.findViewById(R.id.card_color)
    }

    fun filterList(filteredList: ArrayList<CourseItem>?) {
        arrayList = filteredList!!
        notifyDataSetChanged()
    }
}