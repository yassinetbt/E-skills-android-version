package io.eskills.Adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import io.eskills.Models.StackItem
import io.eskills.Models.ViewQuestionAnswer
import io.eskills.Models.ViewQuestionBody
import io.eskills.Models.VoteBody
import io.eskills.R
import io.eskills.Retrofit.RetrofitClient
import io.eskills.Views.Fragments.SingleQuestionFragment
import io.eskills.Views.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StackEskillsAdapter(var context: Context, var arrayList: ArrayList<StackItem>) :
    RecyclerView.Adapter<StackEskillsAdapter.ItemHolder>() {

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
        Glide.with(context!!).load(listItem.stack_profile_image)
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
        holder.votes_image.setBackgroundResource(R.drawable.ic_votes)
        holder.stack_question_top.text = topic
        holder.stack_profile_name.text = listItem.stack_profile_name
        holder.stack_profile_time.text = listItem.stack_profile_time
        holder.stack_question.text = listItem.stack_question
        holder.votes_number.text = listItem.votes_number
        holder.replies_number.text = listItem.replies_number
        holder.views_number.text = listItem.views_number
        holder.itemView.setOnClickListener {
            RetrofitClient.instanceConnected.viewQuestion(ViewQuestionBody(listItem.id_question!!)).enqueue(object :
                Callback<ViewQuestionAnswer> {
                override fun onResponse(call: Call<ViewQuestionAnswer>, response: Response<ViewQuestionAnswer>) {
                    if (response.code() == 200) {
                    }
                }

                override fun onFailure(call: Call<ViewQuestionAnswer>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
            val activity = holder.itemView.context as? HomeActivity
            activity?.setCurrentFragment()
            activity?.loadMainFragment(SingleQuestionFragment(listItem))


        }
        if (listItem.voted!!) {
            holder.votes_image.setBackgroundResource(R.drawable.ic_blue_votes)
        }
        holder.votes_image.setOnClickListener {
            if (listItem.voted == false) {
                holder.votes_image.setBackgroundResource(R.drawable.ic_blue_votes)
                holder.votes_number.text = (listItem.votes_number!!.toInt() + 1).toString()
                listItem.votes_number = (listItem.votes_number!!.toInt() + 1).toString()
                RetrofitClient.instanceConnected.voteAnswer(VoteBody(listItem.id_question!!, "up"))
                    .enqueue(
                        object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.code() == 200) {
                                    Toast.makeText(context,
                                        "Your vote is added successfully ",
                                        Toast.LENGTH_SHORT).show()
                                    listItem.voted = true
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                            }

                        })
            } else {
                holder.votes_image.setBackgroundResource(R.drawable.ic_votes)
                holder.votes_number.text = (listItem.votes_number!!.toInt() - 1).toString()
                listItem.votes_number = (listItem.votes_number!!.toInt() - 1).toString()
                RetrofitClient.instanceConnected.voteAnswer(VoteBody(listItem.id_question!!,
                    "down")).enqueue(
                    object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.code() == 200) {
                                Toast.makeText(context,
                                    "You canceled your vote",
                                    Toast.LENGTH_SHORT).show()
                                listItem.voted = false
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                        }

                    })
            }
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var stack_profile_image: ImageView = itemView.findViewById(R.id.stack_profile_image)
        var votes_image: ImageView = itemView.findViewById(R.id.votes_item)
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

}