package com.zemu.jpush.movie_kotlin.loginmvp


import com.zemu.jpush.movie_kotlin.bean.LoginBean

interface LoginView {
    val inputPhoneNumber: String
    val inputPwd: String
    fun onLoginSuccess(loginBean: LoginBean)
    fun onFailed(t: Throwable)

}
