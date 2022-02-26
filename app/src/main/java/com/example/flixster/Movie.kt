/*
* For parsing out individual object and displaying information for individual movie in our UI
*
* */
package com.example.flixster

import org.json.JSONArray

data class Movie(
    val movieId: Int,
    private val posterPath: String,
    val title: String,
    val overviews: String,
){
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    companion object{
        // For iterating the JSONArray and return list of movie data classes
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie>{
            val movies = mutableListOf<Movie>()  // the list we are going to return

            // traverse movieJsonArray from 0 to the end.
            for (i in 0 until movieJsonArray.length()){
                val movieJson = movieJsonArray.getJSONObject(i) // to get each object in the JSONArray

                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")
                    )
                )
            }
            return movies;
        }
    }
}