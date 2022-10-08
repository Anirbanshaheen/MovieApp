package com.example.movieapp.HomeScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemViewBatmanMovieBinding
import com.example.movieapp.models.Search

class BatmanMovieAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val dataList: MutableList<Search> = mutableListOf()
    var onItemClick: ((model:Search, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemViewBatmanMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModel(binding)
    }

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewModel) {
            val model = dataList[position]
            val binding = holder.binding

            val imageUrl = model.poster
            binding.movieBatmanIV.let { view ->
                Glide.with(view)
                    .load(imageUrl)
                    .into(view)
            }
        }
    }

    inner class ViewModel(val binding: ItemViewBatmanMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(dataList[absoluteAdapterPosition], absoluteAdapterPosition)
                }
            }
        }
    }

    // For data load
    fun initLoad(list: List<Search>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }
}


