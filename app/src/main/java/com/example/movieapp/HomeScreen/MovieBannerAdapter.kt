package com.example.movieapp.HomeScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemViewMovieBannerBinding
import com.example.movieapp.models.Search

// private val viewPager2: ViewPager2
class MovieBannerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val dataList: MutableList<Search> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemViewMovieBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModel(binding)
    }

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewModel) {
            val model = dataList[position]
            val binding = holder.binding

            Glide.with(binding.movieIV).load(model.poster).into(binding.movieIV)
            binding.movieTV.text = model.title

        }
    }

    /*private val runnable = Runnable {
        dataList.addAll(dataList)
        notifyDataSetChanged()
    }*/

    inner class ViewModel(val binding: ItemViewMovieBannerBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    // For data load
    fun initLoad(list: List<Search>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

}


