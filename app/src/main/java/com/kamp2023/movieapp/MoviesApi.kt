package com.kamp2023.movieapp

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/3/search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): MoviesResponse


}
