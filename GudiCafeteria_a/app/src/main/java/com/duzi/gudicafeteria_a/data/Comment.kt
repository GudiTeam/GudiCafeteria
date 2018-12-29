package com.duzi.gudicafeteria_a.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
    val cafe_Id: String,
    val user_Id: String,
    val comment: String,
    val comment_score: String,
    val seq: String? = null,
    val ins_user_dtm: String? = null,
    val upd_user_dtm: String? = null
) : Parcelable