package com.drawing.rickandmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.adapters.PersonagesAdapter
import com.drawing.rickandmorty.databinding.FragmentPersonagesBinding
import com.drawing.rickandmorty.models.Result
import com.drawing.rickandmorty.ui.MainActivity
import com.drawing.rickandmorty.ui.ViewModel
import com.drawing.rickandmorty.util.Resource

class PersonagesFragment : Fragment(R.layout.fragment_personages) {

    private var binding: FragmentPersonagesBinding? = null
    lateinit var viewModel: ViewModel
    lateinit var charactersAdapter: PersonagesAdapter
    private var toggle = false

    val TAG = "PersonagesFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonagesBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
        viewModel.charactersLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
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

    private fun hideProgressBar() {
        binding!!.pbPaginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding!!.pbPaginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        var viewType = 1
        binding!!.ivBurgerMenu.setOnClickListener {
            if (!toggle) {
                viewType = 2
                toggle = true
            }else{
                viewType = 1
                toggle = false
            }
        }
        charactersAdapter = PersonagesAdapter(viewType)
        binding!!.rvPersonages.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(activity)

                if (viewType == 2) {
                    layoutManager = GridLayoutManager(activity, 2)
                    binding!!.ivBurgerMenu.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding!!.root.context,
                            R.drawable.ic_grid_view
                        )
                    )
                } else {
                    layoutManager = LinearLayoutManager(activity)
                    binding!!.ivBurgerMenu.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding!!.root.context,
                            R.drawable.ic_list_view
                        )
                    )
                }
            }
        }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}