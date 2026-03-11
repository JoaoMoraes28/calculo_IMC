package com.example.calculadora_imc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora_imc.ui.theme.Calculadora_IMCTheme
import kotlin.math.roundToInt
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculadora_IMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Forms(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Forms(modifier: Modifier) {
    Column(
        modifier = modifier
            .padding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var peso by remember {
            mutableStateOf("")
        }

        var altura by remember {
            mutableStateOf("")
        }

        var result by remember {
            mutableStateOf("")
        }

        var colorImc by remember {
            mutableStateOf(Color.LightGray)
        }

        var message by remember {
            mutableStateOf("Seus Dados")
        }

        val yellow = colorResource(R.color.yellowImc)
        val green = colorResource(R.color.greenImc)
        val orange = colorResource(R.color.orangeImc)

        var inputModififer = Modifier
            .fillMaxWidth()
            .height(60.dp)

        var colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedBorderColor = colorResource(R.color.defaultBlue),
        )

        Column(
            modifier = Modifier
                .background(colorResource(R.color.defaultBlue))
                .fillMaxWidth()
                .height(200.dp)
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                contentDescription = "Icone IMC",
                painter = painterResource(R.drawable.bmi),
                modifier = Modifier.size(45.dp)
            )
            Text(
                text = "Calculadora IMC",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = W500,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-30).dp)
                    .height(350.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.card)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$message",
                        color = colorResource(R.color.defaultBlue),
                        fontSize = 20.sp,
                        fontWeight = W600
                    )

                    OutlinedTextField(
                        value = altura,
                        onValueChange = { newValue ->
                            altura = newValue
                        },
                        placeholder = {
                            Text(
                                text = "Altura (Ex: 1.70)"
                            )
                        },
                        modifier = inputModififer,
                        colors = colors
                    )

                    OutlinedTextField(
                        value = peso,
                        onValueChange = { newValue ->
                            peso = newValue
                        },
                        placeholder = {
                            Text(
                                text = "Peso",
                            )
                        },
                        modifier = inputModififer,
                        colors = colors
                    )

                    Button(
                        onClick = {
                            if (peso.toDoubleOrNull() != null && altura.toDoubleOrNull() != null) {
                                val pesoDouble: Double = peso.toDouble()
                                val alturaDouble: Double = altura.toDouble()

                                val imc = calculateImc(pesoDouble, alturaDouble)

                                if (imc < 18.5) {
                                    result = "$imc Abaixo do peso"
                                    colorImc = yellow

                                } else if (imc >= 18.5 && imc < 25.0) {
                                    result = "$imc Peso ideal"
                                    colorImc = green

                                } else if (imc >= 25 && imc < 30) {
                                    result = "$imc Levemente acima do peso"
                                    colorImc = orange

                                } else if (imc >= 30) {
                                    result = "$imc Acima do peso"
                                    colorImc = Color.Red

                                }

                                message = "Seus dados"

                            } else {
                                message = "Peso ou altura inválidos!"

                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.defaultBlue)
                        )
                    ) {
                        Text(
                            text = "Calcular"
                        )
                    }
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorImc),
                Alignment.Center
            ) {
                Text(
                    text = "$result",
                    fontSize = 25.sp,
                    fontWeight = W600,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

fun calculateImc(peso: Double, altura: Double): Double {
    val result: Double = peso / (altura * 2)
    return (result * 10).roundToInt() / 10.0

}

