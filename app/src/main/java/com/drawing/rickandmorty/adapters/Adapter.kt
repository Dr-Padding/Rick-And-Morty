package com.drawing.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drawing.rickandmorty.databinding.ItemCharacterPreviewV1Binding
import com.drawing.rickandmorty.models.Result

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCharacterPreviewV1Binding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterPreviewV1Binding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = differ.currentList[position]

        holder.apply {
            Glide.with(holder.itemView.context).load(character.image).into(binding.ivAvatar)
            binding.tvStatus.text = character.status
            binding.tvName.text = character.name
            binding.tvSpeciesAndGender.text = character.species + ", " + character.gender

            holder.itemView.setOnClickListener{
                onItemClickListener?.let { it(character) }
            }

        }
    }

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (Result) -> Unit){

        onItemClickListener = listener
    }

}