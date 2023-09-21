package ru.aeyu.catapitestapp.data.local.data_source

import androidx.room.*
import ru.aeyu.catapitestapp.data.local.models.CatLocal

@Dao
interface CatsDao {

    @Query("SELECT * FROM FavoriteCats")
    suspend fun getFavoriteCatsLocal(): List<CatLocal>

    @Upsert
    suspend fun saveCats(cats: List<CatLocal>)

    @Upsert
    suspend fun saveCat(catLocal: CatLocal)

}
