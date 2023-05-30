package com.example.pokeproj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import com.example.api.Api
import com.example.testapi.Pokemon
import com.example.testapi.PokemonHandler
import com.example.testapi.getAllPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokeInfo : AppCompatActivity(), PokemonHandler {
    private var PokeId = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poke_info)

        // Get the intent that started this activity
        val intent = intent

        // Get the integer data from the intent
        PokeId = intent.getIntExtra("PokeId", 1)

        println(PokeId)

        getAllPokemon(this, this)
    }
    override suspend fun handlePokemons(pokemons: List<Pokemon>) {
        withContext(Dispatchers.Main) {
            // Get Other info for this pokemon
            val api = Api()
            val pokemonApi = api.getPokemon(PokeId+1)

            // get elmement
            val Title = findViewById<TextView>(R.id.Title)
            val Pokename = findViewById<TextView>(R.id.Pokename)
            val PokeIdLabel = findViewById<TextView>(R.id.PokeId)
            val PokeSize = findViewById<TextView>(R.id.PokeSize)
            val PokeType = findViewById<TextView>(R.id.PokeType)
            val PokeWeight = findViewById<TextView>(R.id.PokeWeight)
            val PokeShortDescription = findViewById<TextView>(R.id.PokeShortDescription)
            val PokeLongDescription = findViewById<TextView>(R.id.PokeLongDescription)

            val StatPv = findViewById<TextView>(R.id.StatPv)
            val BarPV = findViewById<ProgressBar>(R.id.BarPV)

            val StatAttaque = findViewById<TextView>(R.id.StatAttaque)
            val barAttaque = findViewById<ProgressBar>(R.id.barAttaque)

            val StatDefense = findViewById<TextView>(R.id.StatDefense)
            val BarDefense = findViewById<ProgressBar>(R.id.BarDefense)

            val StatAttaqueSpe = findViewById<TextView>(R.id.StatAttaqueSpe)
            val BarAttaqueSpe = findViewById<ProgressBar>(R.id.BarAttaqueSpe)

            val StatDefenseSpe = findViewById<TextView>(R.id.StatDefenseSpe)
            val barDefenseSpe = findViewById<ProgressBar>(R.id.barDefenseSpe)

            val StatVitesse = findViewById<TextView>(R.id.StatVitesse)
            val BarVitesse = findViewById<ProgressBar>(R.id.BarVitesse)

            val StatTotal = findViewById<TextView>(R.id.StatTotal)
            val BarTotal = findViewById<ProgressBar>(R.id.BarTotal)

            // Update Element
            Title.text = pokemons[PokeId].name
            Pokename.text = pokemons[PokeId].name
            PokeIdLabel.text = pokemons[PokeId].id.toString()
            PokeSize.text = (pokemonApi?.height?.toDouble() ?: 0.0).toString() + "m"
            PokeType.text = ""
            pokemons[PokeId].apiTypes.forEach {
                PokeType.text = if (PokeType.text == "")
                    it.name.toString()
                    else PokeType.text.toString() + " - " +it.name.toString()
            }
            PokeWeight.text = ((pokemonApi?.weight?.toDouble() ?: 0.0)/10).toString() + "Kg"
            PokeShortDescription.text = pokemonApi?.shortDescription
            PokeLongDescription.text = pokemonApi?.description

            StatPv.text = pokemons[PokeId].stats.HP.toString()
            BarPV.progress = pokemons[PokeId].stats.HP

            StatAttaque.text = pokemons[PokeId].stats.attack.toString()
            barAttaque.progress = pokemons[PokeId].stats.attack

            StatDefense.text = pokemons[PokeId].stats.defense.toString()
            BarDefense.progress = pokemons[PokeId].stats.defense

            StatAttaqueSpe.text = pokemons[PokeId].stats.special_attack.toString()
            BarAttaqueSpe.progress = pokemons[PokeId].stats.special_attack

            StatDefenseSpe.text = pokemons[PokeId].stats.special_defense.toString()
            barDefenseSpe.progress = pokemons[PokeId].stats.special_defense

            StatVitesse.text = pokemons[PokeId].stats.speed.toString()
            BarVitesse.progress = pokemons[PokeId].stats.speed

            val total =
                pokemons[PokeId].stats.HP +
                pokemons[PokeId].stats.attack +
                pokemons[PokeId].stats.defense +
                pokemons[PokeId].stats.special_attack +
                pokemons[PokeId].stats.special_defense +
                pokemons[PokeId].stats.speed

            StatTotal.text = total.toString()
            BarTotal.progress = total / 6

        }
    }
}