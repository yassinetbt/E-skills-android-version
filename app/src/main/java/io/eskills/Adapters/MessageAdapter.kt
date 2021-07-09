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
import io.eskills.Models.MessageItem
import io.eskills.R
import io.eskills.Views.Fragments.MessagingBoxFragment
import io.eskills.Views.HomeActivity

class MessageAdapter(var context: Context, var arrayList: ArrayList<MessageItem>) :
    RecyclerView.Adapter<MessageAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_message, parent, false)
        return ItemHolder(itemHolder)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var listItem: MessageItem = arrayList.get(position)

        Glide.with(context).load(listItem.image)
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?,
                ) {
                    holder.profileImage.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
        holder.messageText.text = listItem.message
        holder.messageTime.text = listItem.time
        holder.messageName.text = listItem.name
        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as? HomeActivity
            activity?.setCurrentFragment()
            activity?.loadMainFragment(MessagingBoxFragment())

        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileImage: ImageView = itemView.findViewById(R.id.message_profile_image)
        var messageText: TextView = itemView.findViewById(R.id.message_text)
        var messageTime: TextView = itemView.findViewById(R.id.message_profile_time)
        var messageName: TextView = itemView.findViewById(R.id.message_profile_name)

    }

    fun filterList(filteredList: ArrayList<MessageItem>?) {
        arrayList = filteredList!!
        notifyDataSetChanged()
    }


}