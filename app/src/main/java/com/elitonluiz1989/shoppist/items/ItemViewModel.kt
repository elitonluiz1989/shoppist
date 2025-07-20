package com.elitonluiz1989.shoppist.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.domain.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {
    private val _defaultItem = Item(
        id = 0,
        name = "Item",
        quantity = 1,
        price = BigDecimal.ZERO
    )

    private val _state = MutableStateFlow(ItemState(
        form = ItemFormData(
            id = _defaultItem.id,
            name = _defaultItem.name,
            quantity = _defaultItem.quantity.toString(),
            price = ""
        )
    ))
    val state: StateFlow<ItemState> = _state

    init {
        onEvent(ItemEvent.Load)
        observeQuantity()
    }

    fun onEvent(event: ItemEvent) {
        when (event) {
            is ItemEvent.UpdateId -> {
                _state.value = _state.value.copy(
                    form = _state.value.form.copy(id = event.value)
                )
            }

            is ItemEvent.UpdateName -> {
                _state.value = _state.value.copy(
                    form = _state.value.form.copy(name = event.value)
                )
            }

            is ItemEvent.UpdateQuantity -> {
                updateQuantity(event)
            }

            is ItemEvent.UpdatePrice -> {
                updatePrice(event)
            }

            is ItemEvent.UpdateForm -> {
                updateForm(event)
            }

            is ItemEvent.MarkFormAsTouched -> {
                _state.value = _state.value.copy(
                    form = _state.value.form.copy(formTouched = true)
                )
            }

            is ItemEvent.Add -> {
                add()
            }

            is ItemEvent.Delete -> {
                delete(event)
            }

            is ItemEvent.Load -> {
                loadItems()
            }
        }
    }

    private fun updateQuantity(event: ItemEvent.UpdateQuantity) {
        if (event.value.any { it.isDigit() == false }) {
            return
        }

        _state.value = _state.value.copy(
            form = _state.value.form.copy(quantity = event.value)
        )
    }

    private fun updatePrice(event: ItemEvent.UpdatePrice) {
        if (event.value.matches(Regex("^\\d*([.,])?\\d{0,2}$")) == false) return

        _state.value = _state.value.copy(
            form = _state.value.form.copy(price = event.value)
        )
    }

    private fun updateForm(event: ItemEvent.UpdateForm) {
        onEvent(ItemEvent.UpdateId(event.value.id))
        onEvent(ItemEvent.UpdateName(event.value.name))
        onEvent(ItemEvent.UpdateQuantity(event.value.quantity.toString()))

        val price =
            if (event.value.price > BigDecimal.ZERO) event.value.price.toString()
            else ""

        onEvent(ItemEvent.UpdatePrice(price))
    }

    private fun add() {
        viewModelScope.launch {
            if (!state.value.form.valid) return@launch

            val item = Item(
                id = state.value.form.id,
                name = state.value.form.name,
                quantity = state.value.form.quantityConverted,
                price = state.value.form.priceConverted
            )

            repository.upsert(item)

            onEvent(ItemEvent.UpdateForm(_defaultItem))
            loadItems()
        }
    }

    private fun delete(event: ItemEvent.Delete) {
        viewModelScope.launch {
            repository.delete(event.item)

            loadItems()
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

    private fun observeQuantity() {
        CoroutineScope(Dispatchers.Default).launch {
            state
                .map { it.form.quantityInvalid }
                .distinctUntilChanged()
                .collect {
                    _state.value = _state.value.copy(error = if (it) "The quantity must be between 1 and 99" else null)
                }
        }
    }
}
