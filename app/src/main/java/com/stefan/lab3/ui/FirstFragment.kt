package com.stefan.lab3.ui

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
import com.stefan.lab3.adapters.MoviesRecyclerViewAdapter
import com.stefan.lab3.R
import com.stefan.lab3.databinding.FragmentFirstBinding
import com.stefan.lab3.models.Movie
import com.stefan.lab3.viewmodels.FirstFragmentViewModel

class FirstFragment : Fragment(), CellClickListener {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val movies: MutableList<Movie> = mutableListOf()
    private lateinit var firstFragmentViewModel: FirstFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movies.clear()

        firstFragmentViewModel = ViewModelProvider(this).get(FirstFragmentViewModel::class.java)
        firstFragmentViewModel.getMovieMutableLiveData()
            .observe(viewLifecycleOwner,
                { t ->
                    if (t != null) {
                        displayData(t)
                    }
                }
            )

        binding.rvMoviesList.layoutManager = LinearLayoutManager(activity)
        binding.rvMoviesList.adapter = MoviesRecyclerViewAdapter(movies, this)

        binding.etMovieTitle.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                val title: String = binding.etMovieTitle.text.toString()
                activity?.hideSoftKeyboard()
                firstFragmentViewModel.searchMovieByTitle(title)
                true
            } else {
                Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                false
            }
        }
    }

    private fun displayData(body: Movie) {
        movies.add(body)
        binding.rvMoviesList.adapter?.notifyItemInserted(movies.size - 1)
        binding.etMovieTitle.text.clear()
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