package com.example.movieapp.ListingPage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemViewMovieListBinding
import com.example.movieapp.models.Search

class LatestMovieListingAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val dataList: MutableList<Search> = mutableListOf()
    var onItemClick: ((model:Search, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemViewMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModel(binding)
    }

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewModel) {
            val model = dataList[position]
            val binding = holder.binding

            val imageUrl = model.poster
            binding.movieIV.let { view ->
                Glide.with(view)
                    .load(imageUrl)
                    .into(view)
            }

            binding.movieNameTV.text = model.title
        }
    }

    inner class ViewModel(val binding: ItemViewMovieListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    // By clicking, invoke data and pass through the fragment
                    onItemClick?.invoke(dataList[absoluteAdapterPosition], absoluteAdapterPosition)
                }
            }
        }
    }

    // Load data for RecyclerView
    fun initLoad(list: List<Search>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    // Load data for pagination
    fun pagingLoad(list: List<Search>) {
        val currentIndex = dataList.size
        val newDataCount = list.size
        dataList.addAll(list)
        notifyItemRangeInserted(currentIndex, newDataCount)
    }

}


