package ru.aeyu.catapitestapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.aeyu.catapitestapp.databinding.FavoriteCatItemBinding
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.ui.extensions.getImageFromRemote

class FavoriteCatsAdapter(
    private val onItemClick: (cat: Cat?, position: Int) -> Unit,
) : RecyclerView.Adapter<FavoriteCatsAdapter.FavoriteCatViewHolder>() {

    private val diffCallback = object: DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(
            oldItem: Cat,
            newItem: Cat
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Cat,
            newItem: Cat
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class FavoriteCatViewHolder(
        private val binding: FavoriteCatItemBinding,
        itemClick: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.catImage.setOnClickListener {
                itemClick(bindingAdapterPosition)
            }
        }

        fun bind(item: Cat?) {
            if (item == null)
                return
            binding.catImage.getImageFromRemote(
                context = binding.catImage.context,
                url = item.url,
                progressBarWidget = binding.catLoading
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCatViewHolder {
        val binding = FavoriteCatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteCatViewHolder(binding) { position ->
            val cat = if(position >= itemCount) Cat() else differ.currentList[position]
            onItemClick(cat, position)
        }
    }

    override fun onBindViewHolder(holder: FavoriteCatViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}