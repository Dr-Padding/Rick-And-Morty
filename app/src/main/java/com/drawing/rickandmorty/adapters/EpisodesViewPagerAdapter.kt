package com.drawing.rickandmorty.adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.drawing.rickandmorty.ui.fragments.EpisodesRecyclerViewFragment

class EpisodesViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> {
               EpisodesRecyclerViewFragment()
           }
           1 -> {
               EpisodesRecyclerViewFragment()
           }
           2 -> {
               EpisodesRecyclerViewFragment()
           }
           3 -> {
               EpisodesRecyclerViewFragment()
           }
           4 -> {
               EpisodesRecyclerViewFragment()
           }
           else -> {
               Fragment()
           }
       }
    }


}