package ru.aeyu.evoreceipt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.aeyu.catapitestapp.data.local.models.BreedLocal
import ru.aeyu.catapitestapp.data.local.models.CatLocal
import ru.aeyu.evoreceipt.data.local.data_source.CatsDao

@Database(
    entities = [BreedLocal::class, CatLocal::class],
    version = 1, exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class MainDataBase : RoomDatabase() {

    abstract fun catsDao(): CatsDao

    companion object {
        const val DB_NAME = "cats.db"
    }
}
