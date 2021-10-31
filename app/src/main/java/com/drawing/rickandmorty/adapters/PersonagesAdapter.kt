package com.drawing.rickandmorty.adapters



import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.databinding.ItemCharacterPreviewV1Binding
import com.drawing.rickandmorty.models.Result

class PersonagesAdapter : RecyclerView.Adapter<PersonagesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCharacterPreviewV1Binding): RecyclerView.ViewHolder(binding.root){
        fun bind(character: Result){

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
                    val fColor = ForegroundColorSpan(binding.root.context.getColorFromAttr(
                        R.attr.alivePersonageStatusTextColor
                    ))
                    spannableString.setSpan(fColor,0, 5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                }
                    "Dead" -> {
                    val fColor = ForegroundColorSpan(ContextCompat.getColor(binding.root.context, R.color.valentine_red))
                    spannableString.setSpan(fColor,0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                }
                    else -> {

                }
                }

                binding.tvStatus.text = spannableString

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