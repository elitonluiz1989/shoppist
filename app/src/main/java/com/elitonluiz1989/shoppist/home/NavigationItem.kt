package com.elitonluiz1989.shoppist.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import com.elitonluiz1989.shoppist.shared.models.AppScreens
import com.elitonluiz1989.shoppist.shared.models.NavigationItem

fun createHomeNavigationItem(title: String) = NavigationItem(
    id = AppScreens.Home,
    title = title,
    selectedIcon = Icons.Filled.Home,
    unSelectedIcon = Icons.Outlined.Home,
    route = HomeRoute
)
