package com.brid.azis.vipgame.test.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.brid.azis.vipgame.test.Model.Card

val DATABASE_NAME = "DbTugasAkhir"
val TABLE_NAME = "DbOnGoingMission"
val COL_ID = "id"
val COL_TITLE = "title"
val COL_INSTRUCTION = "instruction"
val COL_TYPE = "type"
val COL_EXP = "exp"
val COL_REWARD = "reward"
val COL_LEVEL = "level"
val DATABASE_VERSION = 1

class DbOnGoingMission (var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " VARCHAR(256)," +
                COL_INSTRUCTION + " TEXT," +
                COL_TYPE + " INTEGER," +
                COL_EXP + " INTEGER," +
                COL_REWARD + " INTEGER," +
                COL_LEVEL + " INTEGER)"

        db?.execSQL(createTable)
        Toast.makeText(context,"Database Created",Toast.LENGTH_SHORT).show()
    }

    fun insertData(card: Card) {
        val db = this.writableDatabase
        var cv = ContentValues()

        cv.put(COL_TITLE, card.title)
        cv.put(COL_INSTRUCTION, card.instruction)
        cv.put(COL_TYPE, card.type)
        cv.put(COL_EXP, card.exp)
        cv.put(COL_REWARD, card.reward)
        cv.put(COL_LEVEL, card.level)

        var result = db.insert(TABLE_NAME, null, cv)

        if (result == -1.toLong())
            Toast.makeText(context, "Data Gagal Dimasukkan", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Data Sukses Dimasukkan", Toast.LENGTH_SHORT).show()
    }

    fun readData(): MutableList<Card> {
        var list: MutableList<Card> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                var card = Card()
                card._id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                card.title = result.getString(result.getColumnIndex(COL_TITLE))
                card.instruction = result.getString(result.getColumnIndex(COL_INSTRUCTION))
                card.type = result.getString(result.getColumnIndex(COL_TYPE)).toInt()
                card.exp = result.getString(result.getColumnIndex(COL_EXP)).toInt()
                card.reward = result.getString(result.getColumnIndex(COL_REWARD)).toInt()
                card.level = result.getString(result.getColumnIndex(COL_LEVEL)).toInt()
                list.add(card)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }



    fun deleteData() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()

    }

    fun updateData(){
        val db = this.writableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)

        if(result.moveToFirst()){
            do{
                var cv = ContentValues()

                db.update(TABLE_NAME,cv, COL_ID + "=? AND " + COL_TITLE + "=? AND " +
                        COL_INSTRUCTION +"=? AND " + COL_TYPE + "=? AND " + COL_EXP +
                        "=? AND " + COL_REWARD +"=? AND " + COL_LEVEL + "=?",
                        arrayOf(result.getString(result.getColumnIndex(COL_ID)),
                                result.getString(result.getColumnIndex(COL_TITLE)),
                                result.getString(result.getColumnIndex(COL_INSTRUCTION)),
                                result.getString(result.getColumnIndex(COL_LEVEL)),
                                result.getString(result.getColumnIndex(COL_TYPE)),
                                result.getString(result.getColumnIndex(COL_REWARD)),
                                result.getString(result.getColumnIndex(COL_EXP))))

            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }



}