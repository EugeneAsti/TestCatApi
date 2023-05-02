package ru.aeyu.catapitestapp.domain.cicerone.repository.main

import com.github.terrakok.cicerone.ResultListenerHandler
import com.github.terrakok.cicerone.Router
import ru.aeyu.catapitestapp.domain.cicerone.Screens.aboutScreen
import ru.aeyu.catapitestapp.domain.cicerone.Screens.favoritesScreen
import ru.aeyu.catapitestapp.domain.cicerone.Screens.homeScreen
import ru.aeyu.catapitestapp.ui.about.AboutFragment

class MainActivityScreensRouterImpl(
    private val router: Router
) : MainActivityScreenRouter {

    private var resultListenerHandler: ResultListenerHandler? = null

    override fun onHome() {
        router.navigateTo(homeScreen())
    }

    override fun onFavorites() {
        router.navigateTo(favoritesScreen())
    }

    override fun onAbout() {
        resultListenerHandler = router.setResultListener(
            AboutFragment.EXTRA_CAT_ID_KEY
        ){data ->
            router.navigateTo(aboutScreen(data as String))
        }
    }

    override fun onDestroy() {
        resultListenerHandler?.dispose()
        router.exit()
    }
//    router.setResultListener(RESULT_KEY) { data ->
//        view.showPhoto(data as Bitmap)
//    }
//    router.navigateTo(SelectPhoto(RESULT_KEY))
//}
//
////send result
//fun onPhotoClick(photo: Bitmap) {
//    router.sendResult(resultKey, photoRes)
//    router.exit()
}