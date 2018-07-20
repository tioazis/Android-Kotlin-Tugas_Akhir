package com.brid.azis.vipgame.test.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.brid.azis.vipgame.test.DataModel.DataPlayer
import org.jetbrains.anko.db.*

val Context.playerDB: DBPlayerProgressHelper
    get() = DBPlayerProgressHelper.getInstance(applicationContext)


class DBPlayerProgressHelper (var context: Context): ManagedSQLiteOpenHelper(context, "FinalProject.db", null, 1) {
    companion object {

        private var instances:DBPlayerProgressHelper? = null

        @Synchronized
        fun getInstance(context: Context):DBPlayerProgressHelper{
            if (instances == null){
                instances = DBPlayerProgressHelper(context.applicationContext)
            }
            return instances as DBPlayerProgressHelper
        }
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(DataPlayer.TABLE_PLAYER,true)
    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.createTable( DataPlayer.TABLE_PLAYER,true,
                DataPlayer.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                DataPlayer.PLAYER_NAME to INTEGER + UNIQUE,
                DataPlayer.PLAYER_EXP to INTEGER,
                DataPlayer.PLAYER_LEVEL to INTEGER,
                DataPlayer.PLAYER_MISSIONCOMPLETED to INTEGER)


    }
}