package com.example.nexus.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus.R
import com.example.nexus.ui.components.BottomNavigationBar
import com.example.nexus.ui.components.CustomCard
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
/**
 * Função composable para a tela do Dashboard.
 * Exibe informações do usuário e opções de navegação.
 *
 * @param navController Controlador de navegação para gerenciar a navegação do aplicativo.
 */
@Composable
fun DashboardScreen(navController: NavController) {

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { contentPadding ->
        ColumnBackgroundColor {
            // Cartão superior com informações do perfil do usuário
            CustomCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(contentPadding),
                shape = RoundedCornerShape(0.dp, 0.dp, 100.dp, 100.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Imagem de perfil
                    Image(
                        painter = painterResource(id = R.drawable.profile_image),
                        contentDescription = "Imagem de perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    SpacerCustom(paddingBottom = 8.dp)

                    // Nome do usuário
                    TextCustom(
                        text = "Nome do Usuário",
                        fontSize = 22.sp
                    )

                    SpacerCustom(paddingBottom = 8.dp)

                    // Email do usuário
                    TextCustom(
                        text = "usuario@exemplo.com",
                        fontSize = 16.sp
                    )
                }
            }

            // Botões de navegação
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Primeira linha de botões
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Botão 1
                    CustomCard(
                        modifier = Modifier
                            .width(180.dp)
                            .height(180.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(30.dp, 0.dp, 20.dp, 0.dp),
                    ) {
                        IconButton(onClick = { /* Ação do botão */ }) {
                            Icon(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(56.dp),
                                painter = painterResource(id = R.drawable.icon_code),
                                contentDescription = "Funcionalidade 1",
                                tint = NeonGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botão 2
                    CustomCard(
                        modifier = Modifier
                            .width(180.dp)
                            .height(180.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(0.dp, 30.dp, 0.dp, 20.dp),
                    ) {
                        IconButton(onClick = { /* Ação do botão */ }) {
                            Icon(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(56.dp),
                                painter = painterResource(id = R.drawable.ic_pwd),
                                contentDescription = "Funcionalidade 2",
                                tint = NeonGreen
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Segunda linha de botões
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Botão 3
                    CustomCard(
                        modifier = Modifier
                            .width(180.dp)
                            .height(180.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(30.dp, 0.dp, 20.dp, 0.dp),
                    ) {
                        IconButton(onClick = { /* Ação do botão */ }) {
                            Icon(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(56.dp),
                                painter = painterResource(id = R.drawable.icon_user),
                                contentDescription = "Funcionalidade 3",
                                tint = NeonGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botão 4
                    CustomCard(
                        modifier = Modifier
                            .width(180.dp)
                            .height(180.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(0.dp, 30.dp, 0.dp, 20.dp),
                    ) {
                        IconButton(onClick = { /* Ação do botão */ }) {
                            Icon(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(56.dp),
                                painter = painterResource(id = R.drawable.icon_email),
                                contentDescription = "Funcionalidade 4",
                                tint = NeonGreen
                            )
                        }
                    }
                }
            }
        }
    }
}



@Preview
@Composable
private fun DashboardScreeenPreview() {
    val navController = rememberNavController()
    DashboardScreen(navController)
}