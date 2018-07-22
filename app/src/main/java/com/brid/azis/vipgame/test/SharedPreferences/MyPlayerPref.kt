package com.brid.azis.vipgame.test.SharedPreferences

import android.content.Context
import android.os.Debug

val PREFERENCE_NAME = "MyPlayerPref"
val PREFERENCE_PLAYER_USERNAME = "Username"
val PREFERENCE_PLAYER_IS_LOGGED_IN = "IsLoggedIn"

class MyPlayerPref(context:Context){

val preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

    fun getPlayerUsername(KEY:String):String{
        print("Get Player Username  ${ preference.getString(KEY, "") }")
        return preference.getString(KEY, "")

    }

    fun setPlayerUsername(value:String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_PLAYER_USERNAME,value)
        editor.apply()
    }

    fun getPlayerIsLoggedIn(KEY:String):Boolean{
//        print("Get Player Is Logged In  ${ preference.getString(KEY, "") }")
        return preference.getBoolean(KEY, false)
    }

    fun setPlayerIsLoggedIn(value:Boolean){
        val editor = preference.edit()
        editor.putBoolean(PREFERENCE_PLAYER_IS_LOGGED_IN,value)
        editor.apply()
    }

    fun resetPlayerPref(){
        val editor = preference.edit()
        editor.putBoolean(PREFERENCE_PLAYER_IS_LOGGED_IN,false)
        editor.putString(PREFERENCE_PLAYER_USERNAME,"")
        editor.apply()
    }




}