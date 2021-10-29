package com.drawing.rickandmorty.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.databinding.ActivityMainBinding.bind
import com.drawing.rickandmorty.databinding.ItemCharacterPreviewV1Binding
import com.drawing.rickandmorty.models.AllCharactersResponse
import com.drawing.rickandmorty.models.Result

class PersonagesAdapter : RecyclerView.Adapter<PersonagesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCharacterPreviewV1Binding): RecyclerView.ViewHolder(binding.root){
        fun bind(character: Result){

                Glide.with(itemView.context).load(character.image).into(binding.ivAvatar)
                binding.tvStatus.text = character.status

                

                if (character.status == "Dead"){
                    binding.tvStatus.setTextColor(Color.YELLOW)
                }


                binding.tvName.text = character.name
                binding.tvSpeciesAndGender.text = character.species + ", " + character.gender

               itemView.setOnClickListener{
                    onItemClickListener?.let { it(character) }
                }


        }
    }

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
        holder.bind(character)
    }

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (Result) -> Unit){

        onItemClickListener = listener
    }

}