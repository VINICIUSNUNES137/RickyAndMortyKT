package br.senai.sp.jandira.rickyandmorty.service

import br.senai.sp.jandira.rickyandmorty.model.CharacterList
import retrofit2.Call
import retrofit2.http.GET

interface CharacterService {

    //https://rickandmortyapi.com/api/

    @GET("character")
    fun getCharacters(): Call<CharacterList>

    @GET("character/{id}")
    fun getCharacter(): Call<br.senai.sp.jandira.rickyandmorty.model.Character>
}
