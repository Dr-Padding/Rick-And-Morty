package com.drawing.rickandmorty.adapters


import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.databinding.ItemCharacterPreviewV1Binding
import com.drawing.rickandmorty.databinding.ItemCharacterPreviewV2Binding
import com.drawing.rickandmorty.databinding.ItemEpisodePreviewBinding
import com.drawing.rickandmorty.models.Result


class EpisodesAdapter : RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    inner class EpisodeViewHolder(val binding: ItemEpisodePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val character = differ.currentList[position]

            Glide.with(itemView.context).load(character.image).into(binding.ivEpisodeAvatar)




            //binding.clItemCharacterV1.transitionName = character.id.toString()

            binding.tvName.text = character.name
            binding.tvSpeciesAndGender.text = character.species + ", " + character.gender
            itemView.setOnClickListener {
                onItemClickListener?.let { it(character, binding.clItemCharacterV1) }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
            val binding = ItemEpisodePreviewBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return EpisodeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(position)
    }


    private var onItemClickListener: ((Result, itemView: View) -> Unit)? = null

    fun setOnItemClickListener(listener: (Result, itemView: View) -> Unit) {

        onItemClickListener = listener
    }



}