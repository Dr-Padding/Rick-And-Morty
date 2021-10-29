package com.drawing.rickandmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.adapters.PersonagesAdapter
import com.drawing.rickandmorty.databinding.FragmentPersonagesBinding
import com.drawing.rickandmorty.ui.MainActivity
import com.drawing.rickandmorty.ui.ViewModel
import com.drawing.rickandmorty.util.Resource

class PersonagesFragment: Fragment(R.layout.fragment_personages) {

    private var binding: FragmentPersonagesBinding? = null
    lateinit var viewModel: ViewModel
    lateinit var charactersAdapter: PersonagesAdapter

    val TAG = "PersonagesFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonagesBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
        viewModel.charactersLiveData.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { allCharactersResponse ->
                    charactersAdapter.differ.submitList(allCharactersResponse.results)

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "an error occurred: $message")

                    }
                }
                is Resource.Loading ->
                    showProgressBar()
            }
        })


    }

    private fun hideProgressBar(){
        binding!!.pbPaginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding!!.pbPaginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView(){
        charactersAdapter = PersonagesAdapter()
        binding!!.rvPersonages.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}