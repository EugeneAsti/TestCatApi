package ru.aeyu.catapitestapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.aeyu.catapitestapp.databinding.BreedItemBinding
import ru.aeyu.catapitestapp.domain.models.Breed

class BreedsAdapter(
    diffCallback: DiffUtil.ItemCallback<Breed>,
    private val onItemClick: (breed: Breed?, position: Int) -> Unit,
) : ListAdapter<Breed, BreedsAdapter.BreedViewHolder>(diffCallback){

    inner class BreedViewHolder(
        binding: BreedItemBinding,
        itemClick: (position: Int) -> Unit,

        ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.breedItemLayout.setOnClickListener {
                itemClick(absoluteAdapterPosition)
            }
        }

        fun bind(item: Breed?) {
            if (item == null)
                return
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val binding = BreedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedViewHolder(binding){ position ->
            onItemClick(getItem(position), position)
        }
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}