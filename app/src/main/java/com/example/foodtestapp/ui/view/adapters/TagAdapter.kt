package com.example.foodtestapp.ui.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtestapp.ui.view.listeners.OnTagClickListener
import com.google.android.material.card.MaterialCardView
import online.example.foodtestapp.R
import online.example.foodtestapp.databinding.RvTagItemBinding

class TagAdapter(private val context: Context) :
    RecyclerView.Adapter<TagAdapter.TagItemViewHolder>() {

    private var data: HashSet<String> = HashSet()
    private lateinit var listener: OnTagClickListener
    var selectedTag: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagItemViewHolder {
        val binding = RvTagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagAdapter.TagItemViewHolder, position: Int) {
        val tagItem = data.elementAt(position)
        holder.bind(tagItem)
    }

    fun setClickListener(listener: OnTagClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: HashSet<String>?) {
        data?.let {
            this.data = it
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelected(position: Int) {
        selectedTag = position
    }

    inner class TagItemViewHolder(rvTagItemBinding: RvTagItemBinding) :
        RecyclerView.ViewHolder(rvTagItemBinding.root) {
        private val binding = rvTagItemBinding

        fun bind(tagItem: String?) {
            if (selectedTag == bindingAdapterPosition) {
                selectItem(binding.cvTag, binding.tvTag)
            } else {
                unselectItem(binding.cvTag, binding.tvTag)
            }

            binding.tvTag.text = tagItem.toString()

            binding.cvTag.setOnClickListener {
                listener.onTagClick(
                    selectedTag,
                    bindingAdapterPosition,
                    tagItem.toString()
                )
            }
        }
    }

    private fun selectItem(cardView: MaterialCardView, textView: TextView) {
        cardView.setBackgroundResource(R.drawable.selected_tag_item)
        cardView.isClickable = false
        textView.isSelected = true
    }

    private fun unselectItem(cardView: MaterialCardView, textView: TextView) {
        cardView.setBackgroundResource(R.drawable.unselected_tag_item)
        cardView.isClickable = true
        textView.isSelected = false
    }

}
