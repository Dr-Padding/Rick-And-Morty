package com.drawing.rickandmorty.models

data class AllCharactersResponse(
    val info: Info,
    val results: MutableList<Result>
)