package com.example.lastfmapirandomtopartist

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
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
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.getPokeButton)
        button.setOnClickListener{
            getPokemonInfo()
        }
    }

    fun getPokemonInfo() {
        val client = AsyncHttpClient()

        val params = RequestParams()
        params["limit"] = "5"

        val pokemonName = Random.nextInt(0,1292) // Replace with the desired Pokemon name

        client.get("https://pokeapi.co/api/v2/pokemon/$pokemonName", params, object : JsonHttpResponseHandler() {
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

                var pokeImage = json.jsonObject.getString("sprites").substringAfter(":").substringBefore(",").substring(0)
                pokeImage = pokeImage.substring(1, pokeImage.length-1)

                val resultString = pokeImage.replace("\\", "")

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