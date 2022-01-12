package com.drawing.rickandmorty.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.adapters.PersonagesAdapter
import com.drawing.rickandmorty.databinding.FragmentPersonagesBinding
import com.drawing.rickandmorty.ui.MainActivity
import com.drawing.rickandmorty.ui.ViewModelPersonages
import com.drawing.rickandmorty.util.Constants.Companion.QUERY_PAGE_SIZE
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

                if(binding!!.rvPersonages.layoutManager is LinearLayoutManager){

                }
                if (binding!!.rvPersonages.layoutManager is GridLayoutManager) {
                    binding!!.rvPersonages.apply {
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
            }else{
                if(binding!!.rvPersonages.layoutManager is GridLayoutManager){

                }else {
                    binding!!.rvPersonages.apply {
                        layoutManager = GridLayoutManager(activity, 2)
                    }
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
                        charactersAdapter.differ.submitList(allCharactersResponse.results.toList())
                        binding!!.tvTotalCharactersAmount.text = allCharactersResponse.info.count.toString()
                        val totalPages = allCharactersResponse.info.count / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModelPersonages.charactersPage == totalPages
                        if (isLastPage){
                            binding!!.rvPersonages.setPadding(0, 0, 0, 0)
                        }
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

        charactersAdapter.setOnItemClickListener { result, itemView ->
            val bundle = Bundle().apply {
                putSerializable("personage", result)
            }

            // Map the start View in FragmentA and the transitionName of the end View in FragmentB
            val personageDetailsTransitionName = getString(R.string.personage_details_transition_name)
            val extras = FragmentNavigatorExtras(itemView to personageDetailsTransitionName)
            findNavController().navigate(
                R.id.action_personagesFragment_to_personageDetails,
                bundle,
                null,
                extras
            )
        }

    }

    private fun hideProgressBar() {
        binding!!.pbPaginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding!!.pbPaginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = if (binding!!.rvPersonages.layoutManager is LinearLayoutManager) {
                recyclerView.layoutManager as LinearLayoutManager
            }else{
                recyclerView.layoutManager as GridLayoutManager
            }
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem &&  isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate){
                viewModelPersonages.getCharacters()
                isScrolling = false
            }
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
            addOnScrollListener(this@PersonagesFragment.scrollListener)
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