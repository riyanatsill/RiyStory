package com.example.riystory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.riystory.R
import com.example.riystory.data.response.ListStoryItem
import com.example.riystory.databinding.ItemStoryBinding

class StoryAdapter (private val listener: Listener) :
    PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    inner class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem) {
            val radius = 24

            val requestOptions = RequestOptions()
                .transform(RoundedCorners(radius))
                .placeholder(android.R.color.darker_gray)
                .error(R.drawable.baseline_broken_image_24)

            Glide.with(binding.imgHome.context)
                .load(item.photoUrl)
                .apply(requestOptions)
                .into(binding.imgHome)

            binding.txtNameHome.text = item.name
            binding.txtDescHome.text = item.description

            binding.root.setOnClickListener {
                listener.onListener(item)
            }
        }
    }

    interface Listener {
        fun onListener(story: ListStoryItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}