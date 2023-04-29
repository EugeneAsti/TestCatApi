package ru.aeyu.catapitestapp

import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import ru.aeyu.catapitestapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
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

//        val appBarConfiguration = AppBarConfiguration(navGraph = navController.graph)
        itemHome.setOnMenuItemClickListener {
            navController.popBackStack()
            navController.navigate(R.id.navigation_home)
            false
        }
        itemFavorite.setOnMenuItemClickListener {
            navController.popBackStack()
            navController.navigate(R.id.navigation_favorite)
            false
        }

//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_about -> {
                    itemAbout.isVisible = true
                }
                else -> itemAbout.isVisible = false
            }
        }
    }

}