package com.duzi.gudicafeteria_a.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu(
        val cafe_Id: String,
        val menu_Date: String,
        val menu_Div: String,
        val menu_img_Nm: String,
        val rice: String,
        val soup: String,
        val side_Dish1: String,
        val side_Dish2: String,
        val side_Dish3: String,
        val side_Dish4: String,
        val side_Dish5: String,
        val side_Dish6: String,
        val side_Dish7: String,
        val side_Dish8: String,
        val dessert1: String,
        val dessert2: String
) : Parcelable