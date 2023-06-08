package com.example.foodtestapp.ui.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtestapp.core.FoodCategory
import com.example.foodtestapp.ui.view.listeners.OnCategoryClickListener
import com.example.foodtestapp.ui.view.listeners.OnTagClickListener
import online.example.foodtestapp.databinding.RvTagItemBinding

class TagAdapter(private val context: Context) :
    RecyclerView.Adapter<TagAdapter.TagItemViewHolder>(), BindableAdapter<String> {

    private var data: ArrayList<String> = ArrayList()
    private lateinit var listener: OnTagClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagItemViewHolder {
        val binding = RvTagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagAdapter.TagItemViewHolder, position: Int) {
        val tagItem = data[position]
        holder.bind(tagItem)
    }

    fun setClickListener(listener: OnTagClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: ArrayList<String>?) {
        data?.let {
            this.data = it
            notifyDataSetChanged()
        }
    }

    inner class TagItemViewHolder(rvTagItemBinding: RvTagItemBinding) :
        RecyclerView.ViewHolder(rvTagItemBinding.root) {
        private val binding = rvTagItemBinding

        fun bind(tagItem: String?) {
            binding.tvTag.text = tagItem.toString()

            binding.cvTag.setOnClickListener {
                listener.onTagClick(tagItem.toString())
            }
        }
    }

}
