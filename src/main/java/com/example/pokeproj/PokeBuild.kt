package com.example.testapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.http.GET
import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory

data class Pokemon(
    val id: Int,
    val pokedexId: Int,
    val name: String,
    val image: String,
    val sprite: String,
    val slug: String,
    val stats: Stats,
    val apiTypes: List<Type>,
    val apiGeneration: Int,
)

data class Stats(
    val HP: Int,
    val attack: Int,
    val defense: Int,
    val special_attack: Int,
    val special_defense: Int,
    val speed: Int
)

data class Type(
    val name: String,
    val image: String
)

interface PokebuildApi {
    @GET("api/v1/pokemon")
    suspend fun getPokemons(): Response<List<Pokemon>>
}
