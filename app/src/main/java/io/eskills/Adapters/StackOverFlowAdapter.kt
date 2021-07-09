package io.eskills.Adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import io.eskills.Models.StackItem
import io.eskills.R
import io.eskills.Views.Fragments.SingleStackOverFlowQuestionFragment
import io.eskills.Views.HomeActivity

class StackOverFlowAdapter(var context: Context, var arrayList: ArrayList<StackItem>) :
    RecyclerView.Adapter<StackOverFlowAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.stack_item_grid, parent, false)
        return ItemHolder(itemHolder)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var listItem: StackItem = arrayList[position]
        var topic: String
        if (listItem.stack_title?.length!! > 25) {
            topic = listItem.stack_title?.substring(0, 25) + "..."
        } else {
            topic = listItem.stack_title.toString() + "..."
        }
        Glide.with(context).load(listItem.stack_profile_image)
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
                ) {
                    holder.stack_profile_image.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
        holder.stack_question_top.text = topic
        holder.stack_profile_name.text = listItem.stack_profile_name
        holder.stack_profile_time.text = listItem.stack_profile_time
        holder.stack_question.text = listItem.stack_question
        holder.votes_number.text = listItem.votes_number
        holder.replies_number.text = listItem.replies_number
        holder.views_number.text = listItem.views_number
        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as? HomeActivity
            activity?.setCurrentFragment()
            activity?.loadMainFragment(SingleStackOverFlowQuestionFragment(listItem.url))

        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var stack_profile_image: ImageView = itemView.findViewById(R.id.stack_profile_image)
        var stack_question_top: TextView = itemView.findViewById(R.id.stack_question_top)
        var stack_profile_name: TextView = itemView.findViewById(R.id.stack_profile_name)
        var stack_profile_time: TextView = itemView.findViewById(R.id.stack_profile_time)
        var stack_question: TextView = itemView.findViewById(R.id.stack_question)
        var votes_number: TextView = itemView.findViewById(R.id.votes_number)
        var replies_number: TextView = itemView.findViewById(R.id.replies_number)
        var views_number: TextView = itemView.findViewById(R.id.views_number)
    }

    fun filterList(filteredList: ArrayList<StackItem>?) {
        arrayList = filteredList!!
        notifyDataSetChanged()

    }

    fun addToList(filteredList: ArrayList<StackItem>?, position: Int) {
        arrayList = filteredList!!
        notifyItemInserted(position + 1)

    }
}