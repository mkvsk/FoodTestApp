package com.example.foodtestapp.ui.util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageButton
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.foodtestapp.core.Dish
import online.example.foodtestapp.R


class AppDialogBuilder {
    private lateinit var dialogView: View
    private lateinit var dialog: AlertDialog

    @SuppressLint("InflateParams")
    fun getDishInfoDialog(
        dish: Dish,
        context: Context,
        inflater: LayoutInflater, listener: AppDialogListener
    ): AlertDialog {
        dialogView = inflater.inflate(R.layout.dish_info, null)
        val image = dialogView.findViewById<ImageView>(R.id.ivImage)
        val name = dialogView.findViewById<TextView>(R.id.tvName)
        val price = dialogView.findViewById<TextView>(R.id.tvPrice)
        val weight = dialogView.findViewById<TextView>(R.id.tvWeight)
        val description = dialogView.findViewById<TextView>(R.id.tvDescription)
        val btnAddToBag = dialogView.findViewById<Button>(R.id.btnAddToBag)
        val btnAddToFav = dialogView.findViewById<AppCompatImageButton>(R.id.btnAddToFav)
        val btnClose = dialogView.findViewById<AppCompatImageButton>(R.id.btnClose)

        Glide.with(context)
            .load(dish.image_url)
            .into(image)
        name.text = dish.name.toString()
        price.text =
            String.format(context.getString(R.string.dish_price_format), dish.price.toString())
        weight.text =
            String.format(context.getString(R.string.dish_weight_format), dish.weight.toString())
        description.text = dish.description.toString()
        btnAddToBag.setOnClickListener {
            listener.onClick(true)
            dialog.dismiss()
        }
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog = AlertDialog.Builder(context).setView(dialogView).create()
        dialog.window?.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                context,
                R.drawable.dialog_background_inset
            )
        )
        return dialog
    }
}
