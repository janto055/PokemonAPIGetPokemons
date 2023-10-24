package com.example.lastfmapirandomtopartist

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions
import org.json.JSONObject
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonList: MutableList<String>
    private lateinit var nameList: MutableList<String>
    private lateinit var weightList: MutableList<String>
    private lateinit var rvPokemon: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize lists and views used by recycler

        rvPokemon = findViewById(R.id.pokemon_list)
        pokemonList = mutableListOf()
        nameList = mutableListOf()
        weightList = mutableListOf()

        getPokemonInfo()

        val button = findViewById<Button>(R.id.getPokeButton)
        button.setOnClickListener {
            getPokemonInfo()
        }
    }

    fun getPokemonInfo() {
        val client = AsyncHttpClient()

        val params = RequestParams()
        params["limit"] = "20"

        //iterate through a few random pokemon and add URL to pokemonList and other info to lists for recycler
        for (i in 0 until 20) {
            val pokemonName = Random.nextInt(0, 1292) // Replace with the desired Pokemon name

            client.get(
                "https://pokeapi.co/api/v2/pokemon/$pokemonName",
                params,
                object : JsonHttpResponseHandler() {
                    override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                        Log.d("DEBUG OBJECT", json.jsonObject.toString())

                        val pokeName = json.jsonObject.getString("name")
                        val pokeWeight = json.jsonObject.getString("weight")

                        // To explain this part, what I've done is realized that when getString("sprites"), the code copies
                        // ALL of the URLs that the pokeAPI spits out. So i make it so that it removes all but the first URL
                        // [substringAfter(":").substringBefore(",")] and removed the quotations before and after the string
                        // [substring(0); substring(1, pokeImage.length-1)]. I also had to remove the forward slashes that were
                        // outputted but the jsonObject for whatever reason [replace("\\","")]. ONLY THEN did I end up with
                        // one URL of the BACKSIDE of the pokemon >:(

                        var pokeImage = json.jsonObject.getString("sprites").substringAfter(":")
                            .substringBefore(",").substring(0)
                        pokeImage = pokeImage.substring(1, pokeImage.length - 1)

                        val resultString = pokeImage.replace("\\", "")

                        //add information pulled from JSON to Lists for recycler
                        pokemonList.add(resultString)
                        nameList.add(pokeName)
                        weightList.add(pokeWeight.toString())

                        Log.d("DEBUG OBJECT", pokemonList.toString())

                        val nameView = findViewById<TextView>(R.id.pokemonName)
                        val typeView = findViewById<TextView>(R.id.pokemonWeight)
                        val imageView = findViewById<ImageView>(R.id.pokemonImage)


                        Glide.with(this@MainActivity).load(resultString).into(imageView)

                        nameView.text = pokeName
                        typeView.text = pokeWeight

                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String,
                        throwable: Throwable?
                    ) {
                    }
                })

        }
        val adapter = PokemonAdapter(pokemonList,nameList,weightList)
        rvPokemon.adapter = adapter
        rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}
@GlideModule
class lastfmapirandomtopartistGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // Glide default Bitmap Format is set to RGB_565 since it
        // consumed just 50% memory footprint compared to ARGB_8888.
        // Increase memory usage for quality with:
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
    }
}


class PokemonAdapter (
    private val pokemonList: List<String>,
    private val nameList: List<String>,
    private val weightList: List<String>
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokeImage: ImageView
        val nameText: TextView
        val weightText: TextView
        init {
            // Find our RecyclerView item's ImageView for future use
            pokeImage = view.findViewById(R.id.pokemonImage)
            nameText = view.findViewById(R.id.pokemon_Name)
            weightText = view.findViewById(R.id.pokemon_Weight)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(pokemonList[position])
            .centerCrop()
            .into(holder.pokeImage)

        holder.nameText.text = nameList[position]
        holder.weightText.text = weightList[position]
    }

    override fun getItemCount() = pokemonList.size
}