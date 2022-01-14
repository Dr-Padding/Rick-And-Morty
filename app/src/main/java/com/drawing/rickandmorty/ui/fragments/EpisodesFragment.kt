package com.drawing.rickandmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.adapters.EpisodesAdapter
import com.drawing.rickandmorty.adapters.EpisodesViewPagerAdapter
import com.drawing.rickandmorty.databinding.FragmentEpisodesBinding
import com.drawing.rickandmorty.ui.MainActivity
import com.drawing.rickandmorty.ui.ViewModelEpisodes
import com.drawing.rickandmorty.util.Resource
import com.google.android.material.tabs.TabLayoutMediator

class EpisodesFragment: Fragment(R.layout.fragment_episodes) {

    private var binding : FragmentEpisodesBinding? = null
    //private var episodesAdapter = EpisodesAdapter()
    lateinit var episodesViewPagerAdapter : EpisodesViewPagerAdapter
//    lateinit var viewModelEpisodes : ViewModelEpisodes
//    val TAG = "EpisodesFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEpisodesBinding.bind(view)

        setUpViewPager()
        setUpTabLayout()

    }



    private fun setUpViewPager(){
        episodesViewPagerAdapter = EpisodesViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding!!.vpPagerEpisodes.adapter = episodesViewPagerAdapter
    }

    private fun setUpTabLayout(){
        TabLayoutMediator(binding!!.tabLayout, binding!!.vpPagerEpisodes){tab, position ->
            tab.text = "SEASON ${position + 1}"
        }.attach()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}