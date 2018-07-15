package com.brid.azis.vipgame.test.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.R.layout.activity_insert_db_test
import com.brid.azis.vipgame.test.Database.DbOnGoingMission
import com.brid.azis.vipgame.test.Model.Card
import kotlinx.android.synthetic.main.activity_insert_db_test.*
import kotlinx.android.synthetic.main.activity_insert_db_test.view.*
import org.jetbrains.anko.toast

class InsertDbTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_insert_db_test)

        val context = this
        var db = DbOnGoingMission(context)

        btn_insert.setOnClickListener {
            if (et_title.text.toString().length > 0 &&
                    et_instruction.text.toString().length > 0 &&
                    et_type.text.toString().length > 0 &&
                    et_rewards.text.toString().length > 0  &&
                    et_exp.text.toString().length > 0 &&
                    et_level_required.text.toString().length > 0) {

                var card = Card(et_title.text.toString(), et_instruction.text.toString(), et_type.text.toString().toInt(),
                        et_rewards.text.toString().toInt(), et_exp.text.toString().toInt(), et_level_required.text.toString().toInt())
                db.insertData(card)
            } else {
                Toast.makeText(context, "Tolong Semua Data Diisi", Toast.LENGTH_SHORT).show()
            }
        }

        btn_read.setOnClickListener {
            var data = db.readData()
            tvResult.text = ""

            for (i in 0..(data.size - 1)) {
                tvResult.append(data.get(i)._id.toString() + " " +
                        data.get(i).title + " " +
                        data.get(i).instruction + " " +
                        data.get(i).type + " " +
                        data.get(i).reward + " " +
                        data.get(i).exp + " " +
                        data.get(i).level + "\n")
            }
        }

        btn_update.setOnClickListener {
            db.updateData()
            btn_read.performClick()
        }

        btn_delete.setOnClickListener {
            db.deleteData()
            btn_read.performClick()
        }

    }

    fun addData(){

    }
}
