package com.brid.azis.vipgame.test.Model


class Card {

    var _id:Int = 0  //id kartu
    var title:String =  "" //Judul kartu
    var instruction:String = "" //Deskripsi kartu
    var type:Int = 0 //tipe kartu
    var exp:Int = 0 // exp yang didapatkan ketika menyelesaikan misi
    var reward:Int = 0
    var level:Int = 0

    constructor(title:String, instruction:String, type:Int,
                exp:Int,reward:Int,level:Int){

        this.title = title
        this.instruction = instruction
        this.type = type
        this.exp = exp
        this.level = level
        this.reward = reward
    }

    constructor(){}
}