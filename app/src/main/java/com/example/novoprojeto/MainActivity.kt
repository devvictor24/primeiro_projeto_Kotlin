package com.example.novoprojeto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.novoprojeto.ui.theme.NovoProjetoTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NovoProjetoTheme {

                var inputNumber by remember { mutableStateOf("") }
                var result by remember { mutableStateOf("") }
                var isError by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Text(
                            text = "Encontrar Próximo Número Primo",
                            fontSize = 22.sp
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = inputNumber,
                            onValueChange = {
                                inputNumber = it
                                isError = false
                                result = ""
                            },
                            label = { Text("Digite um número:") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                            isError = isError
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                val number = inputNumber.toIntOrNull()

                                if (number == null || number <= 1) {
                                    result = "Digite um número inteiro maior que 1."
                                    isError = true
                                } else {
                                    val next = nextPrime(number)
                                    result = "Próximo primo: $next"
                                }
                            }
                        ) {
                            Text("Encontrar Próximo Primo")
                        }

                        Text(
                            text = result,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}


fun isPrime(number: Int): Boolean {
    if (number <= 1) return false
    if (number == 2) return true
    if (number % 2 == 0) return false

    val limit = sqrt(number.toDouble()).toInt()

    for (i in 3..limit step 2) {
        if (number % i == 0) return false
    }
    return true
}

fun nextPrime(start: Int): Int {
    var candidate = start + 1
    while (!isPrime(candidate)) {
        candidate++
    }
    return candidate
}