package com.elitonluiz1989.shoppist.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.domain.ItemRepository
import com.elitonluiz1989.shoppist.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {
    private val _defaultItem = Item(
        id = 0,
        name = "Item",
        quantity = 1,
        price = 0
    )

    private val _state = MutableStateFlow(
        ItemState(
            form = ItemFormData(
                id = _defaultItem.id,
                name = _defaultItem.name,
                quantity = _defaultItem.quantity.toString(),
                price = ""
            )
        )
    )
    val state: StateFlow<ItemState> = _state.asStateFlow()

    private val priceRegex = Regex("^\\d*([.,])?\\d{0,2}$")

    init {
        observeItems()
        observeQuantity()
    }

    fun onEvent(event: ItemEvent) {
        when (event) {
            is ItemEvent.UpdateId -> updateId(event.value)
            is ItemEvent.UpdateName -> updateName(event.value)
            is ItemEvent.UpdateQuantity -> updateQuantity(event.value)
            is ItemEvent.UpdatePrice -> updatePrice(event.value)
            is ItemEvent.UpdateForm -> updateForm(event.value)
            is ItemEvent.MarkFormAsTouched -> markFormTouched()
            is ItemEvent.Add -> add()
            is ItemEvent.Delete -> delete(event)
        }
    }

    private fun updateId(value: Long) {
        _state.update { it.copy(form = it.form.copy(id = value)) }
    }

    private fun updateName(value: String) {
        _state.update { it.copy(form = it.form.copy(name = value)) }
    }

    private fun updateQuantity(value: String) {
        if (value.any { it.isDigit() == false }) return

        _state.update { it.copy(form = it.form.copy(quantity = value)) }
    }

    private fun updatePrice(value: String) {
        if (value.matches(priceRegex) == false) return

        _state.update { it.copy( form = it.form.copy(price = value)) }
    }

    private fun updateForm(value: Item) {
        onEvent(ItemEvent.UpdateId(value.id))
        onEvent(ItemEvent.UpdateName(value.name))
        onEvent(ItemEvent.UpdateQuantity(value.quantity.toString()))

        val price =
            if (value.price > 0) value.price.toString()
            else ""

        onEvent(ItemEvent.UpdatePrice(price))
    }

    private fun markFormTouched() {
        _state.update { it.copy(form = it.form.copy(formTouched = true)) }
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
        }
    }

    private fun delete(event: ItemEvent.Delete) {
        viewModelScope.launch {
            repository.delete(event.item)
        }
    }

    private fun observeItems() {
        viewModelScope.launch {
            repository.getAll().collect { allItems ->
                _state.update { it.copy(items = allItems) }
            }
        }
    }

    private fun observeQuantity() {
        CoroutineScope(Dispatchers.Default).launch {
            state
                .map { it.form.quantityInvalid }
                .distinctUntilChanged()
                .collect { value ->
                    val error = if (value) R.string.items_screen_quantity_range_validation else null

                    _state.update { it.copy(error = error) }
                }
        }
    }
}
