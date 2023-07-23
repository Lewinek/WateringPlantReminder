package com.example.wateringreminder.plantcreator

import com.example.wateringreminder.FieldValidator
import com.example.wateringreminder.R

class PlantNameValidator() :
    FieldValidator<String>(
        {
            it.isNullOrBlank()
        },
        R.string.label_water_days
    )