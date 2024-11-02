package com.example.roomdatabasewithpaging3.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.roomdatabasewithpaging3.Data.Dogs
import com.example.roomdatabasewithpaging3.databinding.EachRowBinding
import com.example.roomdatabasewithpaging3.ui.MainActivity
import javax.inject.Inject

class DogsAdapter @Inject constructor() :
    PagingDataAdapter<Dogs, DogsAdapter.DogsViewHolder>(DiffUtils) {
    private val favoriteImages = mutableListOf<String>()
    lateinit var context: MainActivity

    class DogsViewHolder(private val binding: EachRowBinding, private val adapter: DogsAdapter) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dogs: Dogs) {
            binding.apply {
                image.load(dogs.url)

                image.setOnClickListener {
                    showOptionsDialog(adapter.context, dogs.url)
                }
            }
        }

        private fun showOptionsDialog(context: Context, imageUrl: String) {
            val options = arrayOf("Share", "Add to favorites")

            AlertDialog.Builder(context)
                .setTitle("Image options")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> shareImage(context, imageUrl)
                        1 -> {
                            adapter.favoriteImage(context, imageUrl)
                            // Notify MainActivity about the new favorite
                            (context as? MainActivity)?.addToFavorites(imageUrl)
                        }
                    }
                }
                .show()
        }

        private fun shareImage(context: Context, imageUrl: String) {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Regardez ce chien adorable ! $imageUrl")
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(shareIntent, "Partager l'image"))
        }
    }

    fun favoriteImage(context: Context, imageUrl: String) {
        if (!favoriteImages.contains(imageUrl)) {
            favoriteImages.add(imageUrl)
            Toast.makeText(context, "Image ajoutée aux favoris : $imageUrl", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(context, "Cette image est déjà dans vos favoris.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    object DiffUtils : DiffUtil.ItemCallback<Dogs>() {
        override fun areItemsTheSame(oldItem: Dogs, newItem: Dogs): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Dogs, newItem: Dogs): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        val dogs = getItem(position)
        if (dogs != null) {
            holder.bind(dogs)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        return DogsViewHolder(
            EachRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), this
        )
    }
}