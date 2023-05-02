package ru.aeyu.catapitestapp

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.badge.BadgeDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.databinding.ActivityMainBinding
import ru.aeyu.catapitestapp.domain.cicerone.repository.main.MainActivityScreenRouter
import ru.aeyu.catapitestapp.domain.cicerone.Screens.homeScreen
import ru.aeyu.catapitestapp.ui.home.HomeViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var badgeFavorites: BadgeDrawable

    private val homeViewModel: HomeViewModel by viewModels()

    private val navigator: Navigator = AppNavigator(this,
        R.id.nav_host_fragment_activity_main)


    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var mainActivityScreenRouter: MainActivityScreenRouter

//    private val navigator: Navigator = object : AppNavigator(
//        this, R.id.nav_host_fragment_activity_main) {
//
//        override fun applyCommands(commands: Array<out Command>) {
//            super.applyCommands(commands)
//            supportFragmentManager.executePendingTransactions()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if(savedInstanceState == null)
            navigator.applyCommands(arrayOf<Command>(Forward(homeScreen())))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navView: BottomNavigationView = binding.navView
//
        val itemAbout = navView.menu.findItem(R.id.navigation_about)
        itemAbout.isVisible = false
        val itemHome = navView.menu.findItem(R.id.navigation_home)
        val itemFavorite = navView.menu.findItem(R.id.navigation_favorite)
//
        badgeFavorites = navView.getOrCreateBadge(R.id.navigation_favorite)
        badgeFavorites.isVisible = false
//
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.navigation_home, R.id.navigation_favorite, R.id.navigation_favorite)
//        )

        itemHome.setOnMenuItemClickListener {
//            navController.popBackStack()
//            navController.navigate(R.id.navigation_home)
            itemAbout.isVisible = false
            mainActivityScreenRouter.onHome()
            false
        }
        itemFavorite.setOnMenuItemClickListener {
//            navController.popBackStack()
//            navController.navigate(R.id.navigation_favorite)
            itemAbout.isVisible = false
            mainActivityScreenRouter.onFavorites()
            homeViewModel.clearFavorites()
            false
        }


//
//        setupActionBarWithNavController(this, navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.navigation_about -> {
//                    itemAbout.isVisible = true
//                }
//                else -> itemAbout.isVisible = false
//            }
//        }

        countFavorites()
        mainActivityScreenRouter.onAbout()
    }

    private fun countFavorites() {
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

    override fun onDestroy() {
        mainActivityScreenRouter.onDestroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}