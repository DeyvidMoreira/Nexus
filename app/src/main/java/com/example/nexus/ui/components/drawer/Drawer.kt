package com.example.nexus.ui.components.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.nexus.ui.components.PasswordModalBottomSheet
import com.example.nexus.ui.components.TopBar
import com.example.nexus.ui.theme.DarkMediumGrey
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(content: @Composable (onOpenDrawer: () -> Unit) -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = DarkMediumGrey
            ) {
                DrawerMenuContent(onOpenBottomSheet = {
                    scope.launch {
                        drawerState.close()
                        bottomSheetState.show()
                    }

                })
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

