package com.elitonluiz1989.shoppist.shared.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elitonluiz1989.shoppist.home.HomeRoute
import com.elitonluiz1989.shoppist.home.HomeScreen
import com.elitonluiz1989.shoppist.items.ItemsRoute
import com.elitonluiz1989.shoppist.items.ItemsSreem

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable<HomeRoute> { HomeScreen() }
        composable<ItemsRoute>{ ItemsSreem() }
    }
}