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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.databinding.FragmentAboutBinding
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.ui.extensions.getImageFromRemote

class AboutFragment : Fragment() {


    private var _binding: FragmentAboutBinding? = null

    private val binding get() = _binding!!

    private val aboutViewModel: AboutViewModel by activityViewModels()

    //    private val args: AboutFragmentArgs by navArgs()
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
//        aboutViewModel.catImageId = args.catImageId ?: ""
        aboutViewModel.catImageId = arguments?.getString(EXTRA_CAT_ID_KEY, "") ?: ""
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

    private fun showSnackBar(messageText: String) {
        Snackbar.make(binding.root, messageText, Snackbar.LENGTH_SHORT).show()
    }

    private fun collectCatInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                aboutViewModel.getCatInformation().collect {
                    handleCatData(it)
                }
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


//    private fun showHowToAllowPermissionIfNeeded() {
//        AlertDialog.Builder(requireContext()).apply {
//            setPositiveButton("Понятно", null)
//            setTitle("Информация")
//            setMessage(
//                "Разрешить сохранение во внеший источник можно в настройках устройства.\n " +
//                        "В настройках Найдите меню Приложения, " +
//                        "\nЗатем найдите приложение CatApiTestApp, " +
//                        "\nДалее откройте меню Разрешения и " +
//                        "включите переключатель в строке Накопители"
//            )
//            show()
//        }
//    }



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


    companion object {
        const val EXTRA_CAT_ID_KEY = "catId_key"

        fun getNewInstance(catId: String?) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_CAT_ID_KEY, catId)
                }
            }
    }


}