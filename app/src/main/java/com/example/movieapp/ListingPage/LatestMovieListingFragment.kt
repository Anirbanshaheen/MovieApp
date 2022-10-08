package com.example.movieapp.ListingPage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.HomeScreen.MovieBannerViewModel
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentLatestMovieListingBinding
import com.example.movieapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LatestMovieListingFragment : Fragment() {

    private var binding: FragmentLatestMovieListingBinding? = null
    private val viewModel by viewModels<MovieBannerViewModel>()
    private val visibleThreshold = 3
    private var searchValue = ""
    private var totalCount = 0
    private var page = 1
    private var latestMovieListingAdapter: LatestMovieListingAdapter = LatestMovieListingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentLatestMovieListingBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get bundle value from HomeScreenFragment
        val bundle: Bundle? = arguments
        bundle?.let {
            searchValue = it.getString("superHeroKey")!!
        }

        // Load data
        initView()
        // Observer dataList for pagination
        initObservers()
    }

    private fun initObservers() {
        viewModel.dataList.observe(viewLifecycleOwner, Observer { model ->
            totalCount = Integer.parseInt(model.totalResults)
            if (page==1) {
                latestMovieListingAdapter.initLoad(model.Search)
            } else {
                latestMovieListingAdapter.pagingLoad(model.Search)
            }
        })
    }

    private fun initView() {
        binding?.latestMovieListingRV?.let { view ->
            with(view) {
                setHasFixedSize(false)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = latestMovieListingAdapter
            }
        }

        // Checking bundle value and load data with pagination
        if (searchValue == "movie") {
            viewModel.getLatestMoviePagination(s=searchValue, page = page, y=2022, apikey = Constants.API_KEY)

            binding?.latestMovieListingRV?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val currentItemCount = recyclerView.layoutManager?.itemCount ?: 0
                        val lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (currentItemCount <= lastVisibleItem + visibleThreshold && currentItemCount < totalCount) {
                            // load page with pagination
                            if (page <= 9) {
                                page++
                                viewModel.getLatestMoviePagination(s=searchValue, page = page, y=2022, apikey = Constants.API_KEY)
                            }
                        }
                    }
                }
            })
        } else if (searchValue == "batman") {
            viewModel.getLatestMoviePagination(s=searchValue, page = page, y=2022, apikey = Constants.API_KEY)

            binding?.latestMovieListingRV?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val currentItemCount = recyclerView.layoutManager?.itemCount ?: 0
                        val lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (currentItemCount <= lastVisibleItem + visibleThreshold && currentItemCount < totalCount) {
                            // load page with pagination
                            if (page <= 1) {
                                page++
                                viewModel.getLatestMoviePagination(s=searchValue, page = page, y=2022, apikey = Constants.API_KEY)
                            }
                        }
                    }
                }
            })
        }

        latestMovieListingAdapter.onItemClick = { model, position ->
            val bundle = bundleOf(
                "model" to model
            )
            findNavController().navigate(R.id.latestMovieDetailsFragment, bundle)
        }
    }

}