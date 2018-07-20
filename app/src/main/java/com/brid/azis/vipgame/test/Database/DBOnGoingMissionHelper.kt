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
                DataCard.CARD_ISDONE to INTEGER,
                DataCard.CARD_CHECKBY to TEXT)

    }



//    fun addCardData(card: Card, context: Context){
//        try {
//            context.missionDB.use{
//                insert(TABLE_NAME,
//                        COL_ID to card._id,
//                        COL_TITLE to card.title,
//                        COL_INSTRUCTION to card.instruction,
//                        COL_TYPE to card.type,
//                        COL_REWARD to card.reward,
//                        COL_LEVEL to card.level,
//                        COL_EXP to card.exp,
//                        COL_ISDONE to null,
//                        COL_CHECKBY to "")
//                Toast.makeText(context, "Data Berhasil Dimasukkan", Toast.LENGTH_SHORT).show()
//            }
//
//        }catch (e: SQLiteConstraintException){
//            Toast.makeText(context, "Data Gagal Dimasukkan", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//
//    fun readData(context: Context): MutableList<Card> {
//        val list: MutableList<Card> = mutableListOf()
//
//        context.missionDB.use {
//            val result = select(TABLE_NAME)
//            val cards = result.parseList(classParser<Card>())
//            list.addAll(cards)
//        }
//
//        return list
//    }
//
//
//
//
//
//    fun deleteData() {
//        val db = this.writableDatabase
//        db.delete(TABLE_NAME, null, null)
//        db.close()
//
//    }
//
//    fun updateData(){
//        val db = this.writableDatabase
//        val query = "Select * from " + TABLE_NAME
//        val result = db.rawQuery(query, null)
//
//        if(result.moveToFirst()){
//            do{
//                var cv = ContentValues()
//
//                db.update(TABLE_NAME,cv, COL_ID + "=? AND " + COL_TITLE + "=? AND " +
//                        COL_INSTRUCTION +"=? AND " + COL_TYPE + "=? AND " + COL_EXP +
//                        "=? AND " + COL_REWARD +"=? AND " + COL_LEVEL + "=?",
//                        arrayOf(result.getString(result.getColumnIndex(COL_ID)),
//                                result.getString(result.getColumnIndex(COL_TITLE)),
//                                result.getString(result.getColumnIndex(COL_INSTRUCTION)),
//                                result.getString(result.getColumnIndex(COL_LEVEL)),
//                                result.getString(result.getColumnIndex(COL_TYPE)),
//                                result.getString(result.getColumnIndex(COL_REWARD)),
//                                result.getString(result.getColumnIndex(COL_EXP))))
//
//            }while (result.moveToNext())
//        }
//
//        result.close()
//        db.close()
//    }

}