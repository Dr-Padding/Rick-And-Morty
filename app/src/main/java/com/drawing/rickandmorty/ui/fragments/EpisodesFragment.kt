package com.drawing.rickandmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.adapters.EpisodesAdapter
import com.drawing.rickandmorty.databinding.FragmentEpisodesBinding
import com.drawing.rickandmorty.ui.MainActivity
import com.drawing.rickandmorty.ui.ViewModelEpisodes
import com.drawing.rickandmorty.util.Resource

class EpisodesFragment: Fragment(R.layout.fragment_episodes) {

    private var binding : FragmentEpisodesBinding? = null
    lateinit var episodesAdapter : EpisodesAdapter
    lateinit var viewModelEpisodes : ViewModelEpisodes
    val TAG = "EpisodesFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEpisodesBinding.bind(view)

        setUpRecyclerView()

        viewModelEpisodes = (activity as MainActivity).viewModelEpisodes
        viewModelEpisodes.episodesLiveData.observe(viewLifecycleOwner, { episodesLiveData ->
            when (episodesLiveData.response){
                is Resource.Success -> {
                    hideProgressBar()
                    episodesLiveData.response.data?.let { season ->
                    episodesAdapter.differ.submitList(season.episodes.toList())

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    episodesLiveData.response.message?.let { message ->
                        Log.e(TAG, "an error occurred: $message")
                    }
                }
                is Resource.Loading -> showProgressBar()
            }
        })
    }

    private fun hideProgressBar() {
        binding!!.pbPaginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding!!.pbPaginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView(){
        episodesAdapter = EpisodesAdapter()
        binding!!.rvEpisodes.apply {
            adapter = episodesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}