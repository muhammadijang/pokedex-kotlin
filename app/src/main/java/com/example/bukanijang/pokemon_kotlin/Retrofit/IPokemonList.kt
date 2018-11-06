package com.example.bukanijang.pokemon_kotlin.Retrofit

import com.example.bukanijang.pokemon_kotlin.Model.Pokedex
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

interface IPokemonList {
    @get:GET("pokedex.json")
    val listPokemon:Observable<Pokedex>
}