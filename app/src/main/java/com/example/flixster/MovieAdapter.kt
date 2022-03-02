package com.example.flixster

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// 1. Adapter is for handling data collection and bind to view
// 2. This class extends RecyclerView.Adapter since adapter is an abstract class
//    requiring functions to be defined later.
private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: MutableList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    // To create the viewHolder defined above and return ViewHolder
    // Expensive operation : create a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // the LayoutInflator is to grab XML file as input and builds View objects from it
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    //To take data at specific position of data and bind it to ViewHolder.
    //Cheap: simply bind data to an existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    //To get number of movies item in the list
    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)

        fun bind(movie : Movie){
            tvTitle.text = movie.title
            tvOverview.text = movie.overviews

            Glide.with(context).load(movie.posterImageUrl).into(ivPoster)

            // for checking the orientation of your screen by accessing resources of landscape
            // mode of your screen
            val orientation = context.resources.configuration.orientation

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context).load(movie.posterImageUrl).into(ivPoster)
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context).load(movie.backdropImageUrl).into(ivPoster)
            }
        }
    }

}
