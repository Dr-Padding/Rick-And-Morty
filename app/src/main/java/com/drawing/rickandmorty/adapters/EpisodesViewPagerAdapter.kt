package com.drawing.rickandmorty.adapters


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.drawing.rickandmorty.ui.fragments.EpisodesRecyclerViewFragment

class EpisodesViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    var seasonNumber = 1

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {

        val bundle = Bundle()

        when(position){
           0 -> {
               seasonNumber = 1
           }
           1 -> {
               seasonNumber = 2
           }
           2 -> {
               seasonNumber = 3
           }
           3 -> {
               seasonNumber = 4
           }
           4 -> {
               seasonNumber = 5
           }
           else -> {
               Fragment()
           }
       }
        bundle.putInt("seasonNumber", seasonNumber)
        val episodeRecyclerViewFragment = EpisodesRecyclerViewFragment()
        episodeRecyclerViewFragment.arguments = bundle
        return episodeRecyclerViewFragment

    }


}