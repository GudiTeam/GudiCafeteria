package com.duzi.gudicafeteria_a.ui.cafe

data class WeeklyMenusQuery(
        val cafeId: String,
        val start: Long,
        val end: Long
)