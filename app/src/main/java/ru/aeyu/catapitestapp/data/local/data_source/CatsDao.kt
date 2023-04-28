package ru.aeyu.evoreceipt.data.local.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.aeyu.catapitestapp.data.local.models.BreedLocal
import ru.aeyu.catapitestapp.data.local.models.CatLocal

@Dao
interface CatsDao {

    @Query("SELECT * FROM Cats")
    fun getCatsLocal(): Flow<List<CatLocal>>

    @Query("SELECT * FROM CatBreeds WHERE catId = :catId")
    fun getCatBreeds(catId: String): Flow<List<BreedLocal>>

    @Upsert
    suspend fun saveCats(cats: List<CatLocal>)

    @Upsert
    suspend fun saveBreeds(cats: List<BreedLocal>)


}
