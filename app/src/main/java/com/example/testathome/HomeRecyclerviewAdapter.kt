package com.example.testathome

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testathome.models.Item

class HomeRecyclerviewAdapter:RecyclerView.Adapter<HomeRecyclerviewAdapter.HomeItemViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        //둘이 같은 객체인지
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.address == newItem.address
        }

        //둘이 같은 아이템인지
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeItemViewHolder {
        return HomeItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_item,parent,false))
    }

    override fun onBindViewHolder(
        holder: HomeItemViewHolder,
        position: Int
    ) {
        val item = differ.currentList[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(item) }
        }
        Log.d("inonBindViewHolder: ",item.title)
        holder.bind(item)

    }

    override fun getItemCount(): Int = differ.currentList.size


    class HomeItemViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        fun bind(item:Item){
            itemView.findViewById<TextView>(R.id.restaurnat_name_textview).text=item.title
            itemView.findViewById<TextView>(R.id.roadAddress_textview).text=item.roadAddress
        }

    }

    private var onItemClickListener:((Item)->Unit)?=null

    fun setOnItemClickListener(listener : (Item)->Unit){
        onItemClickListener = listener
    }
}