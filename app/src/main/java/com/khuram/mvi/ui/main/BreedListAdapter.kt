package com.khuram.mvi.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.khuram.mvi.R
import com.khuram.mvi.databinding.BreedListItemBinding
import com.khuram.mvi.model.Breed


class BreedListAdapter(private val listeners: Listeners):
    RecyclerView.Adapter<BreedListAdapter.BreedListViewHolder>() {

    private val differCallback = object: DiffUtil.ItemCallback<Breed>() {
        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    private lateinit var binding: BreedListItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedListViewHolder {
        binding = BreedListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedListViewHolder(binding, listeners)
    }

    override fun onBindViewHolder(holder: BreedListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Breed>) {
        differ.submitList(list)
    }

    class BreedListViewHolder
    constructor(
        private val binding: BreedListItemBinding,
        private val listeners: Listeners): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Breed) {
            binding.root.setOnClickListener {
                listeners.onItemSelected(item.id, item)
            }

            binding.itemBreedName.text = item.name

            Glide.with(itemView.context)
                .load(item.image.url)
                .placeholder(R.drawable.dog)
                .override(600)
                .into(binding.breedImage)
        }
    }

    interface Listeners {
        fun onItemSelected(position: Int, item: Breed)
    }
}