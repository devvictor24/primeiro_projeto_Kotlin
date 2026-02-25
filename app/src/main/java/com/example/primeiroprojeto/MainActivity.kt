package com.example.primeiroprojeto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.primeiroprojeto.ui.theme.PrimeiroProjetoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PrimeiroProjetoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    RgbScreen()
                }
            }
        }
    }
}

@Composable
fun RgbScreen() {

    var red by remember { mutableStateOf("") }
    var green by remember { mutableStateOf("") }
    var blue by remember { mutableStateOf("") }
    var displayColor by remember { mutableStateOf(Color.White) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "🎨 Criador de Cores RGB",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )

        Text(
            text = "Digite dois caracteres hexadecimais (0-9, A-F) para cada canal.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray
        )
        ColorField("Canal Vermelho", red, Color.Red) {
            red = it
        }

        ColorField("Canal Verde", green, Color(0xFF2E7D32)) {
            green = it
        }

        ColorField("Canal Azul", blue, Color.Blue) {
            blue = it
        }

        Button(
            onClick = {

                if (!isValidHex(red) || !isValidHex(green) || !isValidHex(blue)) {
                    errorMessage =
                        "⚠️ Cada campo precisa ter exatamente 2 caracteres hexadecimais."
                    return@Button
                }

                val hexColor = "#${red}${green}${blue}"

                try {
                    displayColor = Color(hexColor.toColorInt())
                    errorMessage = null
                } catch (e: Exception) {
                    errorMessage = "Cor inválida!"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar Cor")
        }

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        Text(
            text = "Código gerado: #${red}${green}${blue}".uppercase(),
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(displayColor, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {

            val textColor =
                if (displayColor.luminance() < 0.5f)
                    Color.White
                else
                    Color.Black

            Text(
                text = "#${red}${green}${blue}".uppercase(),
                color = textColor,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun ColorField(
    label: String,
    value: String,
    labelColor: Color,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            val filtered = it.filter { char ->
                char in '0'..'9' ||
                        char in 'A'..'F' ||
                        char in 'a'..'f'
            }.take(2)

            onChange(filtered)
        },
        label = { Text(label, color = labelColor) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

fun isValidHex(input: String): Boolean {
    return input.length == 2 &&
            input.all {
                it in '0'..'9' ||
                        it in 'A'..'F' ||
                        it in 'a'..'f'
            }
}