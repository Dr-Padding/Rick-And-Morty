package com.drawing.rickandmorty.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.adapters.EpisodesAdapter
import com.drawing.rickandmorty.databinding.FragmentPersonageDetailsBinding
import com.drawing.rickandmorty.models.episodes_from_rick_and_morty_api.Result
import com.drawing.rickandmorty.repository.Repository
import com.drawing.rickandmorty.ui.*
import com.drawing.rickandmorty.util.Constants.Companion.API_KEY
import com.drawing.rickandmorty.util.Resource
import com.google.android.material.transition.MaterialContainerTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PersonageDetails: Fragment(R.layout.fragment_personage_details) {

    private var binding: FragmentPersonageDetailsBinding? = null
    private val args: PersonageDetailsArgs by navArgs()
    lateinit var episodesAdapter: EpisodesAdapter
    lateinit var viewModelEpisodes : ViewModelEpisodes
    val TAG = "PersonageDetails"


    //lateinit var episodeCode: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonageDetailsBinding.bind(view)

        val personage = args.personage

        setUpRecyclerView()


        val repository = Repository()
        val episodesViewModelProviderFactory = EpisodesViewModelProviderFactory(repository)

        viewModelEpisodes = ViewModelProvider(this, episodesViewModelProviderFactory)[ViewModelEpisodes::class.java]

        viewModelEpisodes.getIdsOfEpisodesInWhichCharacterAppeared(personage.episode)




        viewModelEpisodes.episodesLiveData.observe(viewLifecycleOwner, { episodesLiveData ->

            episodesAdapter.differ.submitList(episodesLiveData.listOfEpisodes?.toList())

//            when (episodesLiveData.response){
//                is Resource.Success -> {
//                    hideProgressBar()
//
////                    episodesLiveData.response.data?.let { season ->
////                        episodesAdapter.differ.submitList(season.episodes.toList())
//                    }
//                }
//                is Resource.Error -> {
//                    hideProgressBar()
//                    episodesLiveData.response.message?.let { message ->
//                        Log.e(TAG, "an error occurred: $message")
//                    }
//                }
//                is Resource.Loading -> showProgressBar()
//            }
        })

        //Toast.makeText(requireContext(), "$episodeCode", Toast.LENGTH_SHORT).show()



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

    }

    fun setUpRecyclerView(){
        episodesAdapter = EpisodesAdapter()
        binding!!.rvEpisodes.apply {
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