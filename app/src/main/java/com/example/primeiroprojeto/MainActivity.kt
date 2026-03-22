package com.example.primeiroprojeto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.primeiroprojeto.ui.theme.PrimeiroProjetoTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val FULL_NAME_KEY = "FULL_NAME_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PrimeiroProjetoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    var fullName by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = stringResource(R.string.header_text))

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text(stringResource(R.string.full_name_label)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (fullName.isNotEmpty()) {

                    val intent = Intent(context, WelcomeActivity::class.java)
                    intent.putExtra(MainActivity.FULL_NAME_KEY, fullName)

                    context.startActivity(intent)

                } else {
                    Toast.makeText(
                        context,
                        "Digite seu nome!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {
            Text(text = stringResource(R.string.submit_button_text))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    PrimeiroProjetoTheme {
        MainScreen()
    }
}