package com.brid.azis.vipgame.test.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.brid.azis.vipgame.test.DataModel.DataPlayer
import org.jetbrains.anko.db.*

val Context.playerDB: DBPlayerHelper
    get() = DBPlayerHelper.getInstance(applicationContext)


class DBPlayerHelper (var context: Context): ManagedSQLiteOpenHelper(context, "FinalProject.db", null, 1) {
    companion object {

        private var instances:DBPlayerHelper? = null

        @Synchronized
        fun getInstance(context: Context):DBPlayerHelper{
            if (instances == null){
                instances = DBPlayerHelper(context.applicationContext)
            }
            return instances as DBPlayerHelper
        }
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(DataPlayer.TABLE_PLAYER,true)
    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.createTable( DataPlayer.TABLE_PLAYER,true,
                DataPlayer.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                DataPlayer.PLAYER_USERNAME to TEXT + UNIQUE,
                DataPlayer.PLAYER_NAME to TEXT,
                DataPlayer.PLAYER_PASSWORD to TEXT,
                DataPlayer.PLAYER_POINT to INTEGER,
                DataPlayer.PLAYER_EXP to INTEGER,
                DataPlayer.PLAYER_LEVEL to INTEGER,
                DataPlayer.PLAYER_MISSION_COMPLETED to INTEGER)


    }
}