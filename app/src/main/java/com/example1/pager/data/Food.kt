package com.example1.pager.data

import androidx.annotation.DrawableRes

data class Food(
    @DrawableRes val foodImage : Int,
    val foodName : String,
    val foodDescription : String
)
