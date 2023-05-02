package ru.aeyu.catapitestapp.domain.cicerone

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.aeyu.catapitestapp.ui.about.AboutFragment
import ru.aeyu.catapitestapp.ui.favorite.FavoritesFragment
import ru.aeyu.catapitestapp.ui.home.HomeFragment

object Screens {
//    fun mainScreen() = ActivityScreen {
//        Intent(it, MainActivity::class.java)
//    }

    fun homeScreen() = FragmentScreen {
        HomeFragment()
    }
    fun favoritesScreen() = FragmentScreen {
        FavoritesFragment()
    }
    fun aboutScreen(catId: String) = FragmentScreen {
        AboutFragment.getNewInstance(catId)
    }
}