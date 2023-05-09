package br.senai.sp.jandira.rickyandmorty.service

import com.google.gson.Gson
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val URL_BASE = "https://rickandmortyapi.com/api/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getCharacterService(): CharacterService {
        return retrofitFactory.create(CharacterService::class.java)
    }


}