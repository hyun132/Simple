package com.example.testathome

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.testathome.databinding.HomeItemBinding
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
        return HomeItemViewHolder(HomeItemBinding.inflate(LayoutInflater.from(parent.context)))
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


    class HomeItemViewHolder(private var binding:HomeItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(item:Item){
            binding.item=item
            binding.executePendingBindings() // binding에 필요한 모든 작업들 즉시 실행하도록 강제하는..?
        }

    }

    private var onItemClickListener:((Item)->Unit)?=null

    fun setOnItemClickListener(listener : (Item)->Unit){
        onItemClickListener = listener
    }

}