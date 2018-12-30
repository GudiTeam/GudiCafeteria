package com.duzi.gudicafeteria_a.ui.user

import com.duzi.gudicafeteria_a.data.User

/**
 * User 정보는 화면마다 거의 필요한 정보이므로 singleton 객체로 관리
 */
object UserInstance {
    private var user_Id: String? = null
    private var user_Nm: String? = null
    private var user_Img: String? = null
    private var remark: String? = null

    fun setUserInfo(userId: String, userName: String, userImg: String, remark: String? = null) {
        user_Id = userId
        user_Nm = userName
        user_Img = userImg
        this.remark = remark
    }

    fun setUserInfo(user: User) {
        this.user_Id = user.user_Id
        this.user_Nm = user.user_Nm
        this.user_Img = user.user_Img
        this.remark = user.remark
    }

    fun setUserNull() {
        user_Id = null
        user_Nm = null
        user_Img = null
        remark = null
    }

    fun getUserId(): String? = user_Id
    fun getUserName(): String? = user_Nm
    fun getUserImg(): String? = user_Img
}