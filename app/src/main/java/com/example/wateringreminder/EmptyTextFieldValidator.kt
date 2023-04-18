package com.example.wateringreminder

class EmptyTextFieldValidator : FieldValidator<String>(
    {
        it?.isNotEmpty() ?: false
    },
    R.string.water_days_label
)