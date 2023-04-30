package ru.aeyu.catapitestapp

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.databinding.ActivityMainBinding
import ru.aeyu.catapitestapp.ui.home.HomeViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var badgeFavorites: BadgeDrawable

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val navView: BottomNavigationView = binding.navView

        val itemAbout = navView.menu.findItem(R.id.navigation_about)
        val itemHome = navView.menu.findItem(R.id.navigation_home)
        val itemFavorite = navView.menu.findItem(R.id.navigation_favorite)

        badgeFavorites = navView.getOrCreateBadge(R.id.navigation_favorite)
        badgeFavorites.isVisible = false

// An icon only badge will be displayed unless a number is set:
        //badge.number = 99

        val appBarConfiguration = AppBarConfiguration(navGraph = navController.graph)
        itemHome.setOnMenuItemClickListener {
            navController.popBackStack()
            navController.navigate(R.id.navigation_home)
            false
        }
        itemFavorite.setOnMenuItemClickListener {
            navController.popBackStack()
            navController.navigate(R.id.navigation_favorite)
            homeViewModel.clearFavorites()
            false
        }

        setupActionBarWithNavController(this, navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_about -> {
                    itemAbout.isVisible = true
                }
                else -> itemAbout.isVisible = false
            }
        }

        collectFavorites()
    }

    private fun collectFavorites() {
        this.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                homeViewModel.sumOfAddedFavorites.collect { result ->
                    if (result > 0) {
                        badgeFavorites.isVisible = true
                        badgeFavorites.number = result
                    } else
                        badgeFavorites.isVisible = false
                }
            }
        }
    }

}