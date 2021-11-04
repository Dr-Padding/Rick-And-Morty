package com.drawing.rickandmorty.adapters


import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.adapters.PersonagesAdapter.Companion.VIEW_TYPE_TWO
import com.drawing.rickandmorty.databinding.ItemCharacterPreviewV1Binding
import com.drawing.rickandmorty.databinding.ItemCharacterPreviewV2Binding
import com.drawing.rickandmorty.models.Result
import com.drawing.rickandmorty.util.Constants
import kotlin.properties.Delegates

class PersonagesAdapter(val viewType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    inner class View1ViewHolder(val binding: ItemCharacterPreviewV1Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindV1(position: Int) {
            val character = differ.currentList[position]

            Glide.with(itemView.context).load(character.image).into(binding.ivAvatar)
            val spannableString = SpannableString(character.status)

            @ColorInt
            fun Context.getColorFromAttr(
                @AttrRes attrColor: Int,
                typedValue: TypedValue = TypedValue(),
                resolveRefs: Boolean = true
            ): Int {
                theme.resolveAttribute(attrColor, typedValue, resolveRefs)
                return typedValue.data
            }

            when (character.status) {
                "Alive" -> {
                    val fColor = ForegroundColorSpan(
                        binding.root.context.getColorFromAttr(
                            R.attr.alivePersonageStatusTextColor
                        )
                    )
                    spannableString.setSpan(fColor, 0, 5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                }
                "Dead" -> {
                    val fColor = ForegroundColorSpan(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.valentine_red
                        )
                    )
                    spannableString.setSpan(fColor, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                }
                else -> {

                }
            }

            binding.tvStatus.text = spannableString
            binding.tvName.text = character.name
            binding.tvSpeciesAndGender.text = character.species + ", " + character.gender
            itemView.setOnClickListener {
                onItemClickListener?.let { it(character) }
            }
        }
    }

    inner class View2ViewHolder(val binding: ItemCharacterPreviewV2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindV2(position: Int) {
            val character = differ.currentList[position]

            Glide.with(itemView.context).load(character.image).into(binding.ivAvatarV2)
            val spannableString = SpannableString(character.status)

            @ColorInt
            fun Context.getColorFromAttr(
                @AttrRes attrColor: Int,
                typedValue: TypedValue = TypedValue(),
                resolveRefs: Boolean = true
            ): Int {
                theme.resolveAttribute(attrColor, typedValue, resolveRefs)
                return typedValue.data
            }

            when (character.status) {
                "Alive" -> {
                    val fColor = ForegroundColorSpan(
                        binding.root.context.getColorFromAttr(
                            R.attr.alivePersonageStatusTextColor
                        )
                    )
                    spannableString.setSpan(fColor, 0, 5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                }
                "Dead" -> {
                    val fColor = ForegroundColorSpan(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.valentine_red
                        )
                    )
                    spannableString.setSpan(fColor, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                }
                else -> {

                }
            }

            binding.tvStatusV2.text = spannableString
            binding.tvNameV2.text = character.name
            binding.tvSpeciesAndGenderV2.text = character.species + ", " + character.gender
            itemView.setOnClickListener {
                onItemClickListener?.let { it(character) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ONE) {
            val binding = ItemCharacterPreviewV1Binding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            View1ViewHolder(binding)
        } else {
            val binding = ItemCharacterPreviewV2Binding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            View2ViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       /* if (holder is View1ViewHolder){
            holder.bindV1(position)
        }else if (holder is View2ViewHolder){
            holder.bindV2(position)
        }*/

        if (holder.itemViewType == VIEW_TYPE_ONE) {
            (holder as View1ViewHolder).bindV1(position)
        } else {
            (holder as View2ViewHolder).bindV2(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        //var character = differ.currentList[position]
        //return viewType

        if (position == 2) {
            return VIEW_TYPE_TWO
        } else {
            return VIEW_TYPE_ONE
        }
    }


    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (Result) -> Unit) {

        onItemClickListener = listener
    }

}