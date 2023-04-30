package ru.aeyu.catapitestapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.aeyu.catapitestapp.databinding.CatItemBinding
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.ui.extensions.getImageFromRemoteWithCompress

class CatsAdapter(
    diffCallback: DiffUtil.ItemCallback<Cat>,
    private val onItemClick: (cat: Cat?, position: Int) -> Unit,
    private val onAddToFavorite: (cat: Cat?, position: Int) -> Unit,
) : PagingDataAdapter<Cat, CatsAdapter.CatViewHolder>(diffCallback) {

//    : RecyclerView.Adapter<CatsAdapter.CatViewHolder>() {
//    val differ = AsyncListDiffer(this, diffCallback)

    inner class CatViewHolder(
        private val binding: CatItemBinding,
        itemClick: (position: Int) -> Unit,
        addToFavoriteClick: (position: Int) -> Unit,
        ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.catImage.setOnClickListener {
                itemClick(bindingAdapterPosition)
            }
            binding.likeBtn.setOnClickListener {
                addToFavoriteClick(bindingAdapterPosition)
            }
        }

        fun bind(item: Cat?) {
            if (item == null)
                return
            binding.catImage.getImageFromRemoteWithCompress(
                context = binding.catImage.context,
                url = item.url,
                progressBarWidget = binding.catLoading
            )
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = CatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CatViewHolder(binding, { position ->
            val cat = if(position >= itemCount) Cat() else getItem(position)
            onItemClick(cat, position)
        },{ position ->
            val cat = if(position >= itemCount) Cat() else getItem(position)
            onAddToFavorite(cat, position)
        })
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}