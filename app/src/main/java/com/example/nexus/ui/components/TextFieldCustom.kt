package com.example.nexus.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.R
import com.example.nexus.ui.theme.DarkMediumGrey
import com.example.nexus.ui.theme.MatteGreen
import com.example.nexus.ui.theme.NeonGreen


@Composable
fun TextFieldCustom(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    fontSize: TextUnit = 12.sp,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    icon: Int = R.drawable.ic_login,
    iconContentDescripition: String? =null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    showTrailingIcon: Boolean = false,
    onTrailingIconClick:(() -> Unit)? = null,
    trailingIcon: Int? = null


) {

    val adjustedFontSize = when {
        value.length > 40 -> 8.sp
        value.length > 20 -> 10.sp
        else -> fontSize
    }

    TextField(
        value,
        onValueChange,
        modifier.fillMaxWidth(),
        label = {
            Text(
                text = hint,
                fontSize = fontSize)
        },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = NeonGreen,
            unfocusedContainerColor = DarkMediumGrey,
            focusedTextColor = MatteGreen,
            focusedContainerColor = DarkMediumGrey,
            errorTextColor = Color.Red,
            cursorColor = NeonGreen,
            errorCursorColor = Color.Red,
            focusedLabelColor = MatteGreen,
            unfocusedLabelColor = NeonGreen,
            errorLabelColor = Color.Red,
            errorIndicatorColor = Color.Red,
            focusedIndicatorColor = MatteGreen,
            unfocusedIndicatorColor = NeonGreen
        ),
        shape = RoundedCornerShape(30.dp, 0.dp, 30.dp, 0.dp),
        maxLines = 1,
        textStyle = TextStyle(color = NeonGreen, fontSize = adjustedFontSize),
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = iconContentDescripition,
                tint = NeonGreen
            )
        },
        trailingIcon = {
            if (showTrailingIcon && trailingIcon != null){
                IconButton(onClick = {onTrailingIconClick?.invoke()}) {
                       Icon(
                           painter = painterResource(id = trailingIcon),
                           contentDescription = "Toggle visibility",
                           tint = NeonGreen
                       )

                }
            }
        },
        visualTransformation = visualTransformation
    )


}


@Composable
@Preview
private fun TextFieldCustomPreview() {

    var username by remember {
        mutableStateOf("")
    }

    TextFieldCustom(
        value = username,
        onValueChange = {
            username = it
        },
        hint = "Username",
        icon = R.drawable.ic_login,
        iconContentDescripition = "Login Icon"
    )
}