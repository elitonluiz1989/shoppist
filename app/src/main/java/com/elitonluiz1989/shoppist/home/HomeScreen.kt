package com.elitonluiz1989.shoppist.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.elitonluiz1989.shoppist.R

@Composable
fun HomeScreen() {
    Text( text = stringResource(R.string.home_sreen_title) )
}