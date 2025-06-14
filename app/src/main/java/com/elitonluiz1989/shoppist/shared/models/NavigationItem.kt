package com.elitonluiz1989.shoppist.shared.models

import androidx.compose.ui.graphics.vector.ImageVector

enum class AppScreens {
    Home,
    Items
}

data class NavigationItem(
    val id: AppScreens,
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: Route
)
