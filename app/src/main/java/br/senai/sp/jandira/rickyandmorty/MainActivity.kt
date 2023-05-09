package br.senai.sp.jandira.rickyandmorty

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.senai.sp.jandira.rickyandmorty.model.CharacterList
import br.senai.sp.jandira.rickyandmorty.model.Info
import br.senai.sp.jandira.rickyandmorty.service.RetrofitFactory
import br.senai.sp.jandira.rickyandmorty.ui.theme.RickyAndMortyTheme
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickyAndMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var results by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.rickyandmorty.model.Character>())
    }

    var info by remember {
        mutableStateOf(Info(0, 0, null, null))
    }

    Column() {
        Button(onClick = {

            //Chamada par a API

            val call = RetrofitFactory().getCharacterService().getCharactersByPage(10)

            call.enqueue(object : Callback<CharacterList>{
                override fun onResponse(
                    call: Call<CharacterList>,
                    response: Response<CharacterList>
                ) {
                    results = response.body()!!.results
                    info = response.body()!!.info

                }

                override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                    TODO("Not yet implemented")
                    Log.i(
                        "ds2m",
                        "onFailure: ${t.message}"
                    )
                }

            })


        }) {
            Text(text = "List all Characters")
        }
        LazyColumn() {
            items(results) {
                Column() {
                    Text(text = it.name)
                    Text(text = it.species)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RickyAndMortyTheme {
        Greeting("Android")
    }
}