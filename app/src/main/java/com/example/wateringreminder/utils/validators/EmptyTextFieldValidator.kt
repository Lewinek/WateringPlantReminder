package com.example.wateringreminder.utils.validators

import com.example.wateringreminder.FieldValidator
import com.example.wateringreminder.R

class EmptyTextFieldValidator : FieldValidator<String>(
    {
        it?.isNotEmpty() ?: false
    },
    R.string.label_water_days
)