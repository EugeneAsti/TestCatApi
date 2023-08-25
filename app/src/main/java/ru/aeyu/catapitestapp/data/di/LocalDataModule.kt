package ru.aeyu.catapitestapp.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.aeyu.catapitestapp.data.local.MainDataBase
import ru.aeyu.catapitestapp.data.local.data_source.CatsDao
import ru.aeyu.catapitestapp.data.local.repository.FavoriteCatsLocalRepository
import ru.aeyu.catapitestapp.data.local.repository.FavoriteCatsLocalRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun provideOutletDao(
        db: MainDataBase
    ) : CatsDao {
        return db.catsDao()
    }

    @Provides
    @Singleton
    fun provideMainDataBase(
        @ApplicationContext context: Context
    ): MainDataBase {
       return Room.databaseBuilder(
           context,
           MainDataBase::class.java,
           MainDataBase.DB_NAME
       ).fallbackToDestructiveMigration()
           // отключает режим WAL
           .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
           .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteCatsLocalRepository(
        catsDao: CatsDao): FavoriteCatsLocalRepository =
        FavoriteCatsLocalRepositoryImpl(catsDao)

}