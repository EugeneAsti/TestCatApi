package ru.aeyu.catapitestapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.R
import ru.aeyu.catapitestapp.databinding.FragmentHomeBinding
import ru.aeyu.catapitestapp.domain.models.Breed
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.ui.home.adapters.BreedsArrayAdapter
import ru.aeyu.catapitestapp.ui.home.adapters.CatsAdapter
import ru.aeyu.catapitestapp.ui.home.adapters.DiffUtilsCat

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var catsAdapter: CatsAdapter

    private lateinit var spinnerAdapter: BreedsArrayAdapter
    private lateinit var spinnerBreeds: AutoCompleteTextView

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catsAdapter = CatsAdapter(DiffUtilsCat, onCatClicked, onAddToFavoriteClicked)


        spinnerBreeds = binding.breedsFilter
        binding.cats.adapter = catsAdapter
        val layoutManager = GridLayoutManager(requireContext(), 3)
        //layoutManager.
        binding.cats.layoutManager = layoutManager
        homeViewModel.isLoadingCats.observe(viewLifecycleOwner) {
            binding.mainProgress.isVisible = it
        }
        collectCats()
        collectErrors()
        collectBreeds()
    }

    private fun collectBreeds() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.getBreeds().collect { breeds ->
                    initBreedAdapter(breeds)

                }
            }
        }
    }

    private fun initBreedAdapter(listBreeds: List<Breed>) {
        spinnerAdapter = BreedsArrayAdapter(
            requireContext(), R.layout.breed_item, listBreeds, onBreedClicked
        )
        spinnerBreeds.setAdapter(spinnerAdapter)
        spinnerBreeds.onItemClickListener = itemClicked

    }

    private fun collectErrors() {
        homeViewModel.errMessages.observe(viewLifecycleOwner) {
            if(it.isNotEmpty())
                showDialog(it)
        }
    }

    private fun showDialog(text: String) {
        AlertDialog.Builder(requireContext()).apply {
            setMessage(text)
            setNeutralButton("Понятно", null)
        }.show()
    }

    private fun collectCats() {
        homeViewModel.onBreedChanged.observe(viewLifecycleOwner) { breedId ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                    homeViewModel.getCatsPaging(breedId).collectLatest {
                        catsAdapter.submitData(it)
                    }

                }
//                homeViewModel.getCats().collect{
//                    catsAdapter.differ.submitList(it)
//                }
            }
        }
    }

    private val onCatClicked = object : (Cat?, Int) -> Unit {
        override fun invoke(cat: Cat?, position: Int) {
            if (cat == null)
                return
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationAbout(cat.id)
            findNavController().navigate(action)
        }
    }

    private val itemClicked = AdapterView.OnItemClickListener { _, _, position, _ ->
        spinnerAdapter.onItemClickListener(position)
    }

    private val onBreedClicked = object : (Breed?) -> Unit {
        override fun invoke(breed: Breed?) {
            if (breed != null)
                homeViewModel.setBreed(breed)
        }
    }

    private val onAddToFavoriteClicked = object : (Cat?, Int) -> Unit {
        override fun invoke(cat: Cat?, position: Int) {
            homeViewModel.onAddToFavoriteClick(cat)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}