package com.brid.azis.vipgame.test.Activity

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brid.azis.vipgame.R.layout.activity_insert_db_test
import com.brid.azis.vipgame.test.DataModel.DataCard
import com.brid.azis.vipgame.test.Database.DBOnGoingMissionHelper
import com.brid.azis.vipgame.test.Database.missionDB
import kotlinx.android.synthetic.main.activity_insert_db_test.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat
import java.util.*

class InsertDbTest : AppCompatActivity() {

    val context = this
    private var dataCards :MutableList<DataCard> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_insert_db_test)


        btn_insert.setOnClickListener {
            if (et_title.text.toString().length > 0 &&
                    et_instruction.text.toString().length > 0 &&
                    et_type.text.toString().length > 0 &&
                    et_rewards.text.toString().length > 0  &&
                    et_exp.text.toString().length > 0 &&
                    et_level_required.text.toString().length > 0) {

                var card = DataCard(1,
                        et_title.text.toString(),
                        et_instruction.text.toString(),
                        et_type.text.toString().toInt(),
                        et_level_required.text.toString().toInt(),
                        et_exp.text.toString().toInt(),
                        et_rewards.text.toString().toInt(),
                        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()),
                        false,"")
                addToUserCard(card)
            } else {
                Toast.makeText(context, "Tolong Semua Data Diisi", Toast.LENGTH_SHORT).show()
            }
        }

        btn_read.setOnClickListener {
            showAllCardData()
            var data = dataCards
            tvResult.text = ""

            for (i in 0..(data.size - 1)) {
                tvResult.append(data.get(i).id.toString() + " " +
                        data.get(i).title + " " +
                        data.get(i).instruction + " " +
                        data.get(i).level + " " +
                        data.get(i).rewardPoint + " " +
                        data.get(i).rewardExp + " " +
                        data.get(i).inputdate + "\n")
            }
        }

        btn_update.setOnClickListener {
            //db.updateData()
            btn_read.performClick()
        }

        btn_delete.setOnClickListener {
            //db.deleteData()
            btn_read.performClick()
        }

        btn_selectData.setOnClickListener{
            val index = et_selectData.text.toString().toInt()

        }

    }

    private fun addToUserCard(card:DataCard){
        try{
            missionDB.use {
                insert(DataCard.TABLE_USERCARD,
                        DataCard.CARD_ID to card.id,
                        DataCard.CARD_TITLE to card.title,
                        DataCard.CARD_INSTRUCTION to card.instruction,
                        DataCard.CARD_TYPE to card.cardType,
                        DataCard.CARD_LEVEL to card.level,
                        DataCard.CARD_EXP to card.rewardExp,
                        DataCard.CARD_REWARD to card.rewardPoint,
                        DataCard.CARD_ISDONE to card.isDone,
                        DataCard.CARD_INPUTDATE to card.inputdate,
                        DataCard.CARD_CHECKBY to card.checkBy)
            }
            Toast.makeText(this,"Data Berhasil Masuk",Toast.LENGTH_SHORT)
        } catch (e:SQLiteConstraintException){
            Toast.makeText(this,"Data Tidak Masuk : $e",Toast.LENGTH_SHORT)
        }
    }
    private fun showAllCardData(){
        missionDB.use {
            val result = select(DataCard.TABLE_USERCARD)
            val cards = result.parseList(classParser<DataCard>())
            dataCards.addAll(cards)
        }
    }


}
