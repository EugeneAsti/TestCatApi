package ru.aeyu.catapitestapp.domain.cicerone.repository.home

import com.github.terrakok.cicerone.Router
import ru.aeyu.catapitestapp.ui.about.AboutFragment

class HomeFragmentScreenRouterImpl(
    private val router: Router
) : HomeFragmentScreenRouter {


    override fun onCatClicked(catId: String) {
        router.sendResult(AboutFragment.EXTRA_CAT_ID_KEY, catId)
    }

}