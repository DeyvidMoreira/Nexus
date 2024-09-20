package com.example.nexus.ui.components


import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextButtonCustom(onClick: () -> Unit, content: @Composable () -> Unit){

    TextButton (onClick =onClick){
        content()
    }


}



@Preview
@Composable
private fun TextCustomPreview(){
    TextButtonCustom(onClick = {println("Texto clicado")}){
        Text(text = "Click-me")
    }
}