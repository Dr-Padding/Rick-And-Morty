package com.drawing.rickandmorty.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.databinding.FragmentPersonageDetailsBinding
import com.drawing.rickandmorty.ui.MainActivity
import com.google.android.material.transition.MaterialContainerTransform
import jp.wasabeef.glide.transformations.BlurTransformation


class PersonageDetails: Fragment(R.layout.fragment_personage_details) {

    private var binding: FragmentPersonageDetailsBinding? = null
    private val args: PersonageDetailsArgs by navArgs()

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


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}