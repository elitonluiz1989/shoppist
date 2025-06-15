package com.elitonluiz1989.shoppist.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.home.createHomeNavigationItem
import com.elitonluiz1989.shoppist.items.createItemsNavigationItem
import com.elitonluiz1989.shoppist.shared.interfaces.Route
import com.elitonluiz1989.shoppist.shared.models.AppScreens
import com.elitonluiz1989.shoppist.shared.models.NavigationItem

@Composable
fun AppDrawer(selectedNavigationItem: AppScreens, onItemClick: (NavigationItem<Route>) -> Unit) {
    val items = listOf(
        createHomeNavigationItem(stringResource(R.string.home_sreen_title)),
        createItemsNavigationItem(stringResource(R.string.items_sreen_title))
    );

    ModalDrawerSheet {
        DrawerHeader()

        DrawerBody(
            items = items,
            selectedNavigationItem = selectedNavigationItem,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DrawerBody(
    items: List<NavigationItem<Route>>,
    selectedNavigationItem: AppScreens,
    onItemClick: (NavigationItem<Route>) -> Unit
) {
    items.forEach { item ->
        NavigationDrawerItem(
            label = { Text(item.title) },
            icon = { ItemIcon(item, selectedNavigationItem) },
            selected = item.id == selectedNavigationItem,
            onClick = { onItemClick(item) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Composable
private fun ItemIcon(item: NavigationItem<Route>, selectedNavigationItem: AppScreens) {
    val icon =
        if (item.id == selectedNavigationItem) { item.selectedIcon }
        else { item.unSelectedIcon }

    Icon(
        imageVector = icon,
        contentDescription = item.title
    )
}