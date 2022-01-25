package com.drawing.rickandmorty.models.episodes_from_rick_and_morty_api

data class AllEpisodesResponse(
    val info: Info,
    val results: MutableList<Result>
)