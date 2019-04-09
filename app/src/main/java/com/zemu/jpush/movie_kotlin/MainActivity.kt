package com.zemu.jpush.movie_kotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.zemu.jpush.movie_kotlin.bean.LoginBean
import com.zemu.jpush.movie_kotlin.loginmvp.LoginPresenter
import com.zemu.jpush.movie_kotlin.loginmvp.LoginView
import com.zemu.jpush.movie_kotlin.utils.EncryptUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),LoginView{
    override fun onFailed(t: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var presenter: LoginPresenter? = null
    private val loginphone: String? = null
    private val loginpwd: String? = null
    private var sp: SharedPreferences? = null
    private var showPwd = false

    override val inputPhoneNumber: String
       get() = ed_user_phone!!.text.toString().trim { it <= ' ' }

    //To change initializer of created properties use File | Settings | File Templates.

    override val inputPwd: String
        get() {
            val pwd = ed_user_pwd!!.text.toString()
            return EncryptUtil.encrypt(pwd)
        }//To change initializer of created properties use File | Settings | File Templates.


    override fun onLoginSuccess(loginBean: LoginBean) {
        //获取sp的编辑器
        val edit = sp!!.edit()
        //吐司登录接口返回的信息
        if (loginBean.message.equals("登陆成功")) {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "" + loginBean.message, Toast.LENGTH_SHORT).show()
        }


        fun onFailed(t: Throwable) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun initPresenter() {
            presenter = LoginPresenter()
            presenter!!.attach(this)
        }

        fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            initPresenter()
        }


    }

}
