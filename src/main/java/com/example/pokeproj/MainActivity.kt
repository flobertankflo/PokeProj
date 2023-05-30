package com.example.pokeproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.testapi.Pokemon
import com.example.testapi.PokemonHandler
import com.example.testapi.getAllPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), PokemonHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btnOpen)
        // Set up the click listener for the button
        button.setOnClickListener {
            // Create an intent to open the second activity
            val intent = Intent(this, PokeInfo::class.java)

            intent.putExtra("PokeId", 3) // A mettre l'id du pokemon
            // Start the new activity
            startActivity(intent)
        }
        getAllPokemon(this, this)
    }
    override suspend fun handlePokemons(pokemons: List<Pokemon>) {
        withContext(Dispatchers.Main) {
            //val monTextView = findViewById<TextView>(R.id.test)
            //monTextView.text = pokemons[0].name
            println(pokemons[0].name)
        }
    }
}
