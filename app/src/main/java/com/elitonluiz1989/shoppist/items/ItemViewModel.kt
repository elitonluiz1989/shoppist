package com.elitonluiz1989.shoppist.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elitonluiz1989.domain.items.Item
import com.elitonluiz1989.domain.items.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel() {
    val items = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun add(item: Item) = viewModelScope.launch {
        repository.upsert(item)
    }

    fun delete(item: Item) = viewModelScope.launch {
        repository.delete(item)
    }
}