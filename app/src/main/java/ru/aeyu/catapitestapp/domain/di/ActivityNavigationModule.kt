package ru.aeyu.catapitestapp.domain.di

import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import ru.aeyu.catapitestapp.domain.cicerone.repository.home.HomeFragmentScreenRouter
import ru.aeyu.catapitestapp.domain.cicerone.repository.home.HomeFragmentScreenRouterImpl
import ru.aeyu.catapitestapp.domain.cicerone.repository.main.MainActivityScreenRouter
import ru.aeyu.catapitestapp.domain.cicerone.repository.main.MainActivityScreensRouterImpl

@Module
@InstallIn(ActivityComponent::class)
object ActivityNavigationModule {

    @Provides
    @ActivityScoped
    fun provideScreenRouter(router: Router): MainActivityScreenRouter = MainActivityScreensRouterImpl(router)
}

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelNavigationModule {

    @Provides
    @ViewModelScoped
    fun provideScreenRouter(router: Router): HomeFragmentScreenRouter = HomeFragmentScreenRouterImpl(router)
}

