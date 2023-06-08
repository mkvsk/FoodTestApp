package com.example.foodtestapp.ui.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodtestapp.core.Dish
import com.example.foodtestapp.ui.view.listeners.OnDishClickListener
import online.example.foodtestapp.databinding.RvDishesItemBinding

class DishAdapter(private val context: Context) :
    RecyclerView.Adapter<DishAdapter.DishItemViewHolder>(), BindableAdapter<Dish> {

    private var data: ArrayList<Dish> = ArrayList()
    private lateinit var listener: OnDishClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishItemViewHolder {
        val binding =
            RvDishesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DishItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DishItemViewHolder, position: Int) {
        val dishItem = data[position]
        holder.bind(dishItem)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: ArrayList<Dish>?) {
        data?.let {
            this.data = it
            notifyDataSetChanged()
        }
    }

    fun setClickListener(listener: OnDishClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class DishItemViewHolder(rvDishesItemBinding: RvDishesItemBinding) :
        RecyclerView.ViewHolder(rvDishesItemBinding.root) {
        private val binding = rvDishesItemBinding

        fun bind(dishItem: Dish?) {
            binding.tvName.text = dishItem!!.name.toString()

            Glide
                .with(context)
                .load(dishItem.image_url)
                .into(binding.ivImage)

            binding.cvDish.setOnClickListener {
                listener.onDishClick(dishItem)
            }

        }
    }

}
