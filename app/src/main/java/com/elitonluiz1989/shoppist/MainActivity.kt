package com.elitonluiz1989.shoppist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.elitonluiz1989.shoppist.shared.components.AppDrawer
import com.elitonluiz1989.shoppist.shared.components.TopAppBar
import com.elitonluiz1989.shoppist.shared.models.AppScreens
import com.elitonluiz1989.shoppist.shared.navigation.Navigation
import com.elitonluiz1989.shoppist.ui.theme.ShoppistTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppistTheme {
                ShoppitsApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppistPreview() {
    ShoppistTheme {
        ShoppitsApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShoppitsApp() {
    Surface(modifier = Modifier.fillMaxSize()){
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()

        var selectedNavigationItem by remember { mutableStateOf(AppScreens.Home) }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    drawerState,
                    navController,
                    scope,
                    selectedNavigationItem,
                    onItemClick = { selectedNavigationItem = it }
                )
            }
        ) {
            DrawerScaffold(drawerState, navController, scope)
        }
    }
}

@Composable
private fun DrawerContent(
    drawerState: DrawerState,
    navController: NavHostController,
    scope: CoroutineScope,
    selectedNavigationItem: AppScreens,
    onItemClick: (AppScreens) -> Unit
) {
    AppDrawer(
        selectedNavigationItem = selectedNavigationItem,
        onItemClick = { item ->
            onItemClick(item.id)

            navController.navigate(item.route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }

            scope.launch {
                drawerState.close()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerScaffold(
    drawerState: DrawerState,
    navController: NavHostController,
    scope: CoroutineScope
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                onNavigationIconClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    ) { innerPadding -> Navigation(navController, innerPadding) }
}