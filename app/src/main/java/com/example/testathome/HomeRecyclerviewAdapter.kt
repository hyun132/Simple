package com.example.testathome

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testathome.databinding.SearchItemBinding
import com.example.testathome.models.Item

class HomeRecyclerviewAdapter:RecyclerView.Adapter<HomeRecyclerviewAdapter.HomeItemViewHolder>() {

    interface LikedClick
    {
        fun onClick(view: View, item: Item)
    }

    var likedClick: LikedClick? = null

    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        //둘이 같은 객체인지
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id== newItem.id
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
        val binding =SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeItemViewHolder,
        position: Int
    ) {
        val item = differ.currentList[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(item) }
        }
        //아이템 클릭했을 때 이 부분 어디로 빼야할지 고민..
        holder.itemView.findViewById<ImageView>(R.id.iv_liked).setOnClickListener {v->
            likedClick?.onClick(v,differ.currentList[position])

        }
        holder.bind(item)

    }

    override fun getItemCount(): Int = differ.currentList.size


    class HomeItemViewHolder(private var binding:SearchItemBinding) :RecyclerView.ViewHolder(binding.root){
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