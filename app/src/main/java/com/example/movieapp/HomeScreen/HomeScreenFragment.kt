package com.example.movieapp.HomeScreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeScreenBinding
import com.example.movieapp.models.Search
import com.example.movieapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private var binding: FragmentHomeScreenBinding? = null
    private val viewModel by viewModels<MovieBannerViewModel>()

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var adapter: MovieBannerAdapter
    private var adapterLatestMovie: LatestMovieAdapter = LatestMovieAdapter()
    private var adapterBatmanMovie: BatmanMovieAdapter = BatmanMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeScreenBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load Slide images
        movieBanner()
        // Load all latest movie
        latestMovie()
        // batman movie
        batmanMovie()
        initClickListener()
    }

    // go to listing page with View More Button
    private fun initClickListener() {
        binding?.viewMoreTV1?.setOnClickListener {
            val bundle = bundleOf(
                "superHeroKey" to "batman"
            )
            findNavController().navigate(R.id.latestMovieListingFragment, bundle)
        }

        binding?.viewMoreTV2?.setOnClickListener {
            val bundle = bundleOf(
                "superHeroKey" to "movie"
            )
            findNavController().navigate(R.id.latestMovieListingFragment, bundle)
        }
    }

    private fun batmanMovie() {
        // Load RecyclerView and call API
        adapterBatmanMovie = BatmanMovieAdapter()
        with(binding!!.batmanRV) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.HORIZONTAL
                )
            )
            adapter = adapterBatmanMovie
        }

        viewModel.getMovieBanners(s="Batman", apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
            adapterBatmanMovie.initLoad(model.Search)
        })

        // Function for individual superhero chip
        chipBatmanMovieChoice()

        // Item click and pass data via bundle
        adapterBatmanMovie.onItemClick = { model, position ->
            val bundle = bundleOf(
                "model" to model
            )
            findNavController().navigate(R.id.latestMovieDetailsFragment, bundle)
        }
    }

    private fun chipBatmanMovieChoice() {
        binding?.chipBatman?.setOnClickListener {
            viewModel.getMovieBanners(s="Batman", apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
                adapterBatmanMovie.initLoad(model.Search)
            })
        }

        binding?.chipSuperman?.setOnClickListener {
            viewModel.getMovieBanners(s="Superman", apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
                adapterBatmanMovie.initLoad(model.Search)
            })
        }

        binding?.chipHulk?.setOnClickListener {
            viewModel.getMovieBanners(s="Hulk", apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
                adapterBatmanMovie.initLoad(model.Search)
            })
        }

        binding?.chipIronMan?.setOnClickListener {
            viewModel.getMovieBanners(s="Iron Man", apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
                adapterBatmanMovie.initLoad(model.Search)
            })
        }

    }

    private fun movieBanner() {
        // initialize the slider with viewpager
        init()
        // API call and load 5 movie poster
        viewModel.getMovieBanners(s="avenger", apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
            val dataList: MutableList<Search> = mutableListOf()
            for (item in 1..5) {
                dataList.add(model.Search[item])
            }
            adapter.initLoad(dataList)
        })
    }

    private fun latestMovie() {
        // Load RecyclerView and API call
        adapterLatestMovie = LatestMovieAdapter()
        with(binding!!.latestRV) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.HORIZONTAL
                )
            )
            adapter = adapterLatestMovie
        }

        viewModel.getLatestMovie(s="movie", y=2022, apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
            adapterLatestMovie.initLoad(model.Search)
        })
        // Function for Individual chip call
        chipLatestChoice()

        // Item click and pass data via bundle
        adapterLatestMovie.onItemClick = { model, position ->
            val bundle = bundleOf(
                "model" to model
            )
            findNavController().navigate(R.id.latestMovieDetailsFragment, bundle)
        }
    }

    private fun chipLatestChoice() {

        binding?.chipYear?.setOnClickListener {
            viewModel.getLatestMovie(s="movie", y=2022, apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
                adapterLatestMovie.initLoad(model.Search)
            })
        }

        binding?.chipYear1?.setOnClickListener {
            viewModel.getLatestMovie(s="movie", y=2021, apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
                adapterLatestMovie.initLoad(model.Search)
            })
        }

        binding?.chipYear2?.setOnClickListener {
            viewModel.getLatestMovie(s="movie", y=2020, apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
                adapterLatestMovie.initLoad(model.Search)
            })
        }

        binding?.chipYear3?.setOnClickListener {
            viewModel.getLatestMovie(s="movie", y=2019, apikey = Constants.API_KEY).observe(viewLifecycleOwner, Observer { model->
                adapterLatestMovie.initLoad(model.Search)
            })
        }

    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    private fun init() {
        viewPager2 = binding!!.viewPager
        handler = Handler(Looper.getMainLooper())

        //adapter = MovieBannerAdapter(viewPager2)
        adapter = MovieBannerAdapter()

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        // For auto slide
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}