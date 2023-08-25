package ru.aeyu.catapitestapp.ui.about

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
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
import ru.aeyu.catapitestapp.ui.BaseFragment
import ru.aeyu.catapitestapp.ui.extensions.getImageFromRemote

class AboutFragment : BaseFragment<FragmentAboutBinding>() {

    private val aboutViewModel: AboutViewModel by activityViewModels()
    private val args: AboutFragmentArgs by navArgs()

    override fun getBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): FragmentAboutBinding = FragmentAboutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutViewModel.catImageId = args.catImageId ?: ""
        collectCatInfo()
        binding.downLoadIcon.setOnClickListener(onDownLoadClick)
        collectMessages()
    }

    private fun collectMessages() {
        aboutViewModel.infoMessages.observe(viewLifecycleOwner) {
            if(it.isNotEmpty())
                showSnackBar(it)
        }
    }

    private fun collectCatInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
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

    private val onDownLoadClick = View.OnClickListener {
        requestWriteExternalStoragePermissions()
    }

    private fun requestWriteExternalStoragePermissions() {
        if (checkPermission() || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            aboutViewModel.onDownloadClick()
        } else {
            allowPermissions.launch(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        }
    }

    private val allowPermissions = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permission ->
        if (!permission ){
            val showRationale =
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (showRationale) {
                showExplanationDialog()
            } else {
                Snackbar.make(requireView(),"Сначала разрешите сохранение изображений.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("ОК"){
                        goToSettings()
                    }
                    .setAction("Нет"){}
//                Toast.makeText(requireContext(),
//                    ,
//                    Toast.LENGTH_LONG
//                ).show()

            }
        }
    }


    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showExplanationDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setPositiveButton("Понятно") { _, _ ->
                allowPermissions.launch(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                )
            }
            setNegativeButton("Не разрешать") { _, _ ->
                Snackbar.make(
                    requireView(),
                    "Увы фотографию невозможно сохранить",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
            setMessage("Разрешение необходимо, чтобы сохранить понравившуюся фотографию")
            show()
        }
    }

    private fun goToSettings() {
        Intent(
            ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${requireContext().packageName}")
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { intent ->
            startActivity(intent)
        }
    }


}