package com.drawing.rickandmorty.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
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
import com.drawing.rickandmorty.adapters.PersonagesAdapter
import com.drawing.rickandmorty.databinding.FragmentPersonageDetailsBinding
import com.drawing.rickandmorty.repository.Repository
import com.drawing.rickandmorty.ui.ViewModelPersonages
import com.drawing.rickandmorty.ui.ViewModelProviderFactory
import com.drawing.rickandmorty.util.Constants
import com.drawing.rickandmorty.util.Resource
import com.google.android.material.transition.MaterialContainerTransform
import jp.wasabeef.glide.transformations.BlurTransformation


class PersonageDetails: Fragment(R.layout.fragment_personage_details) {

    private var binding: FragmentPersonageDetailsBinding? = null
    private val args: PersonageDetailsArgs by navArgs()
    lateinit var viewModelPersonages: ViewModelPersonages
    lateinit var charactersAdapter: PersonagesAdapter
    val TAG = "PersonageDetails"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonageDetailsBinding.bind(view)

        setUpRecyclerView()

        val repository = Repository()
        val viewModelProviderFactory = ViewModelProviderFactory(repository)

        viewModelPersonages = ViewModelProvider(this, viewModelProviderFactory)[ViewModelPersonages::class.java]
        viewModelPersonages.charactersLiveData.observe(viewLifecycleOwner, { charactersLiveData ->


            when (charactersLiveData.response) {
                is Resource.Success -> {
                    //hideProgressBar()
                    charactersLiveData.response.data?.let { allCharactersResponse ->
                        charactersAdapter.differ.submitList(allCharactersResponse.results.toList())

                    }
                }
                is Resource.Error -> {
                    //hideProgressBar()
                    charactersLiveData.response.message?.let { message ->
                        Log.e(TAG, "an error occurred: $message")
                    }
                }
                //is Resource.Loading ->
                    //showProgressBar()
            }


        })



        val personage = args.personage

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
        charactersAdapter = PersonagesAdapter(1)
        binding!!.rvEpisodes.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}