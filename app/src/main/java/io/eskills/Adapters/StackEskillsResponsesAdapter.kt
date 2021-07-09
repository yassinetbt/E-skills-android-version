package io.eskills.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.eskills.Models.QuestionResponseItem
import io.eskills.Models.VoteBody
import io.eskills.R
import io.eskills.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StackEskillsResponsesAdapter(
    var context: Context,
    var arrayList: ArrayList<QuestionResponseItem>,
) :
    RecyclerView.Adapter<StackEskillsResponsesAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.stack_question_response, parent, false)
        return ItemHolder(itemHolder)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var listItem: QuestionResponseItem = arrayList[position]
        //todo
        //Glide.with(context).load(listItem.stack_profile_image)
        //    .into(object : CustomTarget<Drawable?>() {
        //        override fun onResourceReady(
        //            resource: Drawable,
        //            transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
        //        ) {
        //            holder.stack_profile_image.background = resource
        //        }
//
        //        override fun onLoadCleared(placeholder: Drawable?) {
//
        //        }
//
        //    })

        holder.stack_profile_name.text = listItem.stack_profile_name
        holder.stack_profile_time.text = listItem.stack_profile_time
        holder.stack_question.text = listItem.stack_question
        holder.votes_number.text = listItem.votes_number
        holder.views_number.text = listItem.views_number
        if (listItem.accepted == true) {
            holder.accepted.setBackgroundResource(R.drawable.ic_ticked)
        } else {
            holder.accepted.setBackgroundResource(R.drawable.ic_false_answer)
        }
        holder.votes_image.setOnClickListener {
            if (listItem.voted == false) {
                holder.votes_image.setBackgroundResource(R.drawable.ic_blue_votes)
                holder.votes_number.text = (listItem.votes_number!!.toInt() + 1).toString()
                listItem.votes_number = (listItem.votes_number!!.toInt() + 1).toString()
                RetrofitClient.instanceConnected.voteAnswer(VoteBody(listItem.id!!, "up")).enqueue(
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
                RetrofitClient.instanceConnected.voteAnswer(VoteBody(listItem.id!!, "down"))
                    .enqueue(
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
        var stack_profile_image: ImageView = itemView.findViewById(R.id.response_profile_image)
        var votes_image: ImageView = itemView.findViewById(R.id.votes_response_item)
        var stack_profile_name: TextView = itemView.findViewById(R.id.response_profile_name)
        var stack_profile_time: TextView = itemView.findViewById(R.id.response_profile_time)
        var stack_question: TextView = itemView.findViewById(R.id.response_text)
        var votes_number: TextView = itemView.findViewById(R.id.response_votes_number)
        var views_number: TextView = itemView.findViewById(R.id.response_views_number)
        var accepted: ImageView = itemView.findViewById(R.id.question_accepted)
    }

    fun filterList(filteredList: ArrayList<QuestionResponseItem>?) {
        arrayList = filteredList!!
        notifyDataSetChanged()

    }

}