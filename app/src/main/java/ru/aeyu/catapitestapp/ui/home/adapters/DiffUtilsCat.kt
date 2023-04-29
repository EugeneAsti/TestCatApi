package ru.aeyu.catapitestapp.ui.home.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.aeyu.catapitestapp.domain.models.Breed
import ru.aeyu.catapitestapp.domain.models.Cat

object DiffUtilsCat : DiffUtil.ItemCallback<Cat>() {
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


object DiffUtilsBreed : DiffUtil.ItemCallback<Breed>() {
    override fun areItemsTheSame(
        oldItem: Breed,
        newItem: Breed
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Breed,
        newItem: Breed
    ): Boolean {
        return oldItem == newItem
    }
}
