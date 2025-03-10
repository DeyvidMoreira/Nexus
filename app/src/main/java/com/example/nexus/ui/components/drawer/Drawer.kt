package com.example.nexus.ui.components.drawer

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.nexus.ui.states.GeneratorState
import com.example.nexus.ui.theme.DarkMediumGrey
import kotlinx.coroutines.launch


@Composable
fun Drawer(navController: NavController, content: @Composable (onOpenDrawer: () -> Unit) -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = DarkMediumGrey
            ) {
                DrawerMenuContent(navController)
            }
        },
        scrimColor = Color.Black.copy(alpha = 0.5f)
    ) {
        content {
            scope.launch {
                drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {

}

