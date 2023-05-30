package com.example.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokeproj.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import retrofit2.http.Url
import kotlinx.coroutines.flow.Flow

interface PokeApiService {
    @GET("pokemon/{id}?fields=id,name,height,weight")
    suspend fun getPokemon(@Path("id") id: Int): Response<Pokemon>

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): Response<PokemonSpecies>
}


data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val description: String?,
    val shortDescription: String?,
)
data class PokemonSpecies(
    val flavor_text_entries: List<FlavorTextEntry>,
    val genera: List<Genera>
)

data class FlavorTextEntry(
    val flavor_text: String,
    val language: Language
)

data class Genera(
    val genus: String,
    val language: Language
)

data class Language(
    val name: String
)
object ApiService {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val instance: PokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
    }
}

class Api : AppCompatActivity() {

    private val pokeApiService = ApiService.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poke_info)
    }

    suspend fun getPokemon(id: Int): Pokemon? = withContext(Dispatchers.IO) {
        val response: Response<Pokemon> = pokeApiService.getPokemon(id)
        val speciesResponse: Response<PokemonSpecies> = pokeApiService.getPokemonSpecies(id)

        if (response.isSuccessful && speciesResponse.isSuccessful) {
            val pokemon = response.body()

            // Cherchez l'entrée de texte de saveur en anglais pour être la description
            val description = speciesResponse.body()?.flavor_text_entries?.firstOrNull { it.language.name == "fr" }?.flavor_text

            // Cherchez l'entrée de genre en anglais pour être la description courte
            val shortDescription = speciesResponse.body()?.genera?.firstOrNull { it.language.name == "fr" }?.genus

            // Créez un nouvel objet Pokemon qui inclut la description et la description courte
            val updatedPokemon = pokemon?.copy(description = description, shortDescription = shortDescription)
            updatedPokemon
        } else {
            null
        }
    }
}
