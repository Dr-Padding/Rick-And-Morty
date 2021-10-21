package com.drawing.rickandmorty.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "characters"
)

data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)