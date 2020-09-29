package com.example.core.network

import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): JSONObject

    @GET("pokemon/{name}")
    suspend fun fetchPokemonInfo(@Path("name") name: String): JSONObject
}
