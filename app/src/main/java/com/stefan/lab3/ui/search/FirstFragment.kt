package com.stefan.lab3.ui.search

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stefan.lab3.R
import com.stefan.lab3.databinding.FragmentFirstBinding
import com.stefan.lab3.domain.movie.model.Movie
import com.stefan.lab3.ui.MoviesRecyclerViewAdapter

class FirstFragment : Fragment(), CellClickListener {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var firstFragmentViewModel: FirstFragmentViewModel
    private val moviesAdapter = MoviesRecyclerViewAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = ViewModelProviderFactory(requireContext())
        firstFragmentViewModel =
            ViewModelProvider(this, viewModelFactory).get(FirstFragmentViewModel::class.java)

        binding.rvMoviesList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvMoviesList.adapter = moviesAdapter

        firstFragmentViewModel.getMovieMutableLiveData().observe(viewLifecycleOwner) {
            moviesAdapter.updateMovies(it)
            binding.rvMoviesList.scrollToPosition(firstFragmentViewModel.getMovieMutableLiveData().value!!.size - 1)
        }

        binding.etMovieTitle.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                val title: String = binding.etMovieTitle.text.toString()
                activity?.hideSoftKeyboard()
                firstFragmentViewModel.search(title)
                binding.etMovieTitle.text.clear()
                true
            } else {
                Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCellClickListener(data: Movie) {
        val bundle = bundleOf("movieId" to data.imdbID)
        view?.findNavController()?.navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }
}

fun Activity.hideSoftKeyboard() {
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}

interface CellClickListener {
    fun onCellClickListener(data: Movie)
}