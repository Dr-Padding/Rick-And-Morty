package com.drawing.rickandmorty.models.characters

data class AllCharactersResponse(
    val info: Info,
    val results: MutableList<Result>
)