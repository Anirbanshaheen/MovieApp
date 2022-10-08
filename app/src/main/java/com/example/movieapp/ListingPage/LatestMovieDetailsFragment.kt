package com.example.movieapp.ListingPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movieapp.HomeScreen.MovieBannerViewModel
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentLatestMovieDetailsListingBinding
import com.example.movieapp.models.Search
import com.example.movieapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LatestMovieDetailsFragment : Fragment() {

    private var binding: FragmentLatestMovieDetailsListingBinding? = null
    private val viewModel by viewModels<MovieDetailsViewModel>()
    private lateinit var model : Search

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentLatestMovieDetailsListingBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get model from Listing page and HomeScreen page
        val bundle: Bundle? = arguments
        bundle?.let {
            model = it.getParcelable("model")!!
        }

        initData()
        initClickListener()
    }

    private fun initData() {
        // call movie details api for specific movie
        viewModel.getMovieDetails(model.title?:"", model.year?.toInt()?:2000, Constants.API_KEY)

        // observe that movie data and show them
        viewModel.data.observe(viewLifecycleOwner, Observer { data ->

            binding?.movieIV.let { view ->
                Glide.with(view!!)
                    .load(data.poster)
                    .into(view)
            }

            binding?.movieTV?.text = data.title
            binding?.movieReleaseTV?.text = "Released on: ${data.year}"
            binding?.movieDirectorTV?.text = "Director: ${data.director}"
            binding?.movieGenreTV?.text = "Genre: ${data.genre}"
            binding?.movieDetailsTV?.text = data.plot

        })

    }

    private fun initClickListener() {
        // go to video streaming fragment
        binding?.movieIV?.setOnClickListener {
            findNavController().navigate(R.id.videoStreamFragment)
        }
    }

}