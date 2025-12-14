package br.ufpr.hpapi.api

import br.ufpr.hpapi.api.StaffMemberItem
import retrofit2.http.GET
import retrofit2.http.Path

interface HPApi {
    @GET("api/character/{id}")
    suspend fun getCharacterDetails(@Path("id") id: String): List<CharacterDetails>

    @GET("api/characters/staff")
    suspend fun listStaff(): List<StaffMemberItem>

    @GET("api/characters/house/{house}")
    suspend fun listHouseMembers(@Path("house") house: String): List<HouseMemberItem>
}