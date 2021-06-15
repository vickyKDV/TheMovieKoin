package com.vickykdv.themoviekoin.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vickykdv.themoviekoin.BuildConfig
import com.vickykdv.themoviekoin.databinding.ItemListMovieBinding
import com.vickykdv.themoviekoin.model.DataMovie

class MovieAdapter (private val showDetail: (DataMovie) -> Unit
) : PagedListAdapter<DataMovie, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(getItem(position)) {
                txtTitle.text = this?.title
                txtDescription.text = this?.overview
                txtReleaseDate.text = this?.release_date.toString()

                holder.itemView.also {
                    Glide.with(it.context)
                        .load( BuildConfig.imageUrl + getItem(position)?.poster_path)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgPoster)
                }
            }
            root.setOnClickListener {
                getItem(position)?.let { it1 -> showDetail(it1) }
            }
        }
    }

    companion object{
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<DataMovie>(){
            override fun areContentsTheSame(oldItem: DataMovie, newItem: DataMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: DataMovie, newItem: DataMovie): Boolean {
                return oldItem == newItem
            }
        }
    }
    class ViewHolder(val binding: ItemListMovieBinding) : RecyclerView.ViewHolder(binding.root)
}