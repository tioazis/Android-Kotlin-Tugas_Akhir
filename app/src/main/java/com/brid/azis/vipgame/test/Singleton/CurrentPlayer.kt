package com.brid.azis.vipgame.test.Singleton

import android.app.Application

class CurrentPlayer : Application() {

    private var name = ""
    private var email = ""
    private var password = ""
    private var point = 0
    private var level = 1
    private var exp = 0
    private var missionCompleted = 0

    //    Getter Setter

    fun GetPlayerName():String{
        return name
    }

    fun SetPlayerName(value:String?){
        name = value!!
    }

    fun SetPlayerEmail():String{
        return email
    }

    fun SetPlayerEmail(value:String?){
        email = value!!
    }

    fun GetPlayerPoint():Int{
        return point
    }

    fun SetPlayerPoint(value:Int?){
        point = value!!
    }

    fun GetPlayerPassword():String{
        return password
    }

    fun SetPlayerPassword(value:String?){
        password = value!!
    }

    fun GetPlayerLevel():Int{
        return level
    }

    fun SetPlayerLevel(value:Int?){
        level = value!!
    }

    fun GetPlayerExp():Int{
        return exp
    }

    fun SetPlayerExp(value:Int?){
        exp = value!!
    }

    fun GetPlayerMissionCompleted():Int{
        return missionCompleted
    }

    fun SetPlayerMissionCompleted(value:Int?){
        missionCompleted = value!!
    }



    // Function

    fun addLevelBy(value:Int?){
        level += value!!
    }

    fun addExpBy(value:Int?){
        exp += value!!
    }

    fun addPointBy(value:Int?){
        point += value!!
    }

    fun addMissionCompletedBy(value:Int?){
        missionCompleted += value!!
    }

    // Reset

    fun ResetAll(){
        name = ""
        email = ""
        password = ""
        point = 0
        exp = 0
        level = 0
        missionCompleted  = 0
    }

    fun ResetStat(){
        point = 0
        exp = 0
        level = 0
        missionCompleted  = 0

    }

    // Get All



}