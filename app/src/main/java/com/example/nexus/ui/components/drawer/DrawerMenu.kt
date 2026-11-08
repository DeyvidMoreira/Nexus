package com.example.nexus.ui.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nexus.R
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.components.buttons.IconTextButton
import com.example.nexus.ui.components.dialogs.LogoutDialog
import com.example.nexus.ui.theme.DarkMediumGrey
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.MatteGreen
import com.example.nexus.ui.theme.MediumGrey
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom

@Composable
fun DrawerMenuContent(
    navController: NavController,
    viewModel: PwdGeneratorViewModel? = null
) {
    AccountMenuContent(navController, viewModel)
}

@Composable
fun AccountMenuContent(
    navController: NavController,
    viewModel: PwdGeneratorViewModel? = null
) {
    val showDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkMediumGrey)
    ) {
        ColumnBackgroundColor(
            horizontal = Alignment.Start,
            vertical = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                IconTextButton(
                    text = "Usuário",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_login),
                    onClick = {}
                )
                IconTextButton(
                    text = "Redefinir Senha",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_pwd),
                    onClick = {}
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                IconTextButton(
                    text = "Sair",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_logout),
                    onClick = {
                        showDialog.value = true
                    }
                )
            }
        }

        if (showDialog.value && viewModel != null) {
            LogoutDialog(
                onDismiss = { showDialog.value = false },
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@Composable
fun DrawerHeader() {
    ColumnBackgroundColor(
        horizontal = Alignment.CenterHorizontally,
        vertical = Arrangement.Top,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp),
            colors = CardDefaults.cardColors(MediumGrey),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SpacerCustom(paddingTop = 24.dp)
                TextCustom(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 46.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            listOf(LightGreen, NeonGreen, MatteGreen)
                        )
                    )
                )
                SpacerCustom(paddingTop = 24.dp)
                TextCustom(
                    text = stringResource(id = R.string.password_counter),
                    fontSize = 46.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            listOf(LightGreen, NeonGreen, MatteGreen)
                        )
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DrawerMenuContentPreview() {
    val navController = NavController(LocalContext.current)
    AccountMenuContent(navController)
}

@Composable
@Preview(showBackground = true)
fun DrawerHeaderPreview() {
    DrawerHeader()
}

