package com.zemu.jpush.movie_kotlin.api


import com.zemu.jpush.movie_kotlin.bean.LoginBean
import com.zemu.jpush.movie_kotlin.bean.RegisterBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {
    //登录接口
    @FormUrlEncoded
    @POST("user/v1/login")
    fun login(@Field("phone") phone: String, @Field("pwd") pwd: String): Observable<LoginBean>

    //注册接口 @Field("ua")String ua,@Field("screenSize")String screenSize,@Field("os")String os,
    @FormUrlEncoded
    @POST("user/v1/registerUser")
    fun registerUser(@Field("nickName") nickName: String, @Field("phone") phone: String, @Field("pwd") pwd: String, @Field("pwd2") pwd2: String, @Field("sex") sex: Int, @Field("birthday") birthday: String, @Field("email") email: String): Observable<RegisterBean>
}
