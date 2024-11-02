package com.example.roomdatabasewithpaging3.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.roomdatabasewithpaging3.R
class FavoritesAdapter(
    private val images: MutableList<String>,
    private val removeItem: (String) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_image, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val buttonRemove: ImageButton = itemView.findViewById(R.id.buttonRemove)

        fun bind(imageUrl: String) {
            imageView.load(imageUrl)

            // Handle the remove button click
            buttonRemove.setOnClickListener {
                removeItem(imageUrl)
            }
        }
    }
}