package io.eskills.Adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.eskills.Models.ChapterItem
import io.eskills.R
import io.eskills.Views.Fragments.SingleChapterFragment
import io.eskills.Views.HomeActivity


class ChapterAdapter(
    var context: Context,
    var arrayList: ArrayList<ChapterItem>,
) :
    RecyclerView.Adapter<ChapterAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.chapter_item, parent, false)
        return ItemHolder(itemHolder)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var selectedPosition = -1
        val listItem: ChapterItem = arrayList.get(position)
        val lectures = Array<String>(listItem.lectures?.size!!) { "it = $it" }
        val descriptions: ArrayList<String> = ArrayList()
        var i = 0
        for (lecture in listItem.lectures!!) {
            lectures[i] = lecture.title
            i++
            descriptions.add(lecture.description)
        }
        holder.play.setBackgroundResource(listItem.play!!)
        holder.chapters_title.text = listItem.chapters_title
        holder.length.text = listItem.length
        holder.tickline.setBackgroundResource(listItem.tickline!!)
        holder.tick.setBackgroundResource(listItem.tick!!)
        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as? HomeActivity
            activity?.setCurrentFragment()
            AlertDialog.Builder(context)
                .setTitle("Lectures")
                .setSingleChoiceItems(lectures, -1, null)
                .setPositiveButton("OK"
                ) { dialog, id ->
                    selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
                    if (selectedPosition == -1) {
                        Toast.makeText(context, "Please select a lecture", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        activity?.loadMainFragment(SingleChapterFragment(listItem.title!!,
                            lectures[selectedPosition],
                            descriptions[selectedPosition]))
                    }
                }
                .show()


        }

    }


    override fun getItemCount(): Int {
        return arrayList.size
    }


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var play: ImageView = itemView.findViewById(R.id.played)
        var chapters_title: TextView = itemView.findViewById(R.id.chapters_title)
        var length: TextView = itemView.findViewById(R.id.length)
        var tickline: TextView = itemView.findViewById(R.id.tickline)
        var tick: ImageView = itemView.findViewById(R.id.tick)
    }

}