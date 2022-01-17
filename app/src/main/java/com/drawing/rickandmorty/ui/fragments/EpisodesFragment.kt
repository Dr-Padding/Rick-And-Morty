package com.drawing.rickandmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.adapters.EpisodesAdapter
import com.drawing.rickandmorty.adapters.EpisodesViewPagerAdapter
import com.drawing.rickandmorty.databinding.FragmentEpisodesBinding
import com.drawing.rickandmorty.repository.Repository
import com.drawing.rickandmorty.ui.EpisodesViewModelProviderFactory
import com.drawing.rickandmorty.ui.MainActivity
import com.drawing.rickandmorty.ui.ViewModelEpisodes
import com.drawing.rickandmorty.util.Constants.Companion.API_KEY
import com.drawing.rickandmorty.util.Resource
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class EpisodesFragment: Fragment(R.layout.fragment_episodes) {

    private var binding : FragmentEpisodesBinding? = null
    private lateinit var episodesViewPagerAdapter : EpisodesViewPagerAdapter
    //lateinit var viewModelEpisodes : ViewModelEpisodes
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
            tab.id = position
        }.attach()
    }

//    private fun onTabSelected(){
//        binding!!.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                    when(tab!!.id){
//                        0 -> {
//                            viewModelEpisodes.getSeason(1, API_KEY)
//                        }
//                        1 -> {
//                            viewModelEpisodes.getSeason(2, API_KEY)
//                        }
//                        2 -> {
//                            viewModelEpisodes.getSeason(3, API_KEY)
//                        }
//                        3 -> {
//                            viewModelEpisodes.getSeason(4, API_KEY)
//                        }
//                        4 -> {
//                            viewModelEpisodes.getSeason(5, API_KEY)
//                        }
//                    }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                when(tab!!.id){
//                    0 -> {
//                        viewModelEpisodes.getSeason(1, API_KEY)
//                    }
//                    1 -> {
//                        viewModelEpisodes.getSeason(2, API_KEY)
//                    }
//                    2 -> {
//                        viewModelEpisodes.getSeason(3, API_KEY)
//                    }
//                    3 -> {
//                        viewModelEpisodes.getSeason(4, API_KEY)
//                    }
//                    4 -> {
//                        viewModelEpisodes.getSeason(5, API_KEY)
//                    }
//                }
//            }
//        })
//    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}


