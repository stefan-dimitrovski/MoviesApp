package com.stefan.lab3.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stefan.lab3.databinding.FragmentSecondBinding
import com.stefan.lab3.domain.movie.model.Movie
import com.stefan.lab3.ui.search.ViewModelProviderFactory

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var secondFragmentViewModel: SecondFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = ViewModelProviderFactory(requireContext())
        secondFragmentViewModel =
            ViewModelProvider(this, viewModelFactory).get(SecondFragmentViewModel::class.java)

        secondFragmentViewModel.getMovieById(arguments?.getString("movieId")!!)

        secondFragmentViewModel.getMovieMutableLiveData().observe(viewLifecycleOwner) {
            initMovieData(it)
        }

    }

    private fun initMovieData(it: Movie) {
        Glide.with(this).load(it.Poster).into(binding.ivDetailsMoviePoster)
        binding.tvDetailsMovieTitle.text = it.Title
        binding.tvDetailsMovieDirector.text = it.Director
        binding.tvDetailsMovieReleased.text = it.Released
        binding.tvDetailsMovieRuntime.text = it.Runtime
        binding.tvDetailsMovieRated.text = it.Rated
        binding.tvDetailsMovieGenre.text = it.Genre
        binding.tvDetailsMoviePlot.text = it.Plot
        binding.tvDetailsMovieActors.text = it.Actors
        binding.tvDetailsMovieImdbRating.text = it.imdbRating
        binding.tvDetailsMovieBoxOffice.text = it.BoxOffice
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}