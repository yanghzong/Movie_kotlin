package com.zemu.jpush.movie_kotlin.api


import com.zemu.jpush.movie_kotlin.utils.RetrofitManager

object ApiManager {

    //登录页面接口
    val loginApi: LoginApi
        get() = RetrofitManager.instance.create(LoginApi::class.java)

}
