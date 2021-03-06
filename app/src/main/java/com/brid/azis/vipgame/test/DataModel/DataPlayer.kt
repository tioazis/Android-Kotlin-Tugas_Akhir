package com.brid.azis.vipgame.test.DataModel

data class DataPlayer (
                       val username:String,
                       val password:String,
                         val name:String?,
                         val exp:Int?,
                         val point:Int?,
                         val level:Int?,
                         val missionCompleted:Int?){
        companion object {
            const val TABLE_PLAYER:String = "TABLE_PLAYER"
            const val PLAYER_USERNAME = "COL_USERNAME"
            const val PLAYER_NAME = "COL_NAME"
            const val PLAYER_PASSWORD = "COL_PASS"
            const val PLAYER_EXP:String = "COL_EXP"
            const val PLAYER_LEVEL:String = "COL_LEVEL"
            const val PLAYER_POINT:String = "COL_POINT"
            const val PLAYER_MISSION_COMPLETED:String = "COL_MISSION_COMPLETED"



        }
}
