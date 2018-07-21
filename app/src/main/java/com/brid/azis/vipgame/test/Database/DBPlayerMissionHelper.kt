package com.brid.azis.vipgame.test.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.brid.azis.vipgame.test.DataModel.DataCard
import org.jetbrains.anko.db.*


val Context.missionDB: DBOnGoingMissionHelper
    get() = DBOnGoingMissionHelper.getInstance(applicationContext)


class DBOnGoingMissionHelper (var context: Context): ManagedSQLiteOpenHelper(context, "FinalProject.db", null, 1) {
    companion object {

        private var instances:DBOnGoingMissionHelper? = null

        @Synchronized
        fun getInstance(context:Context):DBOnGoingMissionHelper{
            if (instances == null){
                instances = DBOnGoingMissionHelper(context.applicationContext)
            }
            return instances as DBOnGoingMissionHelper
        }
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(DataCard.TABLE_USERCARD,true)
    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.createTable( DataCard.TABLE_USERCARD,true,
                DataCard.CARD_ID to INTEGER + UNIQUE,
                DataCard.CARD_TITLE to TEXT,
                DataCard.CARD_INSTRUCTION to TEXT,
                DataCard.CARD_TYPE to INTEGER,
                DataCard.CARD_LEVEL to INTEGER,
                DataCard.CARD_REWARD to INTEGER,
                DataCard.CARD_EXP to INTEGER,
                DataCard.CARD_INPUTDATE to TEXT,
                DataCard.CARD_FINISHDATE to TEXT,
                DataCard.CARD_ISDONE to INTEGER,
                DataCard.CARD_CHECKBY to TEXT)

    }


}