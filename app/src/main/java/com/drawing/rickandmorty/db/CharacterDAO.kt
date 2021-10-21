package com.drawing.rickandmorty.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drawing.rickandmorty.models.Result


@Dao
interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(result: Result): Long

    @Query("SELECT * FROM characters")
    fun getAllCharacters() : LiveData<List<Result>>


}