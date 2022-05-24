package com.example.examenma.chooseSectionScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ChooseSectionScreen(
    onSubmit: (String) -> Unit
) {
    Scaffold(
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    content = {
                        Text(text = "Owner Section")
                    },
                    onClick = {
                        onSubmit("owner")
                    }
                )
                Button(
                    content = {
                        Text(text = "Client Section")
                    },
                    onClick = {
                        onSubmit("client")
                    }
                )
            }
        }
    )
}