package ru.aeyu.catapitestapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.databinding.FragmentFavoriteBinding
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.ui.BaseFragment
import ru.aeyu.catapitestapp.ui.favorite.adapters.FavoriteCatsAdapter

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val favoriteViewModel: FavoriteViewModel by activityViewModels()

    private lateinit var catsAdapter: FavoriteCatsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catsAdapter = FavoriteCatsAdapter(onCatClicked)


        binding.favoriteCats.adapter = catsAdapter
        val layoutManager = GridLayoutManager(requireContext(), 3)
        //layoutManager.
        binding.favoriteCats.layoutManager = layoutManager
        favoriteViewModel.isLoadingCats.observe(viewLifecycleOwner) {
            binding.mainProgress.isVisible = it
        }
        collectCats()
    }

    private fun collectCats() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                favoriteViewModel.getCats().collect {
                    catsAdapter.differ.submitList(it)
                }
            }
        }
    }

    private val onCatClicked = object : (Cat?, Int) -> Unit {
        override fun invoke(cat: Cat?, position: Int) {
            if (cat == null)
                return
            val action =
                FavoriteFragmentDirections.actionNavigationFavoriteToNavigationAbout(cat.id)
            findNavController().navigate(action)
        }
    }

    override fun getBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)

}