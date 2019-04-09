package com.zemu.jpush.movie_kotlin.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings

class NetworkUtils private constructor() {

    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {
        /** 网络不可用  */
        val NO_NET_WORK = 0
        /** 是wifi连接  */
        val WIFI = 1
        /** 不是wifi连接  */
        val NO_WIFI = 2

        /**
         * 判断是否打开网络
         * @param context
         * @return
         */
        fun isNetWorkAvailable(context: Context): Boolean {
            var isAvailable = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.isAvailable) {
                isAvailable = true
            }
            return isAvailable
        }

        /**
         * 获取网络类型
         * @param context
         * @return
         */
        fun getNetWorkType(context: Context): Int {
            if (!isNetWorkAvailable(context)) {
                return NetworkUtils.NO_NET_WORK
            }
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting)
                NetworkUtils.WIFI
            else
                NetworkUtils.NO_WIFI
        }



        /**
         * 判断MOBILE网络是否可用
         * @param context
         * @return
         * @throws Exception
         */
        fun isMobileDataEnable(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var isMobileDataEnable = false
            isMobileDataEnable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting
            return isMobileDataEnable
        }

        /**
         * 判断wifi 是否可用
         * @param context
         * @return
         * @throws Exception
         */
        fun isWifiDataEnable(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var isWifiDataEnable = false
            isWifiDataEnable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting
            return isWifiDataEnable
        }

        /**
         * 跳转到网络设置页面
         * @param activity
         */
        fun GoSetting(activity: Activity) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            activity.startActivity(intent)
        }

        /**
         * 打开网络设置界面
         */
        fun openSetting(activity: Activity) {
            val intent = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
            activity.startActivity(intent)

        }
    }


}
