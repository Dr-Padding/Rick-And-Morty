package com.drawing.rickandmorty.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.drawing.rickandmorty.models.Result


@Database(
    entities = [Result::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun getCharacterDAO(): CharacterDAO

    companion object{
        @Volatile
        private var instance: com.drawing.rickandmorty.db.Database? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                com.drawing.rickandmorty.db.Database::class.java,
                "rick_and_morty_db.db"
            ).build()
    }
}