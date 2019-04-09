package com.zemu.jpush.movie_kotlin.loginmvp


import com.zemu.jpush.movie_kotlin.bean.LoginBean

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class LoginPresenter {

    private var loginView: LoginView? = null
    private var loginModel: LoginModel? = null
    fun attach(loginView: LoginView) {
        this.loginView = loginView
        loginModel = LoginModel()
    }

    fun login() {
        val pwd = loginView!!.inputPwd
        val phone = loginView!!.inputPhoneNumber
        loginModel!!.login(phone, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer { loginBean ->
                    if (loginBean != null) {
                        if (loginView != null) {
                            loginView!!.onLoginSuccess(loginBean as LoginBean)
                            return@Consumer
                        }
                    }
                    if (loginView != null) {
                        loginView!!.onFailed(Throwable("网络连接超时"))
                    }
                }, Consumer { throwable ->
                    if (loginView != null) {
                        loginView!!.onFailed(throwable)
                    }
                })
    }

    fun detach() {
        if (loginView != null) {
            loginView = null
        }
        if (loginModel != null) {
            loginModel = null
        }
    }

}
