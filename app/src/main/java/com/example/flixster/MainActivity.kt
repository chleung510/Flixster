package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException


private const val TAG = "MainActivity"
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies : RecyclerView // initialize rvMovies(type RecyclerView) late on

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies) // We initialize rvMovies here

        // First arguement refers to context(MainActivity), and 2nd is movies refers to list of movies.
        val movieAdapter = MovieAdapter(this, movies)
        rvMovies.adapter = movieAdapter
        // bind context(MainActivity) to LinearLayoutManager and set it as our layout manager
        // linearLayoutManager is a inbuilt layout manager.
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        // 2nd param is a annoy onResponse handler.
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                // For logging whatever result we got when experiencing an error.
                Log.e(TAG,  "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                // For logging whatever result we got when successful.
                Log.i(TAG, "onSuccess: JSON data $json")

                try {
                    // To get list of movie objects from JSON api and store it in movieJsonArray.
                    val movieJsonArray = json.jsonObject.getJSONArray( "results" )
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged() // For notifying the adapter of the data change
                    Log.i(TAG, "Movie list: $movies")
                } catch (e: JSONException) {
                    Log.e(TAG, "Encountered Exception $e")
                }

            }

        })
    }
}