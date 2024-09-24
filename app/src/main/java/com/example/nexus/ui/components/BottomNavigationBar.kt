package com.example.nexus.ui.components

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus.R
import com.example.nexus.ui.navigation.AuthNavigationGraph
import com.example.nexus.ui.navigation.MainNavigationGraph
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.MatteGreen
import com.example.nexus.ui.theme.NeonGreen

/**
 * Barra de navegação inferior que exibe os ícones de navegação entre diferentes telas.
 *
 * A barra de navegação contém ícones que representam diferentes telas da aplicação, e destaca
 * o ícone selecionado de acordo com a rota atual. Navega entre as telas usando o [navController].
 *
 * @param navController Controlador de navegação que gerencia as transições entre as telas da aplicação.
 */
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        MainNavigationGraph.DASHBOARD,
        MainNavigationGraph.TEST1,
        MainNavigationGraph.TEST2,
        MainNavigationGraph.TEST3,
    )

    BottomNavigation(
        backgroundColor = DarkGrey,
        contentColor = Color.Red
    ) {
        // Obtém a rota atual a partir da entrada de back stack do navController
        val currentRoute = navController.currentBackStackEntry?.destination?.route

        items.forEach { screen ->
            // Verifica se o item atual da barra de navegação é o selecionado
            val isSelected = currentRoute == screen
            // Anima o tamanho do ícone dependendo se está selecionado ou não
            val iconSize by animateDpAsState(targetValue = if (isSelected) 30.dp else 24.dp)
            // Anima a cor do ícone dependendo se está selecionado ou não
            val iconColor by animateColorAsState(targetValue = if (isSelected) MatteGreen else NeonGreen)

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(
                            id = when (screen) {
                                MainNavigationGraph.DASHBOARD -> R.drawable.icon_home
                                MainNavigationGraph.TEST1 -> R.drawable.icon_code
                                MainNavigationGraph.TEST2 -> R.drawable.icon_email
                                MainNavigationGraph.TEST3 -> R.drawable.icon_user
                                else -> R.drawable.ic_pwd
                            }
                        ),
                        contentDescription = screen,
                        modifier = Modifier.size(iconSize), // Define o tamanho do ícone animado
                        tint = iconColor // Define a cor do ícone animado
                    )
                },
                selected = isSelected,
                onClick = {
                    //Verifica se a rota já está ativa antes de navegar
                    if (currentRoute != screen) {
                        navController.navigate(screen) {
                            // Define a navegação, mantendo a tela do dashboard no stack
                            popUpTo(MainNavigationGraph.DASHBOARD) {
                                inclusive = false
                            }
                        }
                    }
                },
                alwaysShowLabel = false, // Oculta o rótulo do item na barra de navegação
                selectedContentColor = Color.Green, // Cor quando o item está selecionado
                unselectedContentColor = Color.Yellow // Cor quando o item não está selecionado
            )
        }
    }
}

@Preview
@Composable
private fun AnimateBottomBar() {
    val naviControler = rememberNavController()

    BottomNavigationBar(naviControler)


}