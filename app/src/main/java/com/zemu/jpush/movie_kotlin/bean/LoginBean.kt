package com.zemu.jpush.movie_kotlin.bean

class LoginBean {

    var result: ResultBean? = null
    var message: String? = null
    var status: String? = null

    class ResultBean {


        var sessionId: String? = null
        var userId: Int = 0
        var userInfo: UserInfoBean? = null

        class UserInfoBean {

            var birthday: Long = 0
            var id: Int = 0
            var lastLoginTime: Long = 0
            var nickName: String? = null
            var phone: String? = null
            var sex: Int = 0
            var headPic: String? = null
        }
    }
}
