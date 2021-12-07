package com.drawing.rickandmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.adapters.PersonagesAdapter
import com.drawing.rickandmorty.databinding.FragmentPersonagesBinding
import com.drawing.rickandmorty.ui.MainActivity
import com.drawing.rickandmorty.ui.ViewModelPersonages
import com.drawing.rickandmorty.util.Resource

class PersonagesFragment : Fragment(R.layout.fragment_personages) {

    private var binding: FragmentPersonagesBinding? = null
    lateinit var viewModelPersonages: ViewModelPersonages
    lateinit var charactersAdapter: PersonagesAdapter
    private var toggle = false
    val TAG = "PersonagesFragment"
    var isLoading = false
    var isLastPage = false
    var isScrolling = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonagesBinding.bind(view)

        setUpRecyclerView(1)

        viewModelPersonages = (activity as MainActivity).viewModelPersonages
        viewModelPersonages.charactersLiveData.observe(viewLifecycleOwner, { charactersLiveData ->

            toggle = charactersLiveData.toggle
            charactersAdapter.recyclerViewType = charactersLiveData.recyclerViewType

            if (charactersLiveData.recyclerViewType == 1){
                charactersAdapter = PersonagesAdapter(charactersLiveData.recyclerViewType)
                binding!!.rvPersonages.apply {
                    adapter = charactersAdapter
                    layoutManager = LinearLayoutManager(activity)
                }
            }else{
                charactersAdapter = PersonagesAdapter(charactersLiveData.recyclerViewType)
                binding!!.rvPersonages.apply {
                    adapter = charactersAdapter
                    layoutManager = GridLayoutManager(activity, 2)
                }
            }

            binding!!.ivBurgerMenu.setImageDrawable(
                ContextCompat.getDrawable(
                    binding!!.root.context,
                    charactersLiveData.burgerMenuImage
                )
            )

            when (charactersLiveData.response) {
                is Resource.Success -> {
                    hideProgressBar()
                    charactersLiveData.response.data?.let { allCharactersResponse ->
                        charactersAdapter.differ.submitList(allCharactersResponse.results)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    charactersLiveData.response.message?.let { message ->
                        Log.e(TAG, "an error occurred: $message")
                    }
                }
                is Resource.Loading ->
                    showProgressBar()
            }
        })

        switchRecyclerViewType()
    }

    private fun hideProgressBar() {
        binding!!.pbPaginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding!!.pbPaginationProgressBar.visibility = View.VISIBLE
    }

    val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }


    private fun setUpRecyclerView(recyclerViewType: Int) {
        charactersAdapter = PersonagesAdapter(recyclerViewType)
        binding!!.rvPersonages.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
   }

    private fun switchRecyclerViewType(){
        with(binding!!) {
            ivBurgerMenu.setOnClickListener {
                if (!toggle) {
                    viewModelPersonages.switchRecyclerViewType(2)
                } else {
                    viewModelPersonages.switchRecyclerViewType(1)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}