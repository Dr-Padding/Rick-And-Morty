package com.drawing.rickandmorty.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.adapters.EpisodesInWhichCharacterAppearedAdapter
import com.drawing.rickandmorty.databinding.FragmentPersonageDetailsBinding
import com.drawing.rickandmorty.repository.Repository
import com.drawing.rickandmorty.ui.*
import com.drawing.rickandmorty.util.Resource
import com.google.android.material.transition.MaterialContainerTransform
import jp.wasabeef.glide.transformations.BlurTransformation


class PersonageDetails: Fragment(R.layout.fragment_personage_details) {

    private var binding: FragmentPersonageDetailsBinding? = null
    private val args: PersonageDetailsArgs by navArgs()
    lateinit var episodesAdapter: EpisodesInWhichCharacterAppearedAdapter
    lateinit var viewModelEpisodes : ViewModelEpisodes
    val TAG = "PersonageDetails"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonageDetailsBinding.bind(view)

        val personage = args.personage

        val repository = Repository()
        val episodesViewModelProviderFactory = ViewModelProviderFactory(repository)

        viewModelEpisodes = ViewModelProvider(this, episodesViewModelProviderFactory)[ViewModelEpisodes::class.java]

        viewModelEpisodes.getIdsOfEpisodesInWhichCharacterAppeared(personage.episode)

        setUpRecyclerView()

        viewModelEpisodes.episodesLiveData.observe(viewLifecycleOwner) { episodesLiveData ->

            when (episodesLiveData.listOfEpisodes) {
                is Resource.Success -> {
                    hideProgressBar()
                    //episodesAdapter = EpisodesInWhichCharacterAppearedAdapter(episodesLiveData.listOfEpisodes)
                    episodesLiveData.listOfEpisodes.data.let {
                        episodesAdapter.differ.submitList(it)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    episodesLiveData.listOfEpisodes.message?.let { message ->
                        Log.e(TAG, "an error occurred: $message")
                    }
                }
                is Resource.Loading -> showProgressBar()
            }
        }


        sharedElementEnterTransition = MaterialContainerTransform()

        Glide.with(this).load(personage.image).into(binding!!.ivAvatar)
        Glide.with(this).load(personage.image)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25)))
            .into(binding!!.ivBlurBackground)

        binding!!.tvName.text = personage.name
        binding!!.tvStatus.text = personage.status
        binding!!.tvGender.text = personage.gender
        binding!!.tvSpecies.text = personage.species
        binding!!.tvOrigin.text = personage.origin.name
        binding!!.tvLocation.text = personage.location.name

        @ColorInt
        fun Context.getColorFromAttr(
            @AttrRes attrColor: Int,
            typedValue: TypedValue = TypedValue(),
            resolveRefs: Boolean = true
        ): Int {
            theme.resolveAttribute(attrColor, typedValue, resolveRefs)
            return typedValue.data
        }

        when (personage.status) {
            "Alive" -> {
                binding!!.tvStatus.setTextColor(requireContext().getColorFromAttr(R.attr.alivePersonageStatusTextColor))
            }
            "Dead" -> {
                binding!!.tvStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.valentine_red))
            }
            else -> {

            }
        }



        binding!!.ivArrowBack.setOnClickListener {

            // This callback will only be called when MyFragment is at least Started.
//            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(false) {
//                override fun handleOnBackPressed() {
//
//                }
//
//            })

            // This callback will only be called when MyFragment is at least Started.
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                // Handle the back button event
            }

            // The callback can be enabled or disabled here or in the lambda

        }

    }

    fun setUpRecyclerView(){
        episodesAdapter = EpisodesInWhichCharacterAppearedAdapter()
        binding!!.rvEpisodesInWichCharacterAppear.apply {
            adapter = episodesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        binding!!.pbPaginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding!!.pbPaginationProgressBar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}