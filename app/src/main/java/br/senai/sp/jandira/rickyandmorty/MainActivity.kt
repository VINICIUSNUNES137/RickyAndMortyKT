package br.senai.sp.jandira.rickyandmorty

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.rickyandmorty.model.CharacterList
import br.senai.sp.jandira.rickyandmorty.model.Info
import br.senai.sp.jandira.rickyandmorty.service.RetrofitFactory
import br.senai.sp.jandira.rickyandmorty.ui.theme.RickyAndMortyTheme
import coil.compose.AsyncImage
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

    Column(modifier = Modifier.padding(start = 32.dp, end = 32.dp)) {
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


        })

        {


            Text(text = "List all Characters")
        }
        Row() {
            Text(text = "TOTAL: ",
                fontWeight = FontWeight(800),
                color = Color.Blue,
                modifier = Modifier.size(width = 50.dp, height = 20.dp)
                )
            Text(
                text = "${info.count}",
                textAlign = TextAlign.End,
                modifier = Modifier.size(width = 40.dp, height = 20.dp)

            )
        }
        Row() {
            Text(
                text = "PAGES: ",
                fontWeight = FontWeight(800),
                color = Color.Blue,
                modifier = Modifier.size(width = 50.dp, height = 20.dp)
            )
            Text(
                text = "${info.pages}",
                textAlign = TextAlign.End,
                modifier = Modifier.size(width = 40.dp, height = 20.dp)

            )
        }

        LazyColumn() {
            items(results) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                backgroundColor = Color(0xFF4baec6)) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Card(shape = CircleShape) {
                            AsyncImage(
                                model = it.image,
                                contentDescription = "avatar do ${it.name}",
                                modifier = Modifier
                                    .size(80.dp)

                            )
                        }    
                    Spacer(modifier = Modifier.width(16.dp))
                    Column() {
                        Text(text = it.name, fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        Text(text = it.species, color = Color.White)
                        Text(text = it.location.name, color = Color.White, fontSize = 12.sp)

                    }
                    }
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