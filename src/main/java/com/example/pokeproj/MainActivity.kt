package com.example.testapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.pokeproj.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokebuildapi.fr/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val apiService = retrofit.create(PokebuildApi::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            val response = apiService.getPokemons()
            if (response.isSuccessful) {
                val pokemons = response.body()
                if (pokemons != null) {
                    handlePokemons(pokemons)
                }
            } else {
                // handle error
            }
        }
    }

    private suspend fun handlePokemons(pokemons: List<Pokemon>) {
        withContext(Dispatchers.Main) {
            //val monTextView = findViewById<TextView>(R.id.test)
            //monTextView.text = pokemons[0].name
            println(pokemons[0].name)
        }
    }
}
