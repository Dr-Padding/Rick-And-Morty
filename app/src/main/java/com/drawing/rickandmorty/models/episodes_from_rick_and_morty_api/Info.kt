package com.drawing.rickandmorty.models.episodes_from_rick_and_morty_api

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)