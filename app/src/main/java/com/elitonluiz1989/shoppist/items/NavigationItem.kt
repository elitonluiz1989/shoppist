package com.elitonluiz1989.shoppist.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import com.elitonluiz1989.shoppist.shared.models.AppScreens
import com.elitonluiz1989.shoppist.shared.models.NavigationItem

fun createItemsNavigationItem(title: String) = NavigationItem(
    id = AppScreens.Items,
    title = title,
    selectedIcon = Icons.Filled.ShoppingCart,
    unSelectedIcon = Icons.Outlined.ShoppingCart,
    route = ItemsRoute
)