package com.zemu.jpush.movie_kotlin.loginmvp

import com.zemu.jpush.movie_kotlin.Constant
import com.zemu.jpush.movie_kotlin.api.ApiManager

import io.reactivex.Observable

class LoginModel {
    fun login(phone: String, pwd: String): Observable<*> {
        return Constant.addMain(ApiManager.loginApi.login(phone, pwd))
    }
}
