package com.example.dreamlog

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DreamListAdapter : ListAdapter<Dream, DreamListAdapter.DreamViewHolder> (DreamComparator()) {


    class DreamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView : TextView = itemView.findViewById(R.id.textView_title)
        val idTextView : TextView = itemView.findViewById(R.id.textView_id)

        // write a helper function that takes a string and a text view
        // assign the text to the text view
        fun bindText (text:String?, textView: TextView){
            textView.text = text
        }

        companion object{
            // could put in onCreateViewHolder as well
            fun create(parent:ViewGroup) : DreamViewHolder{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dream, parent, false)
                return DreamViewHolder(view)
            }
        }
    }

    class DreamComparator : DiffUtil.ItemCallback<Dream>(){
        override fun areContentsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            // compare the primary key
            return oldItem === newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DreamViewHolder {
        return DreamViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DreamViewHolder, position: Int) {
        val currentTask = getItem(position)
        holder.bindText(currentTask.title, holder.titleTextView)
        holder.bindText(currentTask.id.toString(), holder.idTextView)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("id", currentTask.id)
            holder.itemView.context.startActivity(intent)
        }
    }

}