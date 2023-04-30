package ru.aeyu.catapitestapp.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.databinding.FragmentAboutBinding
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.ui.extensions.getImageFromRemote

class AboutFragment : Fragment() {


    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val aboutViewModel: AboutViewModel by activityViewModels()
    private val args: AboutFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val aboutViewModel =
//            ViewModelProvider(this).get(AboutViewModel::class.java)

        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutViewModel.catImageId = args.catImageId ?: ""
        collectCatInfo()
        binding.downLoadIcon.setOnClickListener(onDownLoadClick)
        collectMessages()
    }

    private fun collectMessages() {
        aboutViewModel.infoMessages.observe(viewLifecycleOwner){
            showSnackBar(it)
        }
    }

    private fun showSnackBar(messageText: String){
        Snackbar.make(binding.root, messageText, Snackbar.LENGTH_SHORT).show()
    }
    private fun collectCatInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                aboutViewModel.getCatInformation().collect {
                    handleCatData(it)
                }
//                homeViewModel.getCats().collect{
//                    catsAdapter.differ.submitList(it)
//                }
            }
        }
    }

    private fun handleCatData(cat: Cat) {
        binding.catImageAbout.getImageFromRemote(
            context = requireContext(),
            url = cat.url,
            progressBarWidget = binding.catImageProgressBar
        )
        val breed = cat.breeds.firstOrNull() ?: return

        binding.breedName.text = breed.name
        binding.description.text = breed.description
        binding.temperament.text = breed.temperament
        binding.ratingBarAdaptability.numStars = breed.adaptability
        binding.ratingBarAffectionLevel.numStars = breed.affectionLevel
        binding.ratingBarChildFriendly.numStars = breed.childFriendly
        binding.ratingBarDogFriendly.numStars = breed.dogFriendly
        binding.ratingBarEnergyLevel.numStars = breed.energyLevel
        binding.ratingBarGrooming.numStars = breed.grooming
        binding.ratingBarHealthIssues.numStars = breed.healthIssues
        binding.ratingBarIntelligence.numStars = breed.intelligence
        binding.ratingBarSheddingLevel.numStars = breed.sheddingLevel
        binding.ratingBarSocialNeeds.numStars = breed.socialNeeds
        binding.ratingBarStrangerFriendly.numStars = breed.strangerFriendly
        binding.ratingBarVocalisation.numStars = breed.vocalisation
        binding.ratingBarExperimental.numStars = breed.experimental
        binding.ratingBarHairless.numStars = breed.hairless
        binding.ratingBarNatural.numStars = breed.natural
        binding.ratingBarRare.numStars = breed.rare
        binding.ratingBarRex.numStars = breed.rex

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val onDownLoadClick = View.OnClickListener{
        aboutViewModel.onDownloadClick()
    }
}