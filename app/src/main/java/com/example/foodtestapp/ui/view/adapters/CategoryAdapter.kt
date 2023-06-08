package com.example.foodtestapp.ui.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodtestapp.core.FoodCategory
import com.example.foodtestapp.ui.view.listeners.OnCategoryClickListener
import online.example.foodtestapp.databinding.RvCategoriesItemBinding

class CategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.CategoryItemViewHolder>(), BindableAdapter<FoodCategory> {

    private var data: ArrayList<FoodCategory> = ArrayList()
    private lateinit var listener: OnCategoryClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val binding =
            RvCategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val foodCategoryItem = data[position]
        holder.bind(foodCategoryItem)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: ArrayList<FoodCategory>?) {
        data?.let {
            this.data = it
            notifyDataSetChanged()
        }
    }

    fun setClickListener(listener: OnCategoryClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CategoryItemViewHolder(rvCategoriesItemBinding: RvCategoriesItemBinding) :
        RecyclerView.ViewHolder(rvCategoriesItemBinding.root) {
        private val binding = rvCategoriesItemBinding

        fun bind(foodCategoryItem: FoodCategory?) {
            binding.tvCategory.text = foodCategoryItem!!.name.toString()

            Glide
                .with(context)
                .load(foodCategoryItem.image_url)
                .into(binding.ivCategory)

            binding.cvCategory.setOnClickListener {
                listener.onCategoryClick()
            }

        }
    }


}