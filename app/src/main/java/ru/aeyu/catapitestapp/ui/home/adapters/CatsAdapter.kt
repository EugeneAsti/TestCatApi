package ru.aeyu.catapitestapp.ui.home.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.aeyu.catapitestapp.databinding.CatItemBinding
import ru.aeyu.catapitestapp.domain.models.Cat

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
                itemClick(absoluteAdapterPosition)
            }
            binding.likeBtn.setOnClickListener {
                addToFavoriteClick(absoluteAdapterPosition)
            }
        }

        fun bind(item: Cat?) {
            if (item == null)
                return
            Glide.with(itemView)
                .load(item.url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.catLoading.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.catLoading.isVisible = false
                        return false
                    }
                })
                .into(binding.catImage)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = CatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding, { position ->
            onItemClick(getItem(position), position)
        },{ position ->
            onAddToFavorite(getItem(position), position)
        })
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}