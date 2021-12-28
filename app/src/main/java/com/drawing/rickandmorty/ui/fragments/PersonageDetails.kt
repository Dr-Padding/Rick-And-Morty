package com.drawing.rickandmorty.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.databinding.FragmentPersonageDetailsBinding
import com.google.android.material.transition.MaterialContainerTransform
import jp.wasabeef.glide.transformations.BlurTransformation


class PersonageDetails: Fragment(R.layout.fragment_personage_details) {

    private var binding: FragmentPersonageDetailsBinding? = null
    val args: PersonageDetailsArgs by navArgs()
    val TAG = "PersonageDetails"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonageDetailsBinding.bind(view)
        val personage = args.personage

        sharedElementEnterTransition = MaterialContainerTransform()

        Glide.with(this).load(personage.image).into(binding!!.ivAvatar)
        Glide.with(this).load(personage.image)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25)))
            .into(binding!!.ivBlurBackground)

        binding!!.tvName.text = personage.name
        binding!!.tvStatus.text = personage.status



    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}