package io.eskills.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.eskills.Models.LiveMessageItem
import io.eskills.R


class LiveMessageAdapter(private val context: Context, list: ArrayList<LiveMessageItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: ArrayList<LiveMessageItem>

    private inner class MessageOutViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var live_message: TextView
        var live_profile_image: ImageView
        fun bind(position: Int) {
            val liveMessageItem: LiveMessageItem = list[position]
            live_message.text = liveMessageItem.live_message
            live_profile_image.setImageResource(liveMessageItem.live_profile_image!!)
        }

        init {
            live_message = itemView.findViewById(R.id.live_message_out)
            live_profile_image = itemView.findViewById(R.id.live_profile_image_out)
        }
    }

    private inner class MessageInViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var live_message: TextView
        var live_profile_image: ImageView
        fun bind(position: Int) {
            val liveMessageItem: LiveMessageItem = list[position]
            live_message.text = liveMessageItem.live_message
            live_profile_image.setImageResource(liveMessageItem.live_profile_image!!)
        }

        init {
            live_message = itemView.findViewById(R.id.live_message_in)
            live_profile_image = itemView.findViewById(R.id.live_profile_image_in)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MESSAGE_TYPE_IN) {
            MessageInViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.live_message_in_item, parent, false)
            )
        } else MessageOutViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.live_message_out_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].typemessage === MESSAGE_TYPE_IN) {
            (holder as MessageInViewHolder).bind(position)
        } else {
            (holder as MessageOutViewHolder).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].typemessage
    }

    companion object {
        const val MESSAGE_TYPE_IN = 1
        const val MESSAGE_TYPE_OUT = 2
    }

    init {
        this.list = list
    }
}