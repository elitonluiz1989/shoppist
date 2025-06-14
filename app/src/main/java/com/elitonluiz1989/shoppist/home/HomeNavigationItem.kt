package com.elitonluiz1989.shoppist.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.shared.models.AppScreens
import com.elitonluiz1989.shoppist.shared.models.NavigationItem
import com.elitonluiz1989.shoppist.shared.models.Route

@Composable
fun homeNavigationItem() = NavigationItem(
    id = AppScreens.Home,
    title = stringResource(R.string.home_sreen_title),
    selectedIcon = Icons.Filled.Home,
    unSelectedIcon = Icons.Outlined.Home,
    route = Route("home")
)
