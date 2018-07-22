package com.brid.azis.vipgame.test.Database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import android.widget.Toast
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
        createTableCard(db!!)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        createTableCard(db!!)
    }

    fun createTableCard(db: SQLiteDatabase) {
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
        Log.d("debug", "table user card is created")
    }

    fun addCard(card: DataCard){
        val db = this.writableDatabase

        try{
            db.use {
                db.insert(DataCard.TABLE_USERCARD,
                        DataCard.CARD_ID to card.id,
                        DataCard.CARD_TITLE to card.title,
                        DataCard.CARD_INSTRUCTION to card.instruction,
                        DataCard.CARD_TYPE to card.cardType,
                        DataCard.CARD_LEVEL to card.level,
                        DataCard.CARD_EXP to card.rewardExp,
                        DataCard.CARD_REWARD to card.rewardPoint,
                        DataCard.CARD_ISDONE to card.isDone,
                        DataCard.CARD_INPUTDATE to card.inputdate,
                        DataCard.CARD_FINISHDATE to card.finishDate,
                        DataCard.CARD_CHECKBY to card.checkBy)
            }
            Toast.makeText(this.context,"Data Berhasil Masuk", Toast.LENGTH_SHORT)
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this.context,"Data Tidak Masuk : $e", Toast.LENGTH_SHORT)
        } catch (e: SQLiteException) {
        }

    }


}