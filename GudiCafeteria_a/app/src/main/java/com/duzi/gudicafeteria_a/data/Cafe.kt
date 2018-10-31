package com.duzi.gudicafeteria_a.data

data class Cafe(
        val cafe_Id: String,
        val cafe_Nm: String,
        val cafe_img_Nm: String,
        val cafe_img_Dir: String,
        val price: Int,
        val lunch_YN: String,
        val dinner_YN: String,
        val oper_Time: String,
        val build_Addr: String,
        val build_Nm: String,
        val build_Tel: String,
        val build_Home: String,
        val build_Key: String,
        val build_X: Double,
        val build_Y: Double,
        val build_Score: Double,
        val use_YN: String,
        val menu_L: Menu,
        val menu_D: Menu
)


