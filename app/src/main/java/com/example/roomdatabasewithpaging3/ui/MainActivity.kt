package com.example.roomdatabasewithpaging3.ui


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasewithpaging3.Adapter.DogsAdapter
import com.example.roomdatabasewithpaging3.Adapter.FavoritesAdapter

import com.example.roomdatabasewithpaging3.R
import com.example.roomdatabasewithpaging3.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var dogsAdapter: DogsAdapter

    private val favoriteDogsImages: ArrayList<String> = ArrayList()

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

        dogsAdapter.context = this

        lifecycleScope.launchWhenStarted {
            mainViewModel.getAllDogs().collectLatest { response ->
                binding.apply {
                    recyclerview.isVisible = true
                }
                Log.d("main", "onCreate: $response")
                dogsAdapter.submitData(response)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_about -> {
                showAppDescriptionDialog()
                true
            }
            R.id.action_favorites -> {
                showFavoritesList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAppDescriptionDialog() {
        AlertDialog.Builder(this)
            .setTitle("App description")
            .setMessage("This application retrieves dog images from an API, stores them in a local database, and displays them with pagination. You can also mark your favorite dogs!")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }


    private fun showFavoritesList() {
        if (favoriteDogsImages.isEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Favorite Dogs")
                .setMessage("No favorite dog found.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
            return
        }

        val dialogView = layoutInflater.inflate(R.layout.dialog_favorit, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recyclerViewFavorites)

        // Function to initialize or update the adapter
        fun updateFavoritesAdapter() {
            val favoritesAdapter = FavoritesAdapter(favoriteDogsImages) { imageUrl ->
                favoriteDogsImages.remove(imageUrl)
                updateFavoritesAdapter() // Refresh the adapter with updated list
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
            recyclerView.adapter = favoritesAdapter
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        updateFavoritesAdapter()

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }



    fun addToFavorites(imageUrl: String) {
        favoriteDogsImages.add(imageUrl)
        Log.d("Favorites", "Image added to favorites: $imageUrl") // Log added for debugging
        Toast.makeText(this, "Image ajoutée aux favoris : $imageUrl", Toast.LENGTH_SHORT).show()
    }




    private fun initRecyclerView() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = dogsAdapter
            }
        }
    }
}