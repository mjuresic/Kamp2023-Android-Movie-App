package com.kamp2023.movieapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    val movies = MutableStateFlow<List<Movie>>(listOf())

    init {
        //searchMovies("avengers")
    }

    fun searchMovies(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val tmdbApi = retrofit.create(MoviesApi::class.java)
        val apiKey = "efeb61b0a4725b8470b4c0a03117acf0" // Replace with your actual TMDB API Key

        viewModelScope.launch {
            val result = tmdbApi.searchMovies(apiKey, query)
            movies.value = result.results
        }
    }
}
