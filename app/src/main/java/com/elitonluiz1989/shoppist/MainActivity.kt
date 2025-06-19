package com.elitonluiz1989.shoppist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.elitonluiz1989.shoppist.items.ItemViewModel
import com.elitonluiz1989.shoppist.items.ItemsScreen
import com.elitonluiz1989.shoppist.components.TopAppBar
import com.elitonluiz1989.shoppist.ui.theme.ShoppistTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { TopAppBar() }
        ) { innerPadding ->
            val viewModel: ItemViewModel = hiltViewModel()

            ItemsScreen(viewModel, innerPadding)
        }
    }
}