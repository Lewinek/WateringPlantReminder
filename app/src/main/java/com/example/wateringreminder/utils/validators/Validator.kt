package com.example.wateringreminder

import androidx.annotation.StringRes

abstract class Validator<T> {
    var isValid = true
        protected set

    @get:StringRes
    abstract var error: Int

    abstract fun validate(value: T?): Boolean
}

open class FieldValidator<T>(
    private val validator: (T?) -> Boolean,
    override var error: Int
) : Validator<T>() {
    override fun validate(value: T?): Boolean {
        isValid = validator.invoke(value)
        return isValid
    }
}
