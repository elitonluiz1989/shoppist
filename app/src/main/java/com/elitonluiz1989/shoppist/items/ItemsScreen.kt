package com.elitonluiz1989.shoppist.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.domain.items.Item
import com.elitonluiz1989.shoppist.R

@Composable
fun ItemsScreen(viewModel: ItemViewModel, paddingValues: PaddingValues) {
    val items by viewModel.items.collectAsState()

    var itemName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        OutlinedTextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text(stringResource(R.string.item_screen_add_item_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (itemName.isNotBlank()) {
                viewModel.add(Item(id = 0, name = itemName))
                itemName = ""
            }
        }) {
            Text(stringResource(R.string.add))
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
                    Button(onClick = { viewModel.delete(item) }) {
                        Text(stringResource(R.string.delete))
                    }
                }
            }
        }
    }
}