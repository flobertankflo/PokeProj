package com.example.pokeproj

import CustomAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.Pokemon
import com.example.testapi.PokemonHandler
import com.example.testapi.getAllPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), PokemonHandler {
    public var test = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllPokemon(this, this)
        val saisie_texte = findViewById<EditText>(R.id.editTextRecherche)
        val boutonRecherche = findViewById<ImageButton>(R.id.imageButton4)
        boutonRecherche.setOnClickListener {
            val text = saisie_texte.text.toString()
            saisie_texte.setVisibility(View.VISIBLE);
        }
        saisie_texte.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_UP) {
                saisie_texte.setVisibility(View.GONE);
                saisie_texte.setText("")
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
    override suspend fun handlePokemons(pokemons: List<Pokemon>) {
        withContext(Dispatchers.Main) {
            val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
            recyclerview.layoutManager = LinearLayoutManager(test)
            val data = ArrayList<ItemsViewModel>()
            for (i in 0..800) {
                data.add(ItemsViewModel(pokemons[i].image, pokemons[i].name))
            }
            // This will pass the ArrayList to our Adapter
            val adapter = CustomAdapter(data)

            // Setting the Adapter with the recyclerview
            recyclerview.adapter = adapter
        }
    }
}
