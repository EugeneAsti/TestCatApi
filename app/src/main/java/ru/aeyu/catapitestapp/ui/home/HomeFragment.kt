package ru.aeyu.catapitestapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.databinding.FragmentHomeBinding
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.ui.home.adapters.CatsAdapter
import ru.aeyu.catapitestapp.ui.home.adapters.DiffUtils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var catsAdapter: CatsAdapter

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
        catsAdapter = CatsAdapter(DiffUtils, onCatClicked)
        binding.cats.adapter = catsAdapter
        val layoutManager = GridLayoutManager(requireContext(), 3)
        //layoutManager.
        binding.cats.layoutManager = layoutManager
        collectCats()
        collectErrors()
    }

    private fun collectErrors() {
        homeViewModel.errMessages.observe(viewLifecycleOwner) {
            binding.errText.text = it
        }
    }

    private fun collectCats() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                homeViewModel.getCatsPaging().collectLatest {
                    catsAdapter.submitData(it)
                }
//                homeViewModel.getCats().collect{
//                    catsAdapter.differ.submitList(it)
//                }
            }
        }
    }

    private val onCatClicked = object : (Cat?, Int) -> Unit {
        override fun invoke(cat: Cat?, position: Int) {
            homeViewModel.onCatClicked(cat)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}