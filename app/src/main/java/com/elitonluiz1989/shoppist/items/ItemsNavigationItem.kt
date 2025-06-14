package com.elitonluiz1989.shoppist.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.shared.models.AppScreens
import com.elitonluiz1989.shoppist.shared.models.NavigationItem
import com.elitonluiz1989.shoppist.shared.models.Route

val itemsRoute = Route("items")

@Composable
fun itemsNavigationItem() = NavigationItem(
    id = AppScreens.Items,
    title = stringResource(R.string.items_sreen_title),
    selectedIcon = Icons.Filled.ShoppingCart,
    unSelectedIcon = Icons.Outlined.ShoppingCart,
    route = itemsRoute
)