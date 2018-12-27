package com.duzi.gudicafeteria_a.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
    val cafe_Id: String,
    val user_Id: String,
    val seq: String,
    val comment: String,
    val comment_score: String,
    val ins_user_dtm: String,
    val upd_user_dtm: String
) : Parcelable