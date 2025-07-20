package com.elitonluiz1989.shoppist.items

import java.math.BigDecimal

data class ItemFormData (
    val id: Long = 0,
    val name: String = "",
    val quantity: String = "",
    val price: String = "",
    val formTouched: Boolean = false
) {
    val idValid: Boolean
        get() = id > 0

    val quantityConverted: Short
        get() = quantity.toShortOrNull() ?: 0

    val priceConverted: BigDecimal
        get() = price.toBigDecimalOrNull() ?: BigDecimal.ZERO

    val nameInvalid: Boolean
        get() = formTouched && !validateName()

    val quantityInvalid: Boolean
        get() = formTouched && !validateQuantity()

    val priceInvalid: Boolean
        get() = formTouched && !validatePrice()

    val valid: Boolean
        get() = validateName() && validateQuantity() && validatePrice()

    private fun validateName(): Boolean {
        return name.isNotBlank()
    }

    private fun validateQuantity(): Boolean {
        return quantityConverted > 0 && quantityConverted <= 100
    }

    private fun validatePrice(): Boolean {
        return priceConverted > BigDecimal.ZERO
    }
}