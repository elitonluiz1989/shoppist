package com.elitonluiz1989.shoppist.shared.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elitonluiz1989.shoppist.home.HomeScreen
import com.elitonluiz1989.shoppist.home.homeNavigationItem
import com.elitonluiz1989.shoppist.items.ItemsSreem
import com.elitonluiz1989.shoppist.items.itemsRoute

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    val homeRoute = homeNavigationItem().route.value

    NavHost(
        navController = navController,
        startDestination = homeRoute,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = homeRoute) { HomeScreen() }
        composable(route = itemsRoute.value) { ItemsSreem() }
    }
}