package ru.aeyu.catapitestapp.domain.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import ru.aeyu.catapitestapp.domain.di.CiceroneModule.cicerone
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CiceroneModule {

    val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter() : Router = cicerone.router

}

@Module
@InstallIn(ActivityComponent::class)
object NavigatorHolderModule {
    @Provides
    @ActivityScoped
    fun provideNavigatorHolder(): NavigatorHolder =
        cicerone.getNavigatorHolder()
}
