package com.brid.azis.vipgame.test.Database

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.provider.ContactsContract
import com.brid.azis.vipgame.test.DataModel.DataPlayer
import org.jetbrains.anko.db.*

val Context.playerDB: DBOnGoingMissionHelper
    get() = DBOnGoingMissionHelper.getInstance(applicationContext)

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
        createTableUser(db!!)
    }

    fun createTableUser(db: SQLiteDatabase?) {
        db?.createTable( DataPlayer.TABLE_PLAYER,true,
                DataPlayer.PLAYER_USERNAME to TEXT + UNIQUE,
                DataPlayer.PLAYER_PASSWORD to TEXT,
                DataPlayer.PLAYER_NAME to TEXT,
                DataPlayer.PLAYER_EXP to INTEGER,
                DataPlayer.PLAYER_POINT to INTEGER,
                DataPlayer.PLAYER_LEVEL to INTEGER,
                DataPlayer.PLAYER_MISSION_COMPLETED to INTEGER)
    }

    fun addUser(player:DataPlayer){
        // ???cek database table_usercard dulu???

        val db = this.writableDatabase
        try {
            db.use {
                db.insert(
                        DataPlayer.TABLE_PLAYER,
                        DataPlayer.PLAYER_USERNAME to player.username,
                        DataPlayer.PLAYER_PASSWORD to player.password,
                        DataPlayer.PLAYER_NAME to player.name,
                        DataPlayer.PLAYER_EXP to player.exp,
                        DataPlayer.PLAYER_POINT to player.point,
                        DataPlayer.PLAYER_LEVEL to player.level,
                        DataPlayer.PLAYER_MISSION_COMPLETED to player.missionCompleted)
            }
        } catch (e:SQLiteConstraintException){

        } catch (e:SQLiteException) {
            createTableUser(db)
        }

    }

    fun checkUser(email: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(DataPlayer.PLAYER_USERNAME)
        val db = this.readableDatabase

        // selection criteria
        val selection = "${DataPlayer.PLAYER_USERNAME} = ?"

        // selection argument
        val selectionArgs = arrayOf(email)

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(DataPlayer.TABLE_PLAYER, //Table to query
                columns,        //columns to return
                selection,      //columns for the WHERE clause
                selectionArgs,  //The values for the WHERE clause
                null,  //group the rows
                null,   //filter by row groups
                null)  //The sort order


        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false

    }

    fun checkUser(email: String, password: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(DataPlayer.PLAYER_USERNAME)

        val db = this.readableDatabase

        // selection criteria
        val selection = "${DataPlayer.PLAYER_USERNAME} = ? AND ${DataPlayer.PLAYER_PASSWORD} = ?"

        // selection arguments
        val selectionArgs = arrayOf(email, password)

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(DataPlayer.TABLE_PLAYER, //Table to query
                columns, //columns to return
                selection, //columns for the WHERE clause
                selectionArgs, //The values for the WHERE clause
                null,  //group the rows
                null, //filter by row groups
                null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false

    }




}