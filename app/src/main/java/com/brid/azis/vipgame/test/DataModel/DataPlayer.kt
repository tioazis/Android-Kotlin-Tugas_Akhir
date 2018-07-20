package com.brid.azis.vipgame.test.DataModel

data class DataPlayer (val id : Int?,
                         val name:String?,
                         val exp:String?,
                         val cardType:Int?,
                         val point:Int?,
                         val level:Int?,
                         val missionCompleted:Int?){
        companion object {
            const val TABLE_PLAYER:String = "TABLE_PLAYER"
            const val ID:String = "ID_"
            const val PLAYER_NAME = "COL_NAME"
            const val PLAYER_EXP:String = "COL_TITLE"
            const val PLAYER_LEVEL:String = "COL_LEVEL"
            const val PLAYER_POINT:String = "COL_POINT"
            const val PLAYER_MISSIONCOMPLETED:String = "COL_MISSION_COMPLETED"


        }
}
