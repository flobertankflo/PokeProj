package com.example.testapi

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.pokeproj.MainActivity
import com.example.pokeproj.PokeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.http.GET
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
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
fun getAllPokemon(Activity : LifecycleOwner, Handler: PokemonHandler){
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokebuildapi.fr/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val apiService = retrofit.create(PokebuildApi::class.java)

    Activity.lifecycleScope.launch(Dispatchers.IO) {
        val response = apiService.getPokemons()
        if (response.isSuccessful) {
            val pokemons = response.body()
            if (pokemons != null) {
                Handler.handlePokemons(pokemons)
            }
        } else {
            // handle error
        }
    }
}
interface PokemonHandler {
    suspend fun handlePokemons(pokemons: List<Pokemon>)
}