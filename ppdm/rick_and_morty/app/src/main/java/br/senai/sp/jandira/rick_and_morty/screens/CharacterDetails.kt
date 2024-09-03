package br.senai.sp.jandira.rick_and_morty.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.senai.sp.jandira.rick_and_morty.model.Character
import br.senai.sp.jandira.rick_and_morty.service.RetrofitFactory
import br.senai.sp.jandira.rick_and_morty.ui.theme.Rick_and_MortyTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CharacterDetails(modifier: Modifier = Modifier) {
    var id by remember {
        mutableStateOf("")
    }

    var character by remember {
        mutableStateOf(Character())
    }

    Column {
        OutlinedTextField(
            value = id,
            onValueChange = {
                id = it
            },
            label = {
                Text(text = "Digite o id do personagem")
            }
        )

        Button(
            onClick = {
                val callCharacter = RetrofitFactory()
                    .getCharacterService()
                    .getCharacterById(id.toInt())

                callCharacter.enqueue(object : Callback<Character> {
                    override fun onResponse(p0: Call<Character>, response: Response<Character>) {
                        character = response.body()!!
                    }

                    override fun onFailure(p0: Call<Character>, p1: Throwable) {
                    }

                })
            }
        ) {
            Text(text = "Buscar Personagens")
        }
        Text(text = "Nome: ${character.name}")
        Text(text = "Status: ${character.status}")
        Text(text = "Espécie: ${character.species}")
        Text(text = "Origem: ${character.origin?.name}")
        Text(text = "Localização: ${character.location?.name}")
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsPreview() {
    Rick_and_MortyTheme {
        CharacterDetails()
    }
}