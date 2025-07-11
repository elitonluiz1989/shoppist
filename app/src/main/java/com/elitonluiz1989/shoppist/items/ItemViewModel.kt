package com.elitonluiz1989.shoppist.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elitonluiz1989.domain.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ItemState())
    val state: StateFlow<ItemState> = _state

    init {
        onEvent(ItemEvent.Load)
    }

    fun onEvent(event: ItemEvent) {
        when (event) {
            is ItemEvent.Load -> {
                loadItems()
            }

            is ItemEvent.UpdateName -> {
                _state.value = _state.value.copy(name = event.value)
            }

            is ItemEvent.UpdateQuantity -> {
                if (event.value.any { it.isDigit() == false }) {
                    return
                }

                _state.value = _state.value.copy(quantity = event.value)
            }

            is ItemEvent.UpdatePrice -> {
                if (event.value.matches(Regex("^\\d*([.,])?\\d{0,2}$")) == false) {
                    return
                }

                _state.value = _state.value.copy(price = event.value)
            }

            is ItemEvent.Add -> {
                viewModelScope.launch {
                    repository.upsert(event.item)

                    _state.value = _state.value.copy(id = 0)
                    _state.value = _state.value.copy(name = "")
                    _state.value = _state.value.copy(quantity = "")
                    _state.value = _state.value.copy(price = "")

                    loadItems()
                }
            }

            is ItemEvent.Delete -> {
                viewModelScope.launch {
                    repository.delete(event.item)

                    loadItems()
                }
            }

            is ItemEvent.UpdateForm -> {
                _state.value = _state.value.copy(id = event.value.id)
                _state.value = _state.value.copy(name = event.value.name)
                _state.value = _state.value.copy(quantity = event.value.quantity.toString())
                _state.value = _state.value.copy(price = event.value.price.toString())
            }
        }
    }

    private fun loadItems() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)

                repository.getAll().collect { items ->
                    _state.value = _state.value.copy(items = items, isLoading = false)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}
