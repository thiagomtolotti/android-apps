package br.ufpr.hpapi

import retrofit2.http.GET
import retrofit2.http.Path

interface HPApi {
    @GET("/api/character/{id}")
    suspend fun getCharacterDetails(@Path("id") id: String): CharacterDetails
}