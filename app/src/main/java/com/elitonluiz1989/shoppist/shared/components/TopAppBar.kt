package com.elitonluiz1989.shoppist.shared.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.elitonluiz1989.shoppist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(onNavigationIconClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle Menu"
                )
            }
        },
    )
}