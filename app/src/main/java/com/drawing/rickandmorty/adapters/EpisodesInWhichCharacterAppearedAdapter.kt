package com.drawing.rickandmorty.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drawing.rickandmorty.databinding.ItemEpisodePreviewBinding
import com.drawing.rickandmorty.models.episodes.Episode
import com.drawing.rickandmorty.util.Resource


class EpisodesInWhichCharacterAppearedAdapter(
    val episodes: Resource.Success<MutableList<Episode>>
    ) : RecyclerView.Adapter<EpisodesInWhichCharacterAppearedAdapter.EpisodeViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    inner class EpisodeViewHolder(val binding: ItemEpisodePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val episode = differ.currentList[position]

            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/original${episode.still_path}").into(binding.ivEpisodeAvatar)

            //binding.clItemCharacterV1.transitionName = character.id.toString()

            binding.tvEpisodeNumber.text = "episode ${episode.episode_number}"
            binding.tvEpisodeTitle.text = episode.name
            binding.tvEpisodeDate.text = episode.air_date
            itemView.setOnClickListener {
                onItemClickListener?.let { it(episode, binding.clItemEpisode) }
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

    private var onItemClickListener: ((Episode, itemView: View) -> Unit)? = null

    fun setOnItemClickListener(listener: (Episode, itemView: View) -> Unit) {
        onItemClickListener = listener
    }
}