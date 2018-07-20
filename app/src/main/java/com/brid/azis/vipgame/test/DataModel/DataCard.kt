package com.brid.azis.vipgame.test.DataModel

data class DataCard (val id : Int?,
                     val title:String?,
                     val instruction:String?,
                     val cardType:Int?,
                     val level:Int?,
                     val rewardExp:Int?,
                     val rewardPoint:Int?,
                     val inputdate:String?,
                     val isDone:Boolean?,
                     val checkBy:String?){
    companion object {
        const val TABLE_USERCARD:String = "TABLE_USERCARD"
        const val CARD_ID = "COL_CARDID"
        const val CARD_TITLE:String = "COL_TITLE"
        const val CARD_INSTRUCTION:String = "COL_INSTRUCTION"
        const val CARD_TYPE:String = "COL_TYPE"
        const val CARD_LEVEL:String = "COL_LEVEL"
        const val CARD_EXP:String = "COL_EXP"
        const val CARD_REWARD:String = "COL_POINT"
        const val CARD_INPUTDATE:String =  "COL_INPUTDATE"
        const val CARD_ISDONE:String = "COL_ISDONE"
        const val CARD_CHECKBY:String = "COL_CHECKBY"

    }
}